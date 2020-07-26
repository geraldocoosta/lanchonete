package br.com.laelsonlanchonete.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "usuario")
public class Usuario extends PanacheEntityBase{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
	public String nome;
	public String pass;
	public boolean admin;
}
