package resources;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import db.Sqlite;
import models.Item;
import models.Category;
import models.Comment;

@Path("/resource")
public class Resource {
	private static String feedback = "";
	private static int feedbackTrigger = 0;
	private static int loadTrigger = 0;
	private static int errorTrigger = 0;
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
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/item/{id}")
	public Item getItem(@PathParam("id") int id) throws ClassNotFoundException {
		Item item = db.getItem(id);
		return item;
	}
	
	@POST
	@Path("/item")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newItem(String jsonString) throws IOException, ClassNotFoundException {
		Item item = new Item();
		System.out.println(jsonString);
		JsonObject jsonO = new Gson().fromJson(jsonString, JsonObject.class);
		item.setDescription(jsonO.get("description").getAsString());
		item.setTitle(jsonO.get("title").getAsString());
		item.setAuthor(jsonO.get("author").getAsString());
		item.setCategory(jsonO.get("category").getAsString());
		//System.out.println(price);
		item.setPrice(jsonO.get("price").getAsFloat());
		db.createItem(item);
		Resource.setFeedback("Item successfully created");
		ResponseBuilder response = Response.status(200);
		JsonObject json = new JsonObject();
		json.addProperty("feedback", 200);
		response.entity(json.toString());
		return response.build();
	}

	@DELETE
	@Path("/item/{id}") // maybe /item/delete/id???
	//@Produces(MediaType.TEXT_HTML)
	public void deleteItem(@PathParam("id") int id) throws ClassNotFoundException {
		//System.out.println("im in deleteItem in get");
		int rA = db.deleteItem(id); //rA = rows affected
		if(rA >0){
			Resource.setFeedback("Item successfully deleted");
		}else{
			Resource.setFeedback("Delete not successfull");
		}
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
	public Response newCat(String jsonString) throws IOException, ClassNotFoundException {
		Category cat = new Category();
		//System.out.println(jsonString);
		JsonObject jsonO = new Gson().fromJson(jsonString, JsonObject.class);
		String description = jsonO.get("description").getAsString();
		String name = jsonO.get("name").getAsString();
		cat.setDescription(description);
		cat.setName(name);
		db.createCategory(cat);
		ResponseBuilder response = Response.status(200);//Response.seeOther(URI.create("http://localhost:8080/webshop/createCategory.jsp"));
		//servletResponse.sendRedirect("../../createCategory.jsp");
		Resource.setFeedback("Category successfully created");
		JsonObject json = new JsonObject();
		json.addProperty("feedback", 200);
		response.entity(json.toString());
		return response.build();
	}
	
	
	
	@POST
	@Path("/item/{id}/comment")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newComment(String json, @PathParam("id") int item_id, 
			@Context HttpServletResponse servletResponse) throws IOException, ClassNotFoundException {
		//System.out.println(json);
		Comment comment = new GsonBuilder().create().fromJson(json, Comment.class);
		comment.setItemID(item_id);
		//System.out.println(item_id);
		db.createComment(comment);
		ResponseBuilder response = Response.status(200);//Response.seeOther(URI.create("http://localhost:8080/webshop/createCategory.jsp"));
		//servletResponse.sendRedirect("../../createCategory.jsp");
		Resource.setFeedback("Category successfully created");
		JsonObject jsonO = new JsonObject();
		jsonO.addProperty("feedback", 200);
		response.entity(jsonO.toString());
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
	
	public static String getFeedback(){
		return feedback;
	}

	public static void setFeedback(String feed){
		feedback = feed;
		feedbackTrigger = loadTrigger +1;
	}
	
	public static int getFeedbackTrigger(){
		return feedbackTrigger;
	}
	
	public static int getLoadTrigger(){
		return loadTrigger;
	}
	
	public static void incLoadTrigger(){
		loadTrigger++;
	}
	
	public static void setError(String error){
		feedback = error;
		errorTrigger = loadTrigger +1;
	}
	
	public static int getErrorTrigger(){
		return errorTrigger;
	}
} 