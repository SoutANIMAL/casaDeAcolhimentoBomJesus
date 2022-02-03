package com.fabriciuss.repositcasadeacolhimento.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbFuncionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 128)
    String nome;

    Instant dataNascimento;

    String tipoFuncionario;
    Double salario;
    String cpf;
    String email;
    String telefone;
    String cep;
    String logradouro;
    String complemento;
    String bairro;
    String localidade;
    String uf;
    String ibge;

    Boolean isActive;

    public static Funcionario parseNote(String line) {
        String[] text = line.split(",");
        Funcionario note = new Funcionario();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
//    @OneToMany(mappedBy = "responsavel")
//    List<Refeicao> refeicaoList;
}
