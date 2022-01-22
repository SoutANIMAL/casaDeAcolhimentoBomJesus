package com.fabriciuss.repositcasadeacolhimento.repository;

import com.fabriciuss.repositcasadeacolhimento.domain.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefeicaoRepository extends JpaRepository<Refeicao, Long>{

}
