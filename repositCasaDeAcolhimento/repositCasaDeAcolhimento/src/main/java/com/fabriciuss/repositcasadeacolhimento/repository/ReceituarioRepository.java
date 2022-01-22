package com.fabriciuss.repositcasadeacolhimento.repository;

import com.fabriciuss.repositcasadeacolhimento.domain.Receituario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceituarioRepository extends JpaRepository<Receituario, Long>{
}
