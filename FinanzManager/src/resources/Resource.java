package resources;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import db.Sqlite;
import models.Account;
import models.Transaction;

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
		ResponseBuilder response = null;
		response = Response.status(200);
		JsonObject json = new JsonObject();
		FacebookLogin fbLogin = new FacebookLogin();
		json.addProperty("url", fbLogin.getFBAuthUrl());
		response.entity(json.toString());
		return response.build();
	}
	
	@GET
	@Path("/account/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Account> getAccounts(@PathParam("code") String code){
		String user_id = getUserID(code);
		if (user_id != null)
			return db.getAccounts(user_id);
		else 
			return null;
	}
	
	@GET
	@Path("/transaction/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transaction> getTransactions(@PathParam("code") String code){
		String user_id = getUserID(code);
		if (user_id != null)
			return db.getTransactions(user_id);
		else 
			return null;
	}
	
	
	@POST
	@Path("/account")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAccount(String json){
		JsonObject jOb = new Gson().fromJson(json, JsonObject.class);
		String code = jOb.get("code").getAsString();
		String fbID = getUserID(code);
		if (fbID != null)
			db.createAccount(json, fbID);
		else 
			return Response.status(403).build();
		return Response.status(200).build();
	}
	
	@POST
	@Path("/transaction")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTransaction(String json){
		JsonObject jOb = new Gson().fromJson(json, JsonObject.class);
		String code = jOb.get("code").getAsString();
		String fbID = getUserID(code);
		if (fbID != null)
			db.createTransaction(json);
		else 
			return Response.status(403).build();
		return Response.status(200).build();
	}
	
	@GET
	@Path("/graph/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGraph(@PathParam("code") String code){
		FacebookLogin fbLogin = new FacebookLogin();
		String accessToken = fbLogin.getAccessToken(code);
		ResponseBuilder response = Response.status(200);
		JsonObject userJson = new JsonObject();
		if (accessToken != null){
			FacebookData fbData = new FacebookData(accessToken);
			String userData = fbData.getFacebookData();
			db.saveUser(userData);
			userJson = fbData.getUserData(userData);
			userJson.addProperty("success", true);
		} else {
			userJson.addProperty("success", false);
		}
		
		response.entity(userJson.toString());
		return response.build();
	}
	
	private String getUserID(String code){
		FacebookLogin fbLogin = new FacebookLogin();
		String accessToken = fbLogin.getAccessToken(code);
		JsonObject userJson = new JsonObject();
		if (accessToken != null){
			FacebookData fbData = new FacebookData(accessToken);
			String userData = fbData.getFacebookData();
			userJson = fbData.getUserData(userData);
			return userJson.get("id").getAsString();
		} else {
			return null;
		}
	}
} 