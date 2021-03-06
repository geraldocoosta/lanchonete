package br.com.laelsonlanchonete.resources;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.laelsonlanchonete.entities.Cliente;
import br.com.laelsonlanchonete.utils.Authorization;
import io.vertx.core.http.HttpServerRequest;

@Path("cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ClienteResource {

	@Context
	HttpServerRequest request;

	@GET
	public Response getAll() {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.ok(Cliente.listAll()).build();
	}

	@POST
	@Transactional
	public Response createCliente(Cliente cliente) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		cliente.id = null;
		if (cliente.enderecoCliente != null){
			cliente.enderecoCliente.id = null;
		}
		cliente.persist();
		return Response.ok(cliente).build();
	}

	@PUT
	@Transactional
	public Response modificaCliente(Cliente cliente) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Optional<Cliente> clienteOptional = Cliente.findByIdOptional(cliente.id);
		if (!clienteOptional.isPresent()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Cliente clienteParaAlterar = clienteOptional.get();

		clienteParaAlterar.nomeCliente = cliente.nomeCliente;
		clienteParaAlterar.telefoneCliente = cliente.telefoneCliente;
		clienteParaAlterar.enderecoCliente = cliente.enderecoCliente;
		
		return Response.ok(cliente).build();
	}
	
	@DELETE
	@Transactional
	public Response apagarCliente(Integer id) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Cliente.delete("id", id);
		return Response.ok().build();
	}

}