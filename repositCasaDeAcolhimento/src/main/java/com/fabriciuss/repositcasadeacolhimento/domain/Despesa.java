package com.fabriciuss.repositcasadeacolhimento.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbDespesa")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String descricao;
    String tipo;
    Double custo;
    LocalTime horaPagamento;
    Boolean isActive;

    public static Despesa parseNote(String line) {
        String[] text = line.split(",");
        Despesa note = new Despesa();
        note.setId(Long.parseLong(text[0]));
        note.setDescricao(text[1]);
        return note;
    }

}
