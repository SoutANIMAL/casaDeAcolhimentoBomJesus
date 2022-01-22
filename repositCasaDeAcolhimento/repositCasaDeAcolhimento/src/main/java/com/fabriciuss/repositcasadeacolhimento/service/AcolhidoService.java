package com.fabriciuss.repositcasadeacolhimento.service;

import com.fabriciuss.repositcasadeacolhimento.domain.Acolhido;
import com.fabriciuss.repositcasadeacolhimento.repository.AcolhidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcolhidoService {
    private final Logger log = LoggerFactory.getLogger(AcolhidoService.class);

    private final AcolhidoRepository acolhidoRepository;

    public AcolhidoService(AcolhidoRepository acolhidoRepository) {
        this.acolhidoRepository = acolhidoRepository;
    }

    public List<Acolhido> findAllList(){
        return acolhidoRepository.findAll();
    }

    public Optional<Acolhido> findOne(Long id){
        log.debug("Request to get Acolhido : {}", id);
        return acolhidoRepository.findById(id);
    }

    public void delete(Long id){
        log.debug("Request to delete Acolhido : {}", id);
        acolhidoRepository.deleteById(id);
    }

    public Acolhido save(Acolhido acolhido){
        log.debug("Request to save Acolhido : {}", acolhido);
        acolhido = acolhidoRepository.save(acolhido);
        return acolhido;
    }

    public List<Acolhido> saveAll(List<Acolhido> acolhidos) {
        log.debug("Request to save Acolhido : {}", acolhidos);
        acolhidos = acolhidoRepository.saveAll(acolhidos);
        return acolhidos;
    }
}
