package com.fabriciuss.repositcasadeacolhimento.service;

import com.fabriciuss.repositcasadeacolhimento.domain.Refeicao;
import com.fabriciuss.repositcasadeacolhimento.repository.RefeicaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RefeicaoService {
    private final Logger log = LoggerFactory.getLogger(ReceituarioService.class);

    private final RefeicaoRepository refeicaoRepository;

    public RefeicaoService(RefeicaoRepository refeicaoRepository) {
        this.refeicaoRepository = refeicaoRepository;
    }

    public List<Refeicao> findAllList(){
        return refeicaoRepository.findAll();
    }

    public Optional<Refeicao> findOne(Long id){
        log.debug("Request to get Refeicao : {}", id);
        return refeicaoRepository.findById(id);
    }

    public void delete(Long id){
        log.debug("Request to delete Refeicao : {}", id);
        refeicaoRepository.deleteById(id);
    }

    public Refeicao save(Refeicao refeicao){
        log.debug("Request to save Refeicao : {}", refeicao);
        refeicao = refeicaoRepository.save(refeicao);
        return refeicao;
    }

    public List<Refeicao> saveAll(List<Refeicao> refeicao) {
        log.debug("Request to save Pessoa : {}", refeicao);
        refeicao = refeicaoRepository.saveAll(refeicao);
        return refeicao;
    }
}
