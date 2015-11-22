package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import models.Category;
import models.Comment;
import models.Item;
import models.User;

public class ModelTest {
	// models
	Item i;
	Category c;
	User u;
	Comment com;
	
	@Before
	public void init(){
		i = new Item();
		c = new Category();
		u = User.getInstance();
		com = new Comment();
	}

	@Test
	public void testCategory() {
		c.setDescription("d");
		c.setId(1);
		c.setName("n");
		assertEquals("d", c.getDescription());
		assertEquals(1, c.getId());
		assertEquals("n", c.getName());
	}
	
	@Test 
	public void testItem(){
		i.setAuthor("a");
		i.setCategory("cat");
		i.setCreatedAt("t");
		i.setDescription("d");
		i.setId(1);
		i.setPrice(1f);
		i.setTitle("t");
		assertEquals("d", i.getDescription());
		assertEquals(1, i.getId());
		assertEquals("t", i.getTitle());
		assertEquals("a", i.getAuthor());
		assertEquals(1f, i.getPrice(), 0.00001f);
		assertEquals("t", i.getCreatedAt());
		assertEquals("cat", i.getCategory());
	}
	
	@Test
	public void testComment(){
		com.setAuthor("a");
		com.setMessage("m");
		com.setCreatedAt("t");
		com.setId(1);
		com.setItemID(1);
		com.setTitle("title");
		assertEquals("m", com.getMessage());
		assertEquals(1, com.getId());
		assertEquals(1, com.getItemID());
		assertEquals("title", com.getTitle());
		assertEquals("a", com.getAuthor());
		assertEquals("t", com.getCreatedAt());
	}
	
	@Test
	public void testUser(){
		assertEquals("guest", u.getUsername());
		assertEquals(0, u.getId());
		assertEquals(1, u.getPrivilege());
		u.setUser(2, "testuser", 3);
		assertEquals("testuser", u.getUsername());
		assertEquals(2, u.getId());
		assertEquals(3, u.getPrivilege());
		u.unsetUser();
		assertEquals("guest", u.getUsername());
		assertEquals(0, u.getId());
		assertEquals(1, u.getPrivilege());
	}

}
