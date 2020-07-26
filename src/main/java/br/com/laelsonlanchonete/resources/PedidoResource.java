package br.com.laelsonlanchonete.resources;

import java.math.BigDecimal;
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

import br.com.laelsonlanchonete.entities.Cliente;
import br.com.laelsonlanchonete.entities.Pedido;
import br.com.laelsonlanchonete.utils.Authorization;
import io.vertx.core.http.HttpServerRequest;

@Path("/pedido")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PedidoResource {

	@Context
	HttpServerRequest request;

	@GET
	public Response getAll() {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.ok(Pedido.listAll()).build();
	}

	@POST
	@Transactional
	public Response createPedido(Pedido pedido) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		pedido.id = null;
		if (pedido.cliente != null && pedido.cliente.id != null) {
			pedido.cliente = Cliente.findById(pedido.cliente.id);
		}
		pedido.dataHoraPedido = LocalDateTime.now(ZoneId.systemDefault());
		pedido.valorTotal = pedido.itensPedido.stream().map(i -> i.valorItem).reduce(new BigDecimal("0"),
				BigDecimal::add);
		pedido.persist();
		return Response.ok(pedido).build();
	}

	@PUT
	@Transactional
	public Response modificaPedido(Pedido pedido) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Optional<Pedido> pedidoOptional = Pedido.findByIdOptional(pedido.id);
		if (!pedidoOptional.isPresent()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Pedido pedidoParaAlterar = pedidoOptional.get();

		pedidoParaAlterar.itensPedido = pedido.itensPedido;
		if (pedido.cliente != null && pedido.cliente.id != null) {
			pedidoParaAlterar.cliente = Cliente.findById(pedido.cliente.id);
		} else {
			pedidoParaAlterar.cliente = pedido.cliente;
		}
		pedidoParaAlterar.valorTotal = pedido.valorTotal;
		pedidoParaAlterar.tipoEntrega = pedido.tipoEntrega;
		pedidoParaAlterar.valorTotal = pedido.itensPedido.stream().map(i -> i.valorItem).reduce(new BigDecimal("0"),
				BigDecimal::add);

		return Response.ok(pedido).build();
	}

	@DELETE
	@Transactional
	public Response apagarPedido(Integer id) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Pedido.delete("id", id);
		return Response.ok().build();
	}

}
