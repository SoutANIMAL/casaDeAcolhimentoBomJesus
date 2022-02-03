package com.fabriciuss.repositcasadeacolhimento.web.api;

import com.fabriciuss.repositcasadeacolhimento.domain.Medicamento;
import com.fabriciuss.repositcasadeacolhimento.service.MedicamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Medicamentos")
public class MedicamentoResource {
    private final Logger log = LoggerFactory.getLogger(ProgAtividadesResource.class);

    private final MedicamentoService medicamentoService;

    public MedicamentoResource(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    /**
     * {@code GET  /medicamentos/:id} : get the "id" medicamento.
     *
     * @param id o id do medicamento que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o medicamento, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> getMedicamento(@PathVariable Long id) {
        log.debug("REST request to get Medicamento : {}", id);
        Optional<Medicamento> medicamento = medicamentoService.findOne(id);
        if(medicamento.isPresent()) {
            return ResponseEntity.ok().body(medicamento.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Medicamento>> getMedicamentos(){
        List<Medicamento> lista = medicamentoService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code PUT  /medicamentos} : Atualiza um medicamento existenteUpdate.
     *
     * @param medicamento o medicamento a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o medicamento atualizado,
     * ou com status {@code 400 (Bad Request)} se o medicamento não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o medicamento não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Medicamento> updateMedicamento(@RequestBody Medicamento medicamento) throws URISyntaxException {
        log.debug("REST request to update Medicamento : {}", medicamento);
        if (medicamento.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Medicamento id null");
        }
        Medicamento result = medicamentoService.save(medicamento);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new medicamento.
     *
     * @param medicamento the medicamento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicamento, or with status {@code 400 (Bad Request)} if the medicamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Medicamento> createMedicamento(
            @RequestBody Medicamento medicamento
    ) throws URISyntaxException {
        log.debug("REST request to save Medicamento : {}", medicamento);
        if (medicamento.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo Medicamento não pode ter um ID");
        }
        Medicamento result = medicamentoService.save(medicamento);
        return ResponseEntity.created(new URI("/api/Medicamentos/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Medicamento> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Medicamento> savedNotes  = new ArrayList<>();
        List<Medicamento> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Medicamento::parseNote).collect(Collectors.toList());
        medicamentoService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" medicamento.
     *
     * @param id o id do medicamento que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicamento(@PathVariable Long id) {
        log.debug("REST request to delete Medicamento : {}", id);

        medicamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}