package com.fabriciuss.repositcasadeacolhimento.service;

import com.fabriciuss.repositcasadeacolhimento.domain.ProgAtividades;
import com.fabriciuss.repositcasadeacolhimento.repository.ProgAtividadesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgAtividadesService {
    private final Logger log = LoggerFactory.getLogger(ProgAtividadesService.class);

    private final ProgAtividadesRepository progAtividadesRepository;

    public ProgAtividadesService(ProgAtividadesRepository progAtividadesRepository) {
        this.progAtividadesRepository = progAtividadesRepository;
    }

    public List<ProgAtividades> findAllList(){
        return progAtividadesRepository.findAll();
    }

    public Optional<ProgAtividades> findOne(Long id){
        log.debug("Request to get ProgramacaoAtividades : {}", id);
        return progAtividadesRepository.findById(id);
    }

    public void delete(Long id){
        log.debug("Request to delete ProgramacaoAtividades : {}", id);
        progAtividadesRepository.deleteById(id);
    }

    public ProgAtividades save(ProgAtividades programacaoAtividades){
        log.debug("Request to save ProgramacaoAtividades : {}", programacaoAtividades);
        programacaoAtividades = progAtividadesRepository.save(programacaoAtividades);
        return programacaoAtividades;
    }

    public List<ProgAtividades> saveAll(List<ProgAtividades> programacaoAtividades) {
        log.debug("Request to save Pessoa : {}", programacaoAtividades);
        programacaoAtividades = progAtividadesRepository.saveAll(programacaoAtividades);
        return programacaoAtividades;
    }
}
