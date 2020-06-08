package br.com.laelsonlanchonete.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "user")
public class User extends PanacheEntity{

	public String nome;
	public String pass;
	
}
