package test;

import static org.junit.Assert.*;
import java.io.IOException;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.JerseyTestNg;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Before;
import org.junit.Test;

import models.Category;
import models.Comment;
import models.Item;
import resources.Application;
import resources.Resource;
import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
public class ServerTest{
	
	@Test
	public void testUserFetchesSuccess() {
		expect().
			body("id", equalTo("12")).
			body("firstName", equalTo("Tim")).
			body("lastName", equalTo("Tester")).
			body("birthday", equalTo("1970-01-16T07:56:49.871+01:00")).
		when().
			get("/rest-test-tutorial/user/id/12");
	}
 
	@Test
	public void testUserNotFound() {
		expect().
			body(nullValue()).
		when().
			get("/rest-test-tutorial/user/id/666");
	}
	
	
	
	//Resource r = Mockito.mock(Resource.class);
//	Item i;
//	Comment c;
//	Category ca;
	
//	@Override
//	public Application configure(){
//		return new Application();
//	}
//	
//	@Override
//	protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
//	    return new GrizzlyWebTestContainerFactory();
//	}
//	@Override
//	protected DeploymentContext configureDeployment() {
//	    return ServletDeploymentContext.forPackages("resources,org.codehaus.*").build();
//	}
//	
//	@Override
//	protected void configureClient(ClientConfig config) {
//	    config.register(JacksonFeature.class);
//	}
	
	
//	@Before
//	public void setupResources() throws ClassNotFoundException, IOException{
//		//Mockito.when(request.getParameter("username")).thenReturn("me");
//		r.newCat("CategoryName", "CategoryDescription");
//		ca = r.getCategories().get(0);
//		assertTrue("create category test", ca.getName().equals("CategoryName"));
////		r.newItem("ItemTitleTest", "ItemDescriptionTest", "ItemAuthorTest", , price, servletResponse);
//	}
//	
//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
//	@Test
//	public void testSingleNode() throws Exception {
//	    final int hello = target("resource/item").request().get().getStatus();
//	    System.out.println(hello);
//	    assertEquals(200, hello);
//	}

}
