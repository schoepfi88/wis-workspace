package resources;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
	@GET
	@Path("/item")
	@Produces({MediaType.APPLICATION_JSON })
	public List<Item> getItems() throws ClassNotFoundException {
		Sqlite db = Sqlite.getInstance();
		ArrayList<Item> items = db.getItems();
		return items;
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/item/{id}")
	public Item getItem(@PathParam("id") int id) throws ClassNotFoundException {
		Sqlite db = Sqlite.getInstance();
		Item item = db.getItem(id);
		return item;
	}
	
	@POST
	@Path("/item")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newItem(@FormParam("title") String title,
			@FormParam("description") String description,
			@FormParam("author") String author,
			@FormParam("category") String category,
			@FormParam("price") float price,
			@Context HttpServletResponse servletResponse) throws IOException, ClassNotFoundException {
		Item item = new Item();
		item.setDescription(description);
		item.setTitle(title);
		item.setAuthor(author);
		item.setCategory(category);
		System.out.println(price);
		item.setPrice(price);
		Sqlite.getInstance().createItem(item);
		servletResponse.sendRedirect("../../create.jsp");
		Resource.setFeedback("Item successfully created");
	}

	@DELETE
	@Path("/item/{id}") // maybe /item/delete/id???
	//@Produces(MediaType.TEXT_HTML)
	public void deleteItem(@PathParam("id") int id) throws ClassNotFoundException {
		System.out.println("im in deleteItem in get");
		Sqlite db = Sqlite.getInstance();
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
		return Sqlite.getInstance().getCategories();
	}
	
	@POST
	@Path("/category")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newCat(@FormParam("name") String name,
			@FormParam("description") String description) throws IOException, ClassNotFoundException {
		Category cat = new Category();
		cat.setDescription(description);
		cat.setName(name);
		
		Sqlite.getInstance().createCategory(cat);
		
		ResponseBuilder response = Response.seeOther(URI.create("http://localhost:8080/webshop/createCategory.jsp"));
		//servletResponse.sendRedirect("../../createCategory.jsp");
		Resource.setFeedback("Category successfully created");
		return response.build();
	}
	
	
	
	@POST
	@Path("/item/{id}/comment")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public void newComment(String json, @PathParam("id") int item_id, 
			@Context HttpServletResponse servletResponse) throws IOException, ClassNotFoundException {
		System.out.println(json);
		Comment comment = new GsonBuilder().create().fromJson(json, Comment.class);
		comment.setItemID(item_id);
		System.out.println(item_id);
		Sqlite.getInstance().createComment(comment);
		//servletResponse.sendRedirect("../../createCategory.jsp");
		//Resource.setFeedback("Comment successfully created");
	}
	
	@GET
	@Path("/item/{id}/comment")
	@Produces({ MediaType.APPLICATION_JSON })
	public ArrayList<Comment> getComments(@PathParam("id") int itemID){
		Sqlite db = Sqlite.getInstance();
		ArrayList<Comment> comments = db.getComments(itemID);
		return comments;
		
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