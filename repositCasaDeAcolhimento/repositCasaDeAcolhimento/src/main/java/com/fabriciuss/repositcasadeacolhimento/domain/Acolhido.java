package com.fabriciuss.repositcasadeacolhimento.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbAcolhido")
public class Acolhido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nome;
    Instant dataNascimento;
    String cpf;
    String descricaoDoCaso;
    String Naturalidade;
    Boolean isActive;

    // relacionamento N:N com medicação
    @OneToMany(mappedBy = "paciente")
    List<Receituario> medicamentos;

    public static Acolhido parseNote(String line) {
        String[] text = line.split(",");
        Acolhido note = new Acolhido();
        note.setId(Long.parseLong(text[0]));
        note.setDescricaoDoCaso(text[1]);
        return note;
    }
}
