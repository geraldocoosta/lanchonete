package br.com.laelsonlanchonete.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.laelsonlanchonete.enums.Estados;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "endereco")
public class Endereco extends PanacheEntityBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	public String logradouro;
	public String bairro;
	public String numero;
	public String complemento;
	public String cidade;
	public Estados estado;

}
