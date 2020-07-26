package br.com.laelsonlanchonete.dtos;

import br.com.laelsonlanchonete.entities.Usuario;

public class UserDTO {

	public String nome;
	public boolean admin;
	
	public UserDTO(Usuario usuario) {
		this.nome = usuario.nome;
		this.admin = usuario.admin;
	}
}
