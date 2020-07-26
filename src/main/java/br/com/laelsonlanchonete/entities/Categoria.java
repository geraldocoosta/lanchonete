package br.com.laelsonlanchonete.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name= "categoria")
public class Categoria extends PanacheEntityBase {

    public String nome;
    public LocalDateTime dataHoraCadastro;
}
