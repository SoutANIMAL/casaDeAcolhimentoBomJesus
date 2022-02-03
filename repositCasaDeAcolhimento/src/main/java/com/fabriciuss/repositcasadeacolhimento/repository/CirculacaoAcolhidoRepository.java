package com.fabriciuss.repositcasadeacolhimento.repository;

import com.fabriciuss.repositcasadeacolhimento.domain.CirculacaoAcolhido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CirculacaoAcolhidoRepository extends JpaRepository<CirculacaoAcolhido, Long> {
}
