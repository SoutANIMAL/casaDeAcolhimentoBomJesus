package com.fabriciuss.repositcasadeacolhimento.service;

import com.fabriciuss.repositcasadeacolhimento.domain.Despesa;
import com.fabriciuss.repositcasadeacolhimento.domain.Estoque;
import com.fabriciuss.repositcasadeacolhimento.repository.EstoqueRepositroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {
    private final Logger log = LoggerFactory.getLogger(DespesaService.class);

    private final EstoqueRepositroy estoqueRepositroy;

    public EstoqueService(EstoqueRepositroy estoqueRepositroy) {
        this.estoqueRepositroy = estoqueRepositroy;
    }

    public List<Estoque> findAllList(){
        return estoqueRepositroy.findAll();
    }

    public Optional<Estoque> findOne(Long id){
        log.debug("Request to get Estoque : {}", id);
        return estoqueRepositroy.findById(id);
    }

    public void delete(Long id){
        log.debug("Request to delete Estoque : {}", id);
        estoqueRepositroy.deleteById(id);
    }

    public Estoque save(Estoque estoque){
        log.debug("Request to save Estoque : {}", estoque);
        estoque = estoqueRepositroy.save(estoque);
        return estoque;
    }

    public List<Estoque> saveAll(List<Estoque> estoques) {
        log.debug("Request to save Estoque : {}", estoques);
        estoques = estoqueRepositroy.saveAll(estoques);
        return estoques;
    }
}
