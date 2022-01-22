package com.fabriciuss.repositcasadeacolhimento.repository;


import com.fabriciuss.repositcasadeacolhimento.domain.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepositroy extends JpaRepository<Estoque, Long> {
}
