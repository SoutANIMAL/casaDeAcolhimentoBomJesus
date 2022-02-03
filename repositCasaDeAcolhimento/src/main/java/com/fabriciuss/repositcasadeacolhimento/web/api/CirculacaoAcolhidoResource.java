package com.fabriciuss.repositcasadeacolhimento.web.api;


import com.fabriciuss.repositcasadeacolhimento.domain.CirculacaoAcolhido;
import com.fabriciuss.repositcasadeacolhimento.service.CirculacaoAcolhidoService;
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
@RequestMapping("/circulacaoAcolhidos")
public class CirculacaoAcolhidoResource {
    private final Logger log = LoggerFactory.getLogger(CirculacaoAcolhidoResource.class);

    private final CirculacaoAcolhidoService circulacaoAcolhidoService;

    public CirculacaoAcolhidoResource(CirculacaoAcolhidoService circulacaoAcolhidoService) {
        this.circulacaoAcolhidoService = circulacaoAcolhidoService;
    }

    /**
     * {@code GET  /circulacaoAcolhido/:id} : get the "id" circulacaoAcolhido.
     *
     * @param id o id do circulacaoAcolhido que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o circulacaoAcolhido, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CirculacaoAcolhido> getCirculacaoAcolhido(@PathVariable Long id) {
        log.debug("REST request to get CirculacaoAcolhido : {}", id);
        Optional<CirculacaoAcolhido> circulacaoAcolhido = circulacaoAcolhidoService.findOne(id);
        if(circulacaoAcolhido.isPresent()) {
            return ResponseEntity.ok().body(circulacaoAcolhido.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<CirculacaoAcolhido>> getCirculacaoAcolhidos(){
        List<CirculacaoAcolhido> lista = circulacaoAcolhidoService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code PUT  /circulacaoAcolhido} : Atualiza um circulacaoAcolhido existenteUpdate.
     *
     * @param circulacaoAcolhido o circulacaoAcolhido a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o circulacaoAcolhido atualizado,
     * ou com status {@code 400 (Bad Request)} se o circulacaoAcolhido não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o circulacaoAcolhido não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<CirculacaoAcolhido> updateCirculacaoAcolhido(@RequestBody CirculacaoAcolhido circulacaoAcolhido) throws URISyntaxException {
        log.debug("REST request to update CirculacaoAcolhido : {}", circulacaoAcolhido);
        if (circulacaoAcolhido.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid CirculacaoAcolhido id null");
        }
        CirculacaoAcolhido result = circulacaoAcolhidoService.save(circulacaoAcolhido);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new circulacaoAcolhido.
     *
     * @param circulacaoAcolhido the circulacaoAcolhido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new circulacaoAcolhido, or with status {@code 400 (Bad Request)} if the circulacaoAcolhido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<CirculacaoAcolhido> createCirculacaoAcolhido(
            @RequestBody CirculacaoAcolhido circulacaoAcolhido
    ) throws URISyntaxException {
        log.debug("REST request to save CirculacaoAcolhido : {}", circulacaoAcolhido);
        if (circulacaoAcolhido.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo circulacaoAcolhido não pode ter um ID");
        }
        CirculacaoAcolhido result = circulacaoAcolhidoService.save(circulacaoAcolhido);
        return ResponseEntity.created(new URI("/api/circulacaoAcolhido/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<CirculacaoAcolhido> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<CirculacaoAcolhido> savedNotes  = new ArrayList<>();
        List<CirculacaoAcolhido> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(CirculacaoAcolhido::parseNote).collect(Collectors.toList());
        circulacaoAcolhidoService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" circulacaoAcolhido.
     *
     * @param id o id do circulacaoAcolhido que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCirculacaoAcolhido(@PathVariable Long id) {
        log.debug("REST request to delete CirculacaoAcolhido : {}", id);

        circulacaoAcolhidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
