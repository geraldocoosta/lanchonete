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
import br.com.laelsonlanchonete.entities.MaterialCarga;
import br.com.laelsonlanchonete.utils.Authorization;
import io.vertx.core.http.HttpServerRequest;

@Path("materialCarga")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class MaterialCargaResource {

	@Context
	HttpServerRequest request;

	@GET
	public Response getAll() {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.ok(MaterialCarga.listAll()).build();
	}

	@POST
	@Transactional
	public Response createMaterialCarga(MaterialCarga materialCarga) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		materialCarga.id = null;
		materialCarga.dataHoraCadastro = LocalDateTime.now(ZoneId.systemDefault());
		materialCarga.persist();
		return Response.ok(materialCarga).build();
	}
	
	@POST
	@Path("andamento")
	@Transactional
	public Response adicionaMaterialCarga(Integer idMaterial, Andamentos andamento) {
		MaterialCarga material = MaterialCarga.findById(idMaterial);
		material.andamentos.add(andamento);
		return Response.ok(material).build();
	}

	@PUT
	@Transactional
	public Response modificaMaterialCarga(MaterialCarga materialCarga) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Optional<MaterialCarga> materialCargaOptional = MaterialCarga.findByIdOptional(materialCarga.id);
		if (!materialCargaOptional.isPresent()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		MaterialCarga materialCargaParaAlterar = materialCargaOptional.get();

		materialCargaParaAlterar.nome = materialCarga.nome;
		materialCargaParaAlterar.quantidade = materialCarga.quantidade;
		materialCargaParaAlterar.andamentos = materialCarga.andamentos;
		return Response.ok(materialCarga).build();
	}
	
	@DELETE
	@Transactional
	public Response apagarMaterialCarga(Integer id) {
		if (Authorization.isAuthorized(request)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		MaterialCarga.delete("id", id);
		return Response.ok().build();
	}

}