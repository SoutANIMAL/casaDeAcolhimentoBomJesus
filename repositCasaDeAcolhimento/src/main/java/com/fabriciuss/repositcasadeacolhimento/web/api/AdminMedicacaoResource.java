package com.fabriciuss.repositcasadeacolhimento.web.api;

import com.fabriciuss.repositcasadeacolhimento.domain.AdminMedicacao;
import com.fabriciuss.repositcasadeacolhimento.service.AdminMedicacaoService;
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
@RequestMapping("/adminMedicacoes")
public class AdminMedicacaoResource {
    private final Logger log = LoggerFactory.getLogger(AdminMedicacaoResource.class);

    private final AdminMedicacaoService adminMedicacaoService;

    public AdminMedicacaoResource(AdminMedicacaoService adminMedicacaoService) {
        this.adminMedicacaoService = adminMedicacaoService;
    }

    /**
     * {@code GET  /adminMedicacoes/:id} : get the "id" adminMedicacao.
     *
     * @param id o id do adminMedicacao que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o adminMedicacao, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdminMedicacao> getAdminMedicacao(@PathVariable Long id) {
        log.debug("REST request to get AdminMedicacao : {}", id);
        Optional<AdminMedicacao> adminMedicacao = adminMedicacaoService.findOne(id);
        if(adminMedicacao.isPresent()) {
            return ResponseEntity.ok().body(adminMedicacao.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<AdminMedicacao>> getAdminMedicacoes(){
        List<AdminMedicacao> lista = adminMedicacaoService.findAllList();
        if(lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code PUT  /adminMedicacoes} : Atualiza um adminMedicacao existenteUpdate.
     *
     * @param adminMedicacao o adminMedicacao a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o adminMedicacao atualizado,
     * ou com status {@code 400 (Bad Request)} se o adminMedicacao não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o adminMedicacao não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<AdminMedicacao> updateAdminMedicacao(@RequestBody AdminMedicacao adminMedicacao) throws URISyntaxException {
        log.debug("REST request to update AdminMedicacao : {}", adminMedicacao);
        if (adminMedicacao.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid AdminMedicacao id null");
        }
        AdminMedicacao result = adminMedicacaoService.save(adminMedicacao);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * {@code POST  /} : Create a new adminMedicacao.
     *
     * @param adminMedicacao the adminMedicacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminMedicacao, or with status {@code 400 (Bad Request)} if the adminMedicacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<AdminMedicacao> createAdminMedicacao(
            @RequestBody AdminMedicacao adminMedicacao
    ) throws URISyntaxException {
        log.debug("REST request to save AdminMedicacao : {}", adminMedicacao);
        if (adminMedicacao.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo adminMedicacao não pode ter um ID");
        }
        AdminMedicacao result = adminMedicacaoService.save(adminMedicacao);
        return ResponseEntity.created(new URI("/api/AdminMedicacoes/" + result.getId()))
                .body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<AdminMedicacao> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<AdminMedicacao> savedNotes  = new ArrayList<>();
        List<AdminMedicacao> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                .map(AdminMedicacao::parseNote).collect(Collectors.toList());
        adminMedicacaoService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" adminMedicacao.
     *
     * @param id o id do adminMedicacao que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdminMedicacao(@PathVariable Long id) {
        log.debug("REST request to delete AdminMedicacao : {}", id);

        adminMedicacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
