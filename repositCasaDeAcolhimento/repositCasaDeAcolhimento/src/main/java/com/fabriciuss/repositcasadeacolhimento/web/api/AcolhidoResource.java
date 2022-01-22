package com.fabriciuss.repositcasadeacolhimento.web.api;


import com.fabriciuss.repositcasadeacolhimento.domain.Acolhido;
import com.fabriciuss.repositcasadeacolhimento.service.AcolhidoService;
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
@RequestMapping("/acolhidos")
public class AcolhidoResource {
    private final Logger log = LoggerFactory.getLogger(AcolhidoResource.class);

    private final AcolhidoService acolhidoService;

    public AcolhidoResource(AcolhidoService acolhidoService) {
        this.acolhidoService = acolhidoService;
    }

    /**
     * {@code GET  /acolhidos/:id} : get the "id" acolhido.
     *
     * @param id o id do acolhido que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o acolhido, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Acolhido> getAcolhido(@PathVariable Long id) {
        log.debug("REST request to get Acolhido : {}", id);
        Optional<Acolhido> acolhido = acolhidoService.findOne(id);
        if(acolhido.isPresent()) {
            return ResponseEntity.ok().body(acolhido.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Acolhido>> getAcolhidos(){
        List<Acolhido> lista = acolhidoService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code PUT  /acolhidos} : Atualiza um acolhido existenteUpdate.
     *
     * @param acolhido o acolhido a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o acolhido atualizado,
     * ou com status {@code 400 (Bad Request)} se o acolhido não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o acolhido não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Acolhido> updateAcolhido(@RequestBody Acolhido acolhido) throws URISyntaxException {
        log.debug("REST request to update Acolhido : {}", acolhido);
        if (acolhido.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Acolhido id null");
        }
        Acolhido result = acolhidoService.save(acolhido);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new acolhido.
     *
     * @param acolhido the acolhido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new acolhido, or with status {@code 400 (Bad Request)} if the acolhido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Acolhido> createAcolhido(
            @RequestBody Acolhido acolhido
    ) throws URISyntaxException {
        log.debug("REST request to save Acolhido : {}", acolhido);
        if (acolhido.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo acolhido não pode ter um ID");
        }
        Acolhido result = acolhidoService.save(acolhido);
        return ResponseEntity.created(new URI("/api/acolhidos/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Acolhido> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Acolhido> savedNotes  = new ArrayList<>();
        List<Acolhido> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Acolhido::parseNote).collect(Collectors.toList());
        acolhidoService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" acolhido.
     *
     * @param id o id do acolhido que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcolhido(@PathVariable Long id) {
        log.debug("REST request to delete Acolhido : {}", id);

        acolhidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
