package com.fabriciuss.repositcasadeacolhimento.service;

import com.fabriciuss.repositcasadeacolhimento.domain.CirculacaoAcolhido;
import com.fabriciuss.repositcasadeacolhimento.repository.CirculacaoAcolhidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CirculacaoAcolhidoService {
    private final Logger log = LoggerFactory.getLogger(CirculacaoAcolhidoService.class);

    private final CirculacaoAcolhidoRepository circulacaoAcolhidoRepository;

    public CirculacaoAcolhidoService(CirculacaoAcolhidoRepository circulacaoAcolhidoRepository) {
        this.circulacaoAcolhidoRepository = circulacaoAcolhidoRepository;
    }

    public List<CirculacaoAcolhido> findAllList(){
        return circulacaoAcolhidoRepository.findAll();
    }

    public Optional<CirculacaoAcolhido> findOne(Long id){
        log.debug("Request to get CirculacaoAcolhido : {}", id);
        return circulacaoAcolhidoRepository.findById(id);
    }

    public void delete(Long id){
        log.debug("Request to delete CirculacaoAcolhido : {}", id);
        circulacaoAcolhidoRepository.deleteById(id);
    }

    public CirculacaoAcolhido save(CirculacaoAcolhido circulacaoAcolhido){
        log.debug("Request to save CirculacaoAcolhido : {}", circulacaoAcolhido);
        circulacaoAcolhido = circulacaoAcolhidoRepository.save(circulacaoAcolhido);
        return circulacaoAcolhido;
    }

    public List<CirculacaoAcolhido> saveAll(List<CirculacaoAcolhido> circulacaoAcolhidos) {
        log.debug("Request to save CirculacaoAcolhido : {}", circulacaoAcolhidos);
        circulacaoAcolhidos = circulacaoAcolhidoRepository.saveAll(circulacaoAcolhidos);
        return circulacaoAcolhidos;
    }
}
