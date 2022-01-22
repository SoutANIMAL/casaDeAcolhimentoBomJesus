package com.fabriciuss.repositcasadeacolhimento.web.api;

import com.fabriciuss.repositcasadeacolhimento.domain.Funcionario;
import com.fabriciuss.repositcasadeacolhimento.service.FuncionarioService;
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
@RequestMapping("/funcionarios")
public class FuncionarioResource {
    private final Logger log = LoggerFactory.getLogger(FuncionarioResource.class);

    private final FuncionarioService funcionarioService;

    public FuncionarioResource(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    /**
     * {@code GET  /funcionarios/:id} : get the "id" funcionario.
     *
     * @param id o id do funcionario que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o funcionario, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getFuncionario(@PathVariable Long id) {
        log.debug("REST request to get Funcionario : {}", id);
        Optional<Funcionario> funcionario = funcionarioService.findOne(id);
        if(funcionario.isPresent()) {
            return ResponseEntity.ok().body(funcionario.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Funcionario>> getFuncionarios(){
        List<Funcionario> lista = funcionarioService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code PUT  /funcionarios} : Atualiza um funcionario existenteUpdate.
     *
     * @param funcionario o funcionario a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o funcionario atualizado,
     * ou com status {@code 400 (Bad Request)} se o funcionario não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o funcionario não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Funcionario> updateFuncionario(@RequestBody Funcionario funcionario) throws URISyntaxException {
        log.debug("REST request to update Funcionario : {}", funcionario);
        if (funcionario.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Funcionario id null");
        }
        Funcionario result = funcionarioService.save(funcionario);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new funcionario.
     *
     * @param funcionario the funcionario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funcionario, or with status {@code 400 (Bad Request)} if the funcionario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Funcionario> createFuncionario(
            @RequestBody Funcionario funcionario
    ) throws URISyntaxException {
        log.debug("REST request to save Funcionario : {}", funcionario);
        if (funcionario.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo funcionario não pode ter um ID");
        }
        Funcionario result = funcionarioService.save(funcionario);
        return ResponseEntity.created(new URI("/api/funcionarios/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Funcionario> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Funcionario> savedNotes  = new ArrayList<>();
        List<Funcionario> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(Funcionario::parseNote).collect(Collectors.toList());
        funcionarioService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" funcionario.
     *
     * @param id o id do funcionario que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Long id) {
        log.debug("REST request to delete Funcionario : {}", id);

        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}