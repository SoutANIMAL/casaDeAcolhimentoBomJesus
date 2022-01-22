package com.fabriciuss.repositcasadeacolhimento.repository;

import com.fabriciuss.repositcasadeacolhimento.domain.Acolhido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcolhidoRepository extends JpaRepository<Acolhido, Long>{
}
