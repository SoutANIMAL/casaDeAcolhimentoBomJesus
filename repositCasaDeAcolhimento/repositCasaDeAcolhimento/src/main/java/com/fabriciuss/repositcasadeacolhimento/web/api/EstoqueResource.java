package com.fabriciuss.repositcasadeacolhimento.web.api;

import com.fabriciuss.repositcasadeacolhimento.domain.Despesa;
import com.fabriciuss.repositcasadeacolhimento.domain.Estoque;
import com.fabriciuss.repositcasadeacolhimento.service.DespesaService;
import com.fabriciuss.repositcasadeacolhimento.service.EstoqueService;
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
@RequestMapping("/estoques")
public class EstoqueResource {
    private final Logger log = LoggerFactory.getLogger(EstoqueResource.class);

    private final EstoqueService estoqueService;

    public EstoqueResource(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    /**
     * {@code GET  /estoques/:id} : get the "id" estoque.
     *
     * @param id o id do estoque que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o estoque, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Estoque> getEstoque(@PathVariable Long id) {
        log.debug("REST request to get Estoque : {}", id);
        Optional<Estoque> estoque = estoqueService.findOne(id);
        if(estoque.isPresent()) {
            return ResponseEntity.ok().body(estoque.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Estoque>> getEstoques(){
        List<Estoque> lista = estoqueService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code PUT  /estoque} : Atualiza um estoque existente Update.
     *
     * @param estoque o estoque a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o estoque atualizado,
     * ou com status {@code 400 (Bad Request)} se o estoque não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o estoque não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Estoque> updateEstoque(@RequestBody Estoque estoque) throws URISyntaxException {
        log.debug("REST request to update Estoque : {}", estoque);
        if (estoque.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Estoque id null");
        }
        Estoque result = estoqueService.save(estoque);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new estoque.
     *
     * @param estoque the estoque to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estoque, or with status {@code 400 (Bad Request)} if the estoque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Estoque> createEstoque(
            @RequestBody Estoque estoque
    ) throws URISyntaxException {
        log.debug("REST request to save Estoque : {}", estoque);
        if (estoque.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo estoque não pode ter um ID");
        }
        Estoque result = estoqueService.save(estoque);
        return ResponseEntity.created(new URI("/api/estoque/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Estoque> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Estoque> savedNotes  = new ArrayList<>();
        List<Estoque> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Estoque::parseNote).collect(Collectors.toList());
        estoqueService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" estoque.
     *
     * @param id o id do estoque que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstoque(@PathVariable Long id) {
        log.debug("REST request to delete Estoque : {}", id);

        estoqueService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
