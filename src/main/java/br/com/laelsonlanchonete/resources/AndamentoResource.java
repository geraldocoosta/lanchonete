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

import br.com.laelsonlanchonete.entities.Andamentos;
import br.com.laelsonlanchonete.utils.Authorization;
import io.vertx.core.http.HttpServerRequest;

@Path("andamento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AndamentoResource {

	@Context
	HttpServerRequest request;

	@GET
	public Response getAll() {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.ok(Andamentos.listAll()).build();
	}

	@POST
	@Transactional
	public Response createAndamento(Andamentos andamento) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		andamento.id = null;
		andamento.dataHora = LocalDateTime.now(ZoneId.systemDefault());
		andamento.persist();
		return Response.ok(andamento).build();
	}

	@PUT
	@Transactional
	public Response modificaAndamento(Andamentos andamento) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Optional<Andamentos> andamentoOptional = Andamentos.findByIdOptional(andamento.id);
		if (!andamentoOptional.isPresent()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Andamentos andamentoParaAlterar = andamentoOptional.get();

		andamentoParaAlterar.descricao = andamento.descricao;
		
		return Response.ok(andamento).build();
	}
	
	@DELETE
	@Transactional
	public Response apagarAndamento(Integer id) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Andamentos.delete("id", id);
		return Response.ok().build();
	}

}