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

import org.eclipse.jetty.http.HttpStatus;

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
		Item item = new GsonBuilder().create().fromJson(jsonString, Item.class);
		ResponseBuilder response = null;
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
	public Response deleteItem(@PathParam("id") int id) throws ClassNotFoundException {
		
		ResponseBuilder response = Response.status(200);
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
	public Response newCat(String jsonString) throws IOException, ClassNotFoundException {
		Category cat = new GsonBuilder().create().fromJson(jsonString, Category.class);
		boolean success = db.createCategory(cat);
		ResponseBuilder response = null;
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
	public Response deleteCategory(@PathParam("id") int id) throws ClassNotFoundException {
		ResponseBuilder response = Response.status(200);
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
	public Response newComment(String json, @PathParam("id") int item_id) throws IOException, ClassNotFoundException {
		Comment comment = new GsonBuilder().create().fromJson(json, Comment.class);
		ResponseBuilder response = null;
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
	public Response deleteComment(@PathParam("id") int itemID, @PathParam("cid") int commID) throws ClassNotFoundException {
		ResponseBuilder response = null;
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