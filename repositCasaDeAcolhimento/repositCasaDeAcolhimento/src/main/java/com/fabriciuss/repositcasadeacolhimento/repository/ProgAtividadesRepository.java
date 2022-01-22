package com.fabriciuss.repositcasadeacolhimento.repository;

import com.fabriciuss.repositcasadeacolhimento.domain.ProgAtividades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgAtividadesRepository extends JpaRepository<ProgAtividades, Long>{

}
