package com.fabriciuss.repositcasadeacolhimento.service;

import com.fabriciuss.repositcasadeacolhimento.domain.Receituario;
import com.fabriciuss.repositcasadeacolhimento.repository.ReceituarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceituarioService {
    private final Logger log = LoggerFactory.getLogger(ReceituarioService.class);

    private final ReceituarioRepository receituarioRepository;

    public ReceituarioService(ReceituarioRepository receituarioRepository) {
        this.receituarioRepository = receituarioRepository;
    }

    public List<Receituario> findAllList(){
        return receituarioRepository.findAll();
    }

    public Optional<Receituario> findOne(Long id){
        log.debug("Request to get Receituario : {}", id);
        return receituarioRepository.findById(id);
    }

    public void delete(Long id){
        log.debug("Request to delete Receituario : {}", id);
        receituarioRepository.deleteById(id);
    }

    public Receituario save(Receituario receituario){
        log.debug("Request to save Receituario : {}", receituario);
        receituario = receituarioRepository.save(receituario);
        return receituario;
    }

    public List<Receituario> saveAll(List<Receituario> receituario) {
        log.debug("Request to save Pessoa : {}", receituario);
        receituario = receituarioRepository.saveAll(receituario);
        return receituario;
    }
}
