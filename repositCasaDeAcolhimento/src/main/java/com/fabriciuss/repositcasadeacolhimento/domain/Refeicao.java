package com.fabriciuss.repositcasadeacolhimento.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbRefeicao")
public class Refeicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String descricao;

    Instant dataRefeicao;

    @ManyToOne
    Funcionario responsavel;

    Boolean isActive;

    public static Refeicao parseNote(String line) {
        String[] text = line.split(",");
        Refeicao note = new Refeicao();
        note.setId(Long.parseLong(text[0]));
        return note;
    }
}
