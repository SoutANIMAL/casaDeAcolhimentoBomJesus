package com.fabriciuss.repositcasadeacolhimento.web.api;

import com.fabriciuss.repositcasadeacolhimento.domain.Receituario;
import com.fabriciuss.repositcasadeacolhimento.service.ReceituarioService;
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
@RequestMapping("/receituarios")
public class ReceituarioResource {
    private final Logger log = LoggerFactory.getLogger(RefeicaoResource.class);

    private final ReceituarioService receituarioService;

    public ReceituarioResource(ReceituarioService receituarioService) {
        this.receituarioService = receituarioService;
    }

    /**
     * {@code GET  /receituarios/:id} : get the "id" receituario.
     *
     * @param id o id do receituario que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o receituario, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Receituario> getReceituario(@PathVariable Long id) {
        log.debug("REST request to get Receituario : {}", id);
        Optional<Receituario> receituario = receituarioService.findOne(id);
        if(receituario.isPresent()) {
            return ResponseEntity.ok().body(receituario.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Receituario>> getReceituarios(){
        List<Receituario> lista = receituarioService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code PUT  /receituarios} : Atualiza um receituario existenteUpdate.
     *
     * @param receituario o receituario a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o receituario atualizado,
     * ou com status {@code 400 (Bad Request)} se o receituario não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o receituario não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Receituario> updateReceituario(@RequestBody Receituario receituario) throws URISyntaxException {
        log.debug("REST request to update Receituario : {}", receituario);
        if (receituario.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Receituario id null");
        }
        Receituario result = receituarioService.save(receituario);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new receituario.
     *
     * @param receituario the receituario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new receituario, or with status {@code 400 (Bad Request)} if the receituario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Receituario> createReceituario(
            @RequestBody Receituario receituario
    ) throws URISyntaxException {
        log.debug("REST request to save Receituario : {}", receituario);
        if (receituario.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo receituario não pode ter um ID");
        }
        Receituario result = receituarioService.save(receituario);
        return ResponseEntity.created(new URI("/api/receituarios/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Receituario> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Receituario> savedNotes  = new ArrayList<>();
        List<Receituario> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Receituario::parseNote).collect(Collectors.toList());
        receituarioService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" receituario.
     *
     * @param id o id do receituario que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceituario(@PathVariable Long id) {
        log.debug("REST request to delete Receituario : {}", id);

        receituarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}