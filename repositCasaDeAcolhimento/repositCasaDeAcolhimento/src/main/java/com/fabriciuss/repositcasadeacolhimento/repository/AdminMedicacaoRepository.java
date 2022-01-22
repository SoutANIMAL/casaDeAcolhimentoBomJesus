package com.fabriciuss.repositcasadeacolhimento.repository;

import com.fabriciuss.repositcasadeacolhimento.domain.AdminMedicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMedicacaoRepository extends JpaRepository<AdminMedicacao, Long>{
}
