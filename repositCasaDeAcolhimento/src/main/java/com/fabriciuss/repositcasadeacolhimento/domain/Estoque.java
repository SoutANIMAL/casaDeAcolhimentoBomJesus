package com.fabriciuss.repositcasadeacolhimento.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbEstoque")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer quantidade;
    String nome;
    String descricao;
    Boolean isActive;

    public static Estoque parseNote(String line) {
        String[] text = line.split(",");
        Estoque note = new Estoque();
        note.setId(Long.parseLong(text[0]));
        note.setDescricao(text[1]);
        return note;
    }

}
