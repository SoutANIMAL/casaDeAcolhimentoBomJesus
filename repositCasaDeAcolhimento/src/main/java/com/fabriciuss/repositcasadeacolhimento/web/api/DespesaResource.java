package com.fabriciuss.repositcasadeacolhimento.web.api;


import com.fabriciuss.repositcasadeacolhimento.domain.Despesa;
import com.fabriciuss.repositcasadeacolhimento.service.DespesaService;
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
@RequestMapping("/despesas")
public class DespesaResource {
    private final Logger log = LoggerFactory.getLogger(DespesaResource.class);

    private final DespesaService despesaService;

    public DespesaResource(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    /**
     * {@code GET  /despesas/:id} : get the "id" despesa.
     *
     * @param id o id do despesa que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o despesa, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Despesa> getDespesa(@PathVariable Long id) {
        log.debug("REST request to get Despesa : {}", id);
        Optional<Despesa> despesa = despesaService.findOne(id);
        if(despesa.isPresent()) {
            return ResponseEntity.ok().body(despesa.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Despesa>> getDesepesas(){
        List<Despesa> lista = despesaService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code PUT  /despesa} : Atualiza um despesa existenteUpdate.
     *
     * @param despesa o despesa a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o despesa atualizado,
     * ou com status {@code 400 (Bad Request)} se o despesa não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o despesa não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Despesa> updateDespesa(@RequestBody Despesa despesa) throws URISyntaxException {
        log.debug("REST request to update Despesa : {}", despesa);
        if (despesa.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Despesa id null");
        }
        Despesa result = despesaService.save(despesa);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new despesa.
     *
     * @param despesa the despesa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new despesa, or with status {@code 400 (Bad Request)} if the despesa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Despesa> createDespesa(
            @RequestBody Despesa despesa
    ) throws URISyntaxException {
        log.debug("REST request to save Despesa : {}", despesa);
        if (despesa.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo despesa não pode ter um ID");
        }
        Despesa result = despesaService.save(despesa);
        return ResponseEntity.created(new URI("/api/despesa/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Despesa> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Despesa> savedNotes  = new ArrayList<>();
        List<Despesa> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Despesa::parseNote).collect(Collectors.toList());
        despesaService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" despesa.
     *
     * @param id o id do despesa que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDespesa(@PathVariable Long id) {
        log.debug("REST request to delete Despesa : {}", id);

        despesaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
