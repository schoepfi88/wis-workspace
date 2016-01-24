package resources;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.eclipse.jetty.http.HttpStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import db.Sqlite;
import models.Transaction;
import models.User;
import models.ActiveUser;
import models.Account;

@Path("/resource")
public class Resource {
	private Sqlite db;
	
	
	public Resource() {
		this.db = Sqlite.getInstance();
	}
	// only for test usage
	public Resource(Sqlite db){
		this.db = db;
	}
	
	@GET
	@Path("/facebook/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fbLogin(){
		System.out.println("test");
		ResponseBuilder response = null;
		response = Response.status(200);
		JsonObject json = new JsonObject();
		FacebookLogin fbLogin = new FacebookLogin();
		json.addProperty("url", fbLogin.getFBAuthUrl());
		response.entity(json.toString());
		return response.build();
	}
	
	@GET
	@Path("/account")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Account> getAccounts(){
		int owner = 0;
		return db.getAccounts(owner);
	}
	
	
	@POST
	@Path("/account")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAccount(String json){
		System.out.println("create");
		db.createAccount(json, 0);
		return Response.status(200).build();
	}

} 