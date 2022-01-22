package com.fabriciuss.repositcasadeacolhimento.web.api;

import com.fabriciuss.repositcasadeacolhimento.domain.ProgAtividades;
import com.fabriciuss.repositcasadeacolhimento.service.ProgAtividadesService;
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
@RequestMapping("/ProgAtividades")
public class ProgAtividadesResource {
    private final Logger log = LoggerFactory.getLogger(ProgAtividadesResource.class);

    private final ProgAtividadesService progAtividadesService;

    public ProgAtividadesResource(ProgAtividadesService progAtividadesService) {
        this.progAtividadesService = progAtividadesService;
    }

    /**
     * {@code GET  /progAtividadess/:id} : get the "id" progAtividades.
     *
     * @param id o id do progAtividades que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o progAtividades, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProgAtividades> getProgAtividades(@PathVariable Long id) {
        log.debug("REST request to get progAtividades : {}", id);
        Optional<ProgAtividades> progAtividades = progAtividadesService.findOne(id);
        if(progAtividades.isPresent()) {
            return ResponseEntity.ok().body(progAtividades.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<ProgAtividades>> getProgAtividadess(){
        List<ProgAtividades> lista = progAtividadesService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code PUT  /progAtividadess} : Atualiza um progAtividades existenteUpdate.
     *
     * @param progAtividades o progAtividades a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o progAtividades atualizado,
     * ou com status {@code 400 (Bad Request)} se o progAtividades não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o progAtividades não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<ProgAtividades> updateProgAtividades(@RequestBody ProgAtividades progAtividades) throws URISyntaxException {
        log.debug("REST request to update ProgAtividades : {}", progAtividades);
        if (progAtividades.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid ProgAtividades id null");
        }
        ProgAtividades result = progAtividadesService.save(progAtividades);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new progAtividades.
     *
     * @param progAtividades the progAtividades to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new progAtividades, or with status {@code 400 (Bad Request)} if the progAtividades has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<ProgAtividades> createProgAtividades(
            @RequestBody ProgAtividades progAtividades
    ) throws URISyntaxException {
        log.debug("REST request to save ProgAtividades : {}", progAtividades);
        if (progAtividades.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo ProgAtividades não pode ter um ID");
        }
        ProgAtividades result = progAtividadesService.save(progAtividades);
        return ResponseEntity.created(new URI("/api/ProgAtividadess/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ProgAtividades> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<ProgAtividades> savedNotes  = new ArrayList<>();
        List<ProgAtividades> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(ProgAtividades::parseNote).collect(Collectors.toList());
        progAtividadesService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" progAtividades.
     *
     * @param id o id do progAtividades que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgAtividades(@PathVariable Long id) {
        log.debug("REST request to delete ProgAtividades : {}", id);

        progAtividadesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}