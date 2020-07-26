package br.com.laelsonlanchonete.resources;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

import br.com.laelsonlanchonete.entities.Categoria;
import br.com.laelsonlanchonete.utils.Authorization;
import io.vertx.core.http.HttpServerRequest;

@Path("categoria")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CategoriaResource {

	@Context
	HttpServerRequest request;

	@GET
	public Response getAll() {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.ok(Categoria.listAll()).build();
	}

	@POST
	@Transactional
	public Response createCategoria(Categoria categoria) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		categoria.id = null;
		categoria.dataHoraCadastro = LocalDateTime.now(ZoneId.systemDefault());
		categoria.persist();
		return Response.ok(categoria).build();
	}

	@PUT
	@Transactional
	public Response modificaCategoria(Categoria categoria) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Optional<Categoria> categoriaOptional = Categoria.findByIdOptional(categoria.id);
		if (!categoriaOptional.isPresent()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Categoria categoriaParaAlterar = categoriaOptional.get();

		categoriaParaAlterar.nome = categoria.nome;
		
		return Response.ok(categoria).build();
	}
	
	@DELETE
	@Transactional
	public Response apagarCategoria(Integer id) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Categoria.delete("id", id);
		return Response.ok().build();
	}

}