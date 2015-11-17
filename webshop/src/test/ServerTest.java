package test;

import static org.junit.Assert.*;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import db.Sqlite;
import models.Category;
import models.Item;
import resources.Resource;


public class ServerTest extends JerseyTest {
	@Mock
	Sqlite db = Mockito.mock(Sqlite.class);
	
	@InjectMocks
	Resource r;
	
	@Before
	public void init() throws ClassNotFoundException{
		
		Category cat = new Category();
		cat.setId(999);
		cat.setDescription("test description");
		cat.setName("test name");
		Item i = new Item();
		i.setTitle("Apple");
    	Mockito.when(db.getItem(1)).thenReturn(i);
		Mockito.when(db.createCategory(cat)).thenReturn(true);
		Mockito.when(db.getCategory(999)).thenReturn(cat);
		
		
	}
	 
    @Override
    protected Application configure() {
        return new resources.Application();
    }
 
    @Test
    public void testGetItem() throws ClassNotFoundException {
    	
    	String jsonString = target("resource/item/1").request().get(String.class);
    	JsonObject item = new Gson().fromJson(jsonString, JsonObject.class);
    	Mockito.verify(db).getItem(1);
        //final String hello = target("resource/item").request().get(String.class);
        assertEquals("Apple", item.get("title").getAsString());
    }
    
    @Test
    public void testCreateCategory() {
    	Category newCat = new Category();
    	newCat.setName("test name");
    	newCat.setDescription("test description");
    	String inputString = new Gson().toJson(newCat);
    	Entity<String> entity = Entity.entity(inputString, MediaType.APPLICATION_JSON);
    	String jsonString = target("resource/category").request().post(entity).readEntity(String.class);
    	JsonObject cat = new Gson().fromJson(jsonString, JsonObject.class);
    	assertEquals(cat.get("feedback").getAsInt(), 200);
    }
}