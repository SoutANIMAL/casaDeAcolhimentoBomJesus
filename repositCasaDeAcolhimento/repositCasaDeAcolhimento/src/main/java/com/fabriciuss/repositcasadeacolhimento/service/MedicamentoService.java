package com.fabriciuss.repositcasadeacolhimento.service;

import com.fabriciuss.repositcasadeacolhimento.domain.Medicamento;
import com.fabriciuss.repositcasadeacolhimento.repository.MedicamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoService {
    private final Logger log = LoggerFactory.getLogger(ReceituarioService.class);

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public List<Medicamento> findAllList(){
        return medicamentoRepository.findAll();
    }

    public Optional<Medicamento> findOne(Long id){
        log.debug("Request to get Medicamento : {}", id);
        return medicamentoRepository.findById(id);
    }

    public void delete(Long id){
        log.debug("Request to delete Medicamento : {}", id);
        medicamentoRepository.deleteById(id);
    }

    public Medicamento save(Medicamento medicamento){
        log.debug("Request to save Medicamento : {}", medicamento);
        medicamento = medicamentoRepository.save(medicamento);
        return medicamento;
    }

    public List<Medicamento> saveAll(List<Medicamento> medicamento) {
        log.debug("Request to save Pessoa : {}", medicamento);
        medicamento = medicamentoRepository.saveAll(medicamento);
        return medicamento;
    }
}
