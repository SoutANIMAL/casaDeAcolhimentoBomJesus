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
@Table(name = "tbProgramacaoAtividades")
public class ProgAtividades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String descricao;
    LocalTime horaInicio;
    LocalTime horaFim;
    Boolean isActive;

    public static ProgAtividades parseNote(String line) {
        String[] text = line.split(",");
        ProgAtividades note = new ProgAtividades();
        note.setId(Long.parseLong(text[0]));
        note.setDescricao(text[1]);
        return note;
    }
}
