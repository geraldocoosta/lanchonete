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

import br.com.laelsonlanchonete.entities.Produto;
import br.com.laelsonlanchonete.utils.Authorization;
import io.vertx.core.http.HttpServerRequest;

@Path("produto")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ProdutoResource {

	@Context
	HttpServerRequest request;

	@GET
	public Response getAll() {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.ok(Produto.listAll()).build();
	}

	@POST
	@Transactional
	public Response createProduto(Produto produto) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		produto.id = null;
		produto.dataHoraCadastro = LocalDateTime.now(ZoneId.systemDefault());
		produto.persist();
		return Response.ok(produto).build();
	}

	@PUT
	@Transactional
	public Response modificaProduto(Produto produto) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Optional<Produto> produtoOptional = Produto.findByIdOptional(produto.id);
		if (!produtoOptional.isPresent()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Produto produtoParaAlterar = produtoOptional.get();

		produtoParaAlterar.nome = produto.nome;
		produtoParaAlterar.descricao = produto.descricao;
		produtoParaAlterar.quantidade = produto.quantidade;
		produtoParaAlterar.categoria = produto.categoria;
		
		return Response.ok(produto).build();
	}
	
	@DELETE
	@Transactional
	public Response apagarProduto(Integer id) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Produto.delete("id", id);
		return Response.ok().build();
	}

}