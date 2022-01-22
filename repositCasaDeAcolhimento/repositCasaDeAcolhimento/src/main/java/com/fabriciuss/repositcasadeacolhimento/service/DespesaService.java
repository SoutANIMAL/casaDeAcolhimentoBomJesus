package com.fabriciuss.repositcasadeacolhimento.service;

import com.fabriciuss.repositcasadeacolhimento.domain.Despesa;
import com.fabriciuss.repositcasadeacolhimento.repository.DespesaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DespesaService {
    private final Logger log = LoggerFactory.getLogger(DespesaService.class);

    private final DespesaRepository despesaRepository;

    public DespesaService(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public List<Despesa> findAllList(){
        return despesaRepository.findAll();
    }

    public Optional<Despesa> findOne(Long id){
        log.debug("Request to get Despesa : {}", id);
        return despesaRepository.findById(id);
    }

    public void delete(Long id){
        log.debug("Request to delete Despesa : {}", id);
        despesaRepository.deleteById(id);
    }

    public Despesa save(Despesa despesa){
        log.debug("Request to save Despesa : {}", despesa);
        despesa = despesaRepository.save(despesa);
        return despesa;
    }

    public List<Despesa> saveAll(List<Despesa> despesas) {
        log.debug("Request to save Despesa : {}", despesas);
        despesas = despesaRepository.saveAll(despesas);
        return despesas;
    }
}
