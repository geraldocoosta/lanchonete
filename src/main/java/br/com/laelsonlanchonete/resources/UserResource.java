package br.com.laelsonlanchonete.resources;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.laelsonlanchonete.dtos.UserDTO;
import br.com.laelsonlanchonete.entities.Usuario;
import br.com.laelsonlanchonete.utils.CookieUtil;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserResource {
	
	@POST
	@Path("login")
	public Response logar(Usuario usuario) {
		Usuario usuarioBuscado = Usuario.find("nome", usuario.nome).firstResult();

		if (usuarioBuscado == null || !usuarioBuscado.pass.equals(usuario.pass)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		return Response.ok(new UserDTO(usuarioBuscado)).cookie(CookieUtil.createCookieLogin()).build();
	}

	@POST
	@Path("logout")
	public Response logout() {
		return Response.ok().header("LOGIN", false).build();
	}

}
