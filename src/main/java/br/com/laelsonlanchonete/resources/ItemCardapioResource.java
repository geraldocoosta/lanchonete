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

import br.com.laelsonlanchonete.entities.ItemCardapio;
import br.com.laelsonlanchonete.utils.Authorization;
import io.vertx.core.http.HttpServerRequest;

@Path("itemCardapio")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ItemCardapioResource {

	@Context
	HttpServerRequest request;

	@GET
	public Response getAll() {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.ok(ItemCardapio.listAll()).build();
	}

	@POST
	@Transactional
	public Response createAndamento(ItemCardapio itemCardapio) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		itemCardapio.id = null;
		itemCardapio.dataHoraCadastro = LocalDateTime.now(ZoneId.systemDefault());
		itemCardapio.persist();
		return Response.ok(itemCardapio).build();
	}

	@PUT
	@Transactional
	public Response modificaAndamento(ItemCardapio itemCardapio) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Optional<ItemCardapio> itemCardapioOptional = ItemCardapio.findByIdOptional(itemCardapio.id);
		if (!itemCardapioOptional.isPresent()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		ItemCardapio itemCardapioParaAlterar = itemCardapioOptional.get();

		itemCardapioParaAlterar.produtos = itemCardapio.produtos;
		itemCardapioParaAlterar.nome = itemCardapio.nome;
		itemCardapioParaAlterar.valorItem = itemCardapio.valorItem;
		return Response.ok(itemCardapio).build();
	}
	
	@DELETE
	@Transactional
	public Response apagarAndamento(Integer id) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		ItemCardapio.delete("id", id);
		return Response.ok().build();
	}

}