package com.fabriciuss.repositcasadeacolhimento.web.api;

import com.fabriciuss.repositcasadeacolhimento.domain.Refeicao;
import com.fabriciuss.repositcasadeacolhimento.service.RefeicaoService;
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
@RequestMapping("/refeicaos")
public class RefeicaoResource {
    private final Logger log = LoggerFactory.getLogger(RefeicaoResource.class);

    private final RefeicaoService refeicaoService;

    public RefeicaoResource(RefeicaoService refeicaoService) {
        this.refeicaoService = refeicaoService;
    }

    /**
     * {@code GET  /refeicaos/:id} : get the "id" refeicao.
     *
     * @param id o id do refeicao que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o refeicao, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Refeicao> getRefeicao(@PathVariable Long id) {
        log.debug("REST request to get Refeicao : {}", id);
        Optional<Refeicao> refeicao = refeicaoService.findOne(id);
        if(refeicao.isPresent()) {
            return ResponseEntity.ok().body(refeicao.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Refeicao>> getRefeicaos(){
        List<Refeicao> lista = refeicaoService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code PUT  /refeicaos} : Atualiza um refeicao existenteUpdate.
     *
     * @param refeicao o refeicao a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o refeicao atualizado,
     * ou com status {@code 400 (Bad Request)} se o refeicao não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o refeicao não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Refeicao> updateRefeicao(@RequestBody Refeicao refeicao) throws URISyntaxException {
        log.debug("REST request to update Refeicao : {}", refeicao);
        if (refeicao.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Refeicao id null");
        }
        Refeicao result = refeicaoService.save(refeicao);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new refeicao.
     *
     * @param refeicao the refeicao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new refeicao, or with status {@code 400 (Bad Request)} if the refeicao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Refeicao> createRefeicao(
            @RequestBody Refeicao refeicao
    ) throws URISyntaxException {
        log.debug("REST request to save Refeicao : {}", refeicao);
        if (refeicao.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo refeicao não pode ter um ID");
        }
        Refeicao result = refeicaoService.save(refeicao);
        return ResponseEntity.created(new URI("/api/refeicaos/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Refeicao> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Refeicao> savedNotes  = new ArrayList<>();
        List<Refeicao> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Refeicao::parseNote).collect(Collectors.toList());
        refeicaoService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" refeicao.
     *
     * @param id o id do refeicao que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRefeicao(@PathVariable Long id) {
        log.debug("REST request to delete Refeicao : {}", id);

        refeicaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}