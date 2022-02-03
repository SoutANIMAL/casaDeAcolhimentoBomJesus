package com.fabriciuss.repositcasadeacolhimento.domain;

import java.time.Instant;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbAdministracaoMedicacao")

public class AdminMedicacao {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;
    Instant dataAdministracao;
    LocalTime horaAdministracao;
    String descricao;

    // relacionamento N:1 com acolhido
    @ManyToOne
    Acolhido acolhido;

    // relacionamento N:1 com funcionario
    @ManyToOne
    Funcionario ministrador;

    // relacionamento N:1 com remedio
    @ManyToOne
    Medicamento medicamento;

    Boolean isActive;

    public static AdminMedicacao parseNote(String line) {
        String[] text = line.split(",");
        AdminMedicacao note = new AdminMedicacao();
        note.setId(Long.parseLong(text[0]));
        note.setDescricao(text[1]);
        return note;
    }

}
