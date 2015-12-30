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
import models.Item;
import models.User;
import models.ActiveUser;
import models.Category;
import models.Comment;

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
	@Path("/item")
	@Produces({MediaType.APPLICATION_JSON })
	public List<Item> getItems() throws ClassNotFoundException {
		ArrayList<Item> items = db.getItems();
		return items;
	}
	
	@POST
	@Path("/item")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newItem(String jsonString,
			@CookieParam(value = "JSESSIONID") String token,
			@CookieParam(value = "user") String user) throws ClassNotFoundException {
		boolean allowed = false;
		if (user != null)
			user = user.split("%22")[1];
		try {
			allowed = db.checkAuth(user, token) && db.checkPriv(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseBuilder response = null;
		
		if (!allowed){
			response = Response.status(HttpStatus.METHOD_NOT_ALLOWED_405);
			return response.build();
		}
		Item item = new GsonBuilder().create().fromJson(jsonString, Item.class);
		JsonObject json = new JsonObject();
		boolean success = db.createItem(item);
		if (success){
			response = Response.status(HttpStatus.CREATED_201);
			json.addProperty("feedback", "Item successfully created");
			json.addProperty("success", success);
		} else {
			response = Response.status(HttpStatus.CONFLICT_409);
			json.addProperty("feedback", "Item not created");
			json.addProperty("success", success);
		}
		response.entity(json.toString());
		return response.build();
	}

	@DELETE
	@Path("/item/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItem(@PathParam("id") int id,
			@CookieParam(value = "JSESSIONID") String token,
			@CookieParam(value = "user") String user) throws ClassNotFoundException {
		boolean allowed = false;
		if (user != null)
			user = user.split("%22")[1];
		try {
			allowed = db.checkAuth(user, token) && db.checkPriv(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseBuilder response = null;
		
		if (!allowed){
			response = Response.status(HttpStatus.METHOD_NOT_ALLOWED_405);
			return response.build();
		}
		
		response = Response.status(200);
		JsonObject json = new JsonObject();
		int rA = db.deleteItem(id); //rA = rows affected
		if(rA >0){
			json.addProperty("feedback", "Item successfully deleted");
			json.addProperty("success", true);
		}else{
			json.addProperty("feedback", "Delete not successfull");
			json.addProperty("success", false);
		}
		response.entity(json.toString());
		return response.build();
	}
	
	@GET
	@Path("/category")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getCategories(){
		return db.getCategories();
	}
	
	@GET
	@Path("/category/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Category getCategory(@PathParam("id") int id){
		return db.getCategory(id);
	}
	
	@POST
	@Path("/category")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newCat(String jsonString, 
			@CookieParam(value = "JSESSIONID") String token,
			@CookieParam(value = "user") String user) throws ClassNotFoundException {
		boolean allowed = false;
		if (user != null)
			user = user.split("%22")[1];
		try {
			allowed = db.checkAuth(user, token) && db.checkPriv(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseBuilder response = null;
		
		if (!allowed){
			response = Response.status(HttpStatus.METHOD_NOT_ALLOWED_405);
			return response.build();
		}
		Category cat = new GsonBuilder().create().fromJson(jsonString, Category.class);
		boolean success = db.createCategory(cat);
		JsonObject json = new JsonObject();
		if (success){
			response = Response.status(HttpStatus.CREATED_201);
			json.addProperty("feedback", "Category successfully created");
			json.addProperty("success", success);
		} else {
			response = Response.status(HttpStatus.FORBIDDEN_403);
			json.addProperty("feedback", "Category not created");
			json.addProperty("success", success);
		}
		response.entity(json.toString());
		return response.build();
	}
	
	@DELETE
	@Path("/category/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCategory(@PathParam("id") int id,
			@CookieParam(value = "JSESSIONID") String token,
			@CookieParam(value = "user") String user) throws ClassNotFoundException {
		boolean allowed = false;
		if (user != null)
			user = user.split("%22")[1];
		try {
			allowed = db.checkAuth(user, token) && db.checkPriv(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseBuilder response = null;
		
		if (!allowed){
			response = Response.status(HttpStatus.METHOD_NOT_ALLOWED_405);
			return response.build();
		}
		response = Response.status(200);
		JsonObject json = new JsonObject();
		int rA = db.deleteCategory(id); //rA = rows affected
		if(rA >0){
			json.addProperty("feedback", "Category successfully deleted");
			json.addProperty("success", true);
		}else{
			json.addProperty("feedback", "Delete not successfull");
			json.addProperty("success", false);
		}
		response.entity(json.toString());
		return response.build();
	}
	
	@POST
	@Path("/item/{id}/comment")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newComment(String json, @PathParam("id") int item_id,
			@CookieParam(value = "JSESSIONID") String token,
			@CookieParam(value = "user") String user) throws ClassNotFoundException {
		boolean allowed = false;
		if (user != null)
			user = user.split("%22")[1];
		try {
			allowed = db.checkAuth(user, token);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseBuilder response = null;
		
		if (!allowed){
			response = Response.status(HttpStatus.METHOD_NOT_ALLOWED_405);
			return response.build();
		}
		Comment comment = new GsonBuilder().create().fromJson(json, Comment.class);
		comment.setItemID(item_id);
		JsonObject jsonO = new JsonObject();
		int success = db.createComment(comment);
		if (success >=0 ){
			response = Response.status(HttpStatus.CREATED_201);
			jsonO.addProperty("feedback", "Comment successfully created");
			jsonO.addProperty("success", true);
			jsonO.addProperty("itemID", item_id);
			jsonO.addProperty("cID", success);
		} else {
			response = Response.status(HttpStatus.CONFLICT_409);
			jsonO.addProperty("feedback", "Comment not created");
			jsonO.addProperty("success", false);
		}
		response.entity(jsonO.toString());
		return response.build();
	}
	
	@DELETE
	@Path("/item/{id}/comment/{cid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteComment(@PathParam("id") int itemID, @PathParam("cid") int commID,
			@CookieParam(value = "JSESSIONID") String token,
			@CookieParam(value = "user") String user) throws ClassNotFoundException {
		boolean allowed = false;
		if (user != null)
			user = user.split("%22")[1];
		try {
			allowed = db.checkAuth(user, token) && db.checkPriv(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseBuilder response = null;
		if (!allowed){
			response = Response.status(HttpStatus.METHOD_NOT_ALLOWED_405);
			return response.build();
		}
		JsonObject json = new JsonObject();
		int rA = db.deleteComment(itemID, commID); //rA = rows affected
		if(rA >0){
			response = Response.status(HttpStatus.OK_200);
			json.addProperty("feedback", "Comment successfully deleted");
			json.addProperty("success", true);
		}else{
			response = Response.status(HttpStatus.NOT_FOUND_404);
			json.addProperty("success", false);
			json.addProperty("feedback", "Delete not successfull");
		}
		response.entity(json.toString());
		return response.build();
	}
	
	@GET
	@Path("/item/{id}/comment")
	@Produces({ MediaType.APPLICATION_JSON })
	public ArrayList<Comment> getComments(@PathParam("id") int itemID){
		ArrayList<Comment> comments = db.getComments(itemID);
		return comments;
		
	}
	
	@GET
	@Path("/item/{id}/comment/{cid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Comment getComment(@PathParam("id") int itemID, @PathParam("cid") int commID){
		return db.getComment(itemID, commID);
		
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
	@Path("/auth/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fbAuth(@PathParam("code") String code, @CookieParam(value = "JSESSIONID") String token){
		ResponseBuilder response = Response.status(200);
		JsonObject jsonUserData = new JsonObject();
		FacebookLogin fbLogin = new FacebookLogin();
		String accessToken = fbLogin.getAccessToken(code);
		// login success
		if (accessToken != null){
			FacebookData fbData = new FacebookData(accessToken);
			String userData = fbData.getFacebookData();
			jsonUserData = fbData.getUserData(userData);
			ArrayList<String> userDataDB = null;
			try {
				db.register(jsonUserData.get("name").getAsString(), accessToken); 	// accessToken is saved as password in DB
				userDataDB = db.login(jsonUserData.get("name").getAsString(), accessToken, token);	// login at server and save session id
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			jsonUserData.addProperty("feedback", "Login successfully");
			jsonUserData.addProperty("success", true);
			User user = new User();
			user.setUser(Integer.parseInt(userDataDB.get(0)), userDataDB.get(1), Integer.parseInt(userDataDB.get(2)), token);
			ActiveUser.getInstance().addUser(user);
			jsonUserData.addProperty("name", user.getUsername());
			jsonUserData.addProperty("priv", user.getPrivilege());
			jsonUserData.addProperty("auth", token);
		} // login fail 
		else {
			jsonUserData.addProperty("feedback", "Login failed");
			jsonUserData.addProperty("success", false);
		}
		response.entity(jsonUserData.toString());
		return response.build();
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(String json, @CookieParam(value = "JSESSIONID") String token){
		ResponseBuilder response = Response.status(200);
		JsonObject jsonUser = new Gson().fromJson(json, JsonObject.class);
		String usr = jsonUser.get("name").getAsString();
		String pass = jsonUser.get("password").getAsString();
		
		ArrayList<String> userData = null;
		JsonObject jsonObj = new JsonObject();
		try {
			userData = db.login(usr, pass, token);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (userData.size() > 0) {
			jsonObj.addProperty("feedback", "Login successfully");
			jsonObj.addProperty("success", true);
			User user = new User();
			user.setUser(Integer.parseInt(userData.get(0)), userData.get(1), Integer.parseInt(userData.get(2)), token);
			ActiveUser.getInstance().addUser(user);
			jsonObj.addProperty("name", user.getUsername());
			jsonObj.addProperty("priv", user.getPrivilege());
			jsonObj.addProperty("auth", token);
		} else {
			jsonObj.addProperty("feedback", "Login failed");
			jsonObj.addProperty("success", false);
		}
			
		response.entity(jsonObj.toString());
		return response.build();
	}
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(String json){
		ResponseBuilder response = Response.status(200);
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("feedback", "Logout successfully");
		jsonObj.addProperty("success", true);
		response.entity(jsonObj.toString());
		return response.build();
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(String json, @CookieParam(value = "JSESSIONID") String token){
		
		ResponseBuilder response = Response.status(200);
		JsonObject jsonUser = new Gson().fromJson(json, JsonObject.class);
		String usr = jsonUser.get("name").getAsString();
		String pass = jsonUser.get("password").getAsString();
		ArrayList<String> userData = null;
		JsonObject jsonObj = new JsonObject();
	    int success = 0;
		try {
			success = db.register(usr, pass);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		if (success > 0) {
			jsonObj.addProperty("feedback", "Registration successfully");
			jsonObj.addProperty("success", true);
		} else {
			jsonObj.addProperty("feedback", "Registration failed");
			jsonObj.addProperty("success", false);
		}
			
		response.entity(jsonObj.toString());
		return response.build();
	}
	
	@POST
	@Path("/checkAuth")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkAuth(String json, @CookieParam(value = "JSESSIONID") String token){
		
		ResponseBuilder response = Response.status(200);
		JsonObject jsonUser = new Gson().fromJson(json, JsonObject.class);
		String usr = jsonUser.get("name").getAsString();
		JsonObject jsonObj = new JsonObject();
	    boolean success = false;
		try {
			success = db.checkAuth(usr, token);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		if (success) {
			jsonObj.addProperty("feedback", "");
			jsonObj.addProperty("success", true);
		} else {
			jsonObj.addProperty("feedback", "");
			jsonObj.addProperty("success", false);
		}
			
		response.entity(jsonObj.toString());
		return response.build();
	}
	
} 