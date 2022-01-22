package com.fabriciuss.repositcasadeacolhimento.service;

import com.fabriciuss.repositcasadeacolhimento.domain.Funcionario;
import com.fabriciuss.repositcasadeacolhimento.repository.FuncionarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    private final Logger log = LoggerFactory.getLogger(FuncionarioService.class);

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<Funcionario> findAllList(){
        return funcionarioRepository.findAll();
    }

    public Optional<Funcionario> findOne(Long id){
        log.debug("Request to get Funcionario : {}", id);
        return funcionarioRepository.findById(id);
    }

    public void delete(Long id){
        log.debug("Request to delete Funcionario : {}", id);
        funcionarioRepository.deleteById(id);
    }

    public Funcionario save(Funcionario funcionario){
        log.debug("Request to save Funcionario : {}", funcionario);
        funcionario = funcionarioRepository.save(funcionario);
        return funcionario;
    }

    public List<Funcionario> saveAll(List<Funcionario> funcionarios) {
        log.debug("Request to save Pessoa : {}", funcionarios);
        funcionarios = funcionarioRepository.saveAll(funcionarios);
        return funcionarios;
    }
}
