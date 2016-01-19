package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import db.Neo4jAdapter;
import models.User;

@Path("/api")
public class Resource {
	
	@Path("/person")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON })
	@POST
	public Response createPerson(String json){
		ResponseBuilder response = Response.status(200);
		User jsonUser = new Gson().fromJson(json, User.class);
		Neo4jAdapter neo = Neo4jAdapter.getInstance();
		neo.open();
		neo.createPerson(jsonUser);
		neo.close();
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("success", true);
		response.entity(jsonObj.toString());
		return response.build();
	
	}

	
	}




