package br.com.laelsonlanchonete.resources;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.laelsonlanchonete.entities.User;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserResource {

	@GET	
	public Response getUser() {
		List<User> users = User.listAll();
		return Response.ok(users).build();
	}
	
	@POST
	@Transactional
	public Response saveUser(User userSave) {
		userSave.persist();
		return Response.ok(userSave).build();
	}
	
}
