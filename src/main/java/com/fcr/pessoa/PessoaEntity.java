package com.fcr.pessoa;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class PessoaEntity {
    
    @Id
    private Integer id;

    private String nome;

    private Integer idade;

    private Character sexo;

}
