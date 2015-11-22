package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import db.Sqlite;
import models.Category;
import models.Comment;
import models.Item;
import models.User;
import resources.Login;
import resources.Logout;
import resources.Register;
import resources.Resource;


public class ServerAPI extends JerseyTest {
	private Resource resource;
	private Sqlite db;
	private Item i;
	private Category cat;
	private Comment c;
	
	// register packages and resource class with mocked database
	@Override
    protected Application configure() {
        ResourceConfig resourcecfg = new ResourceConfig();
        resourcecfg.packages("org.codehaus.*");
		resourcecfg.register(ServletContainer.class);
		resourcecfg.register(JacksonFeature.class);
		db = Mockito.mock(Sqlite.class);
		// mock database data
		cat = new Category();
		cat.setId(999);
		cat.setDescription("test description");
		cat.setName("test name");
		Category cat2 = new Category();
		cat2.setDescription("test description2");
		cat2.setName("test name2");
		i = new Item();
		i.setTitle("Apple");
		i.setAuthor("team9");
		i.setCategory("cat");
		i.setCreatedAt("time");
		i.setDescription("desc");
		Item i2 = new Item();
		i2.setTitle("Strawberry");
		i2.setAuthor("csl");
		c = new Comment();
		c.setMessage("test message");
		c.setTitle("test title");
		Comment c2 = new Comment();
		c2.setMessage("test message2");
		c2.setTitle("test title2");
		ArrayList<Item> items = new ArrayList<>();
		items.add(i);
		items.add(i2);
		ArrayList<Category> cats = new ArrayList<>();
		cats.add(cat);
		cats.add(cat2);
		ArrayList<Comment> coms = new ArrayList<>();
		coms.add(c);
		coms.add(c2);
    	try {
    		// mock the database
			Mockito.when(db.getItem(1)).thenReturn(i);
			Mockito.when(db.getItems()).thenReturn(items);
			Mockito.when(db.createItem(i)).thenReturn(true);
			Mockito.when(db.deleteItem(1)).thenReturn(1);
			Mockito.when(db.getCategory(999)).thenReturn(cat);
			Mockito.when(db.getCategories()).thenReturn(cats);
			Mockito.when(db.createCategory(cat)).thenReturn(true);
			Mockito.when(db.getComment(1,1)).thenReturn(c);
			Mockito.when(db.getComments(1)).thenReturn(coms);
			Mockito.when(db.createComment(c)).thenReturn(true);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resource = new Resource(db);
		resourcecfg.register(resource);
		return resourcecfg;
    }
	
    
    /***********************************************************
     * API tests
     ***********************************************************/
    // get item with id 1
    @Test
    public void testGetItem() throws ClassNotFoundException {
    	String jsonString = target("resource/item/1").request().get(String.class);
    	JsonObject item = new Gson().fromJson(jsonString, JsonObject.class);
    	//Mockito.verify(db, Mockito.times(1)).getItem(1);
        assertEquals("Apple", item.get("title").getAsString());
    }
    
    // get items
    @Test
    public void testGetItems(){
    	String jsonString = target("resource/item").request().get(String.class);
    	JsonArray items = new Gson().fromJson(jsonString, JsonArray.class);
    	//Mockito.verify(db, Mockito.times(1)).getItem(1);
    	JsonObject item0 = items.get(0).getAsJsonObject();
    	JsonObject item1 = items.get(1).getAsJsonObject();
        assertEquals("Apple", item0.get("title").getAsString());
        assertEquals("Strawberry", item1.get("title").getAsString());
        assertEquals("team9", item0.get("author").getAsString());
        assertEquals("csl", item1.get("author").getAsString());
    }
    
    // delete item with id 1
    @Test
    public void testDeleteItem(){
    	String jsonString = target("resource/item/1").request().delete().readEntity(String.class);
    	JsonObject feedback = new Gson().fromJson(jsonString, JsonObject.class);
    	assertEquals(feedback.get("feedback").getAsInt(), 200);
    }
    
    // get category with id 999
    @Test
    public void testGetCategory() throws ClassNotFoundException {
    	String jsonString = target("resource/category/999").request().get(String.class);
    	JsonObject cat = new Gson().fromJson(jsonString, JsonObject.class);
    	//Mockito.verify(db, Mockito.times(1)).getItem(1);
        assertEquals("test description", cat.get("description").getAsString());
        assertEquals("test name", cat.get("name").getAsString());
    }
    
    // get categories
    @Test
    public void testGetCategories(){
    	String jsonString = target("resource/category").request().get(String.class);
    	JsonArray categories = new Gson().fromJson(jsonString, JsonArray.class);
    	//Mockito.verify(db, Mockito.times(1)).getItem(1);
    	JsonObject cat0 = categories.get(0).getAsJsonObject();
    	JsonObject cat1 = categories.get(1).getAsJsonObject();
    	assertEquals("test name", cat0.get("name").getAsString());
        assertEquals("test name2", cat1.get("name").getAsString());
        assertEquals("test description", cat0.get("description").getAsString());
        assertEquals("test description2", cat1.get("description").getAsString());
    }
    
    // get comment (id = 1) of item (id = 1)
    @Test
    public void testGetComment() throws ClassNotFoundException {
    	String jsonString = target("resource/item/1/comment/1").request().get(String.class);
    	JsonObject cat = new Gson().fromJson(jsonString, JsonObject.class);
    	//Mockito.verify(db, Mockito.times(1)).getItem(1);
        assertEquals("test message", cat.get("message").getAsString());
        assertEquals("test title", cat.get("title").getAsString());
    }
    
    // get comments of item with id 1
    @Test
    public void testGetComments() {
    	String jsonString = target("resource/item/1/comment").request().get(String.class);
    	JsonArray comments = new Gson().fromJson(jsonString, JsonArray.class);
    	//Mockito.verify(db, Mockito.times(1)).getItem(1);
    	JsonObject com0 = comments.get(0).getAsJsonObject();
    	JsonObject com1 = comments.get(1).getAsJsonObject();
    	assertEquals("test title", com0.get("title").getAsString());
        assertEquals("test title2", com1.get("title").getAsString());
        assertEquals("test message", com0.get("message").getAsString());
        assertEquals("test message2", com1.get("message").getAsString());
    }
    
    // creation of category
    @Test
    public void testCreateCategory() {
    	String inputString = new Gson().toJson(cat);
    	Entity<String> entity = Entity.entity(inputString, MediaType.APPLICATION_JSON);
    	String jsonString = target("resource/category").request().post(entity).readEntity(String.class);
    	JsonObject cat = new Gson().fromJson(jsonString, JsonObject.class);
    	assertEquals(cat.get("feedback").getAsInt(), 200);
    }
    
    // creation of item
    @Test
    public void testCreateItem() {
    	String inputString = new Gson().toJson(i);
    	Entity<String> entity = Entity.entity(inputString, MediaType.APPLICATION_JSON);
    	String jsonString = target("resource/item").request().post(entity).readEntity(String.class);
    	JsonObject i = new Gson().fromJson(jsonString, JsonObject.class);
    	assertEquals(i.get("feedback").getAsInt(), 200);
    	// test feedback and error variable on server
    	String resourceFeedback = Resource.getFeedback();
    	Resource.setError("ERROR");
    	String resourceError = Resource.getFeedback();
    	int resourceLoadTrigger = Resource.getLoadTrigger();
    	int resourceErrorTrigger = Resource.getErrorTrigger();
    	int resourceFeedbackTrigger = Resource.getFeedbackTrigger();
    	Resource.incLoadTrigger();
    	int resourceLoadTrigger2 = Resource.getLoadTrigger();
    	assertEquals("Item successfully created", resourceFeedback);
    	assertEquals("ERROR", resourceError);
    	assertEquals(resourceLoadTrigger, 0);
    	assertEquals(resourceLoadTrigger2, 1);
    	assertEquals(resourceErrorTrigger, 1);
    	assertEquals(resourceFeedbackTrigger, 1);
    }
    
    // create comment (from item with id = 1)
    @Test
    public void testCreateComment() {
    	String inputString = new Gson().toJson(c);
    	Entity<String> entity = Entity.entity(inputString, MediaType.APPLICATION_JSON);
    	String jsonString = target("resource/item/1/comment").request().post(entity).readEntity(String.class);
    	JsonObject i = new Gson().fromJson(jsonString, JsonObject.class);
    	assertEquals(i.get("feedback").getAsInt(), 200);
    }
    
    
    // test login
    @Test 
    public void testLogin() throws SQLException, ClassNotFoundException, ServletException, IOException {
    	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);       
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        ArrayList<String> userData = new ArrayList<>();
        userData.add("1");	// id
        userData.add("me");	// name
        userData.add("1");	// privileges
        Mockito.when(db.login("me", "secret")).thenReturn(userData);
        Mockito.when(request.getParameter("name")).thenReturn("me");
        Mockito.when(request.getParameter("password")).thenReturn("secret");
        new Login(db).doPost(request,response);
        assertEquals("Login successfull", Resource.getFeedback());
        assertEquals(User.getInstance().getUsername(), "me");
    }
    
    // test logout
    @Test 
    public void testLogout() throws ServletException, IOException, ClassNotFoundException, SQLException {
    	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);       
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        new Logout().doGet(request,response);
        assertEquals("Logout successfull", Resource.getFeedback());
        assertEquals(User.getInstance().getUsername(), "guest");
    }
    
    // test login fail
    @Test 
    public void testLoginFail() throws SQLException, ClassNotFoundException, ServletException, IOException {
    	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);       
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        ArrayList<String> userData = new ArrayList<>();
        userData.add("1");	// id
        userData.add("me");	// name
        userData.add("1");	// privileges
        Mockito.when(db.login("me", "secret")).thenReturn(userData);
        Mockito.when(request.getParameter("name")).thenReturn("you");
        Mockito.when(request.getParameter("password")).thenReturn("secret");
        new Login(db).doPost(request,response);
        assertEquals("Login failed", Resource.getFeedback());
        assertEquals(User.getInstance().getUsername(), "guest");
    }
    
    // test registration
    @Test
    public void testRegister() throws ClassNotFoundException, SQLException, ServletException, IOException{
    	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);       
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(request.getParameter("name")).thenReturn("me");
        Mockito.when(request.getParameter("password")).thenReturn("secret");
        Mockito.when(db.register("me", "secret")).thenReturn(1);
        new Register(db).doPost(request,response);
        assertEquals("Registration successfull", Resource.getFeedback());
    }
    
 // test registration fail
    @Test
    public void testRegisterFail() throws ClassNotFoundException, SQLException, ServletException, IOException{
    	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);       
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(request.getParameter("name")).thenReturn("me");
        Mockito.when(request.getParameter("password")).thenReturn("secret");
        Mockito.when(db.register("me", "secret")).thenReturn(0);
        new Register(db).doPost(request,response);
        assertEquals("Registration failed", Resource.getFeedback());
    }
    
}