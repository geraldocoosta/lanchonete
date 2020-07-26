package br.com.laelsonlanchonete.resources;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.laelsonlanchonete.entities.Pedido;
import io.vertx.core.http.HttpServerRequest;

@Path("/pedido")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PedidoResource {

    @Context
    HttpServerRequest request;

    @GET
    public Response getAll(){
        if (request.getCookie("LOGIN") == null || !request.getCookie("LOGIN").getValue().equals("true")){
            return Response.status(Status.BAD_REQUEST).build();
        }
        return Response.ok(Pedido.listAll()).build();
    }
    
    @POST
    public Response createPedido(Pedido pedido) {
        if (request.getCookie("LOGIN") == null || !request.getCookie("LOGIN").getValue().equals("true")){
            return Response.status(Status.BAD_REQUEST).build();
        }
        pedido.persist();
        return Response.ok(pedido).build();
    }

}
