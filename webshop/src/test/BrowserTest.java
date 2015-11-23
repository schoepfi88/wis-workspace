package test;



import java.util.Random;
import java.util.concurrent.TimeUnit;



import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.annotations.Test;



import db.Sqlite;

import org.junit.Assert;


public class BrowserTest {
	private static final String ID_LOGIN = "loginNavBar";
	private static final String ID_CREATE_ITEM = "createItemNavBar";
	private static final String ID_CREATE_CAT = "createCatNavBar";
	private static final String ID_HOME = "homeNavBar";
	private static final String ID_LOGOUT = "logoutNavBar";
	private static final String URL_PAGE = "http://localhost:8080/webshop/";
	private static final String URL_CREATE_CAT = "http://localhost:8080/webshop/createCategory.jsp";
	private static final String URL_CREATE_ITEM = "http://localhost:8080/webshop/create.jsp";
	private static final String URL_REGISTER = "http://localhost:8080/webshop/register.jsp";
	private static final String URL_LOGIN = "http://localhost:8080/webshop/login.jsp";
	

	private static final int MAX_PAGE_LOADING_TIME = 30000;
	private static final int MAX_IMPLICIT_WAIT = 10000;
	private static final int MAX_IMPLICIT_WAIT_JS = 5000;
	private static final int NUM_ITEMS = 4;

	private WebDriver driver;

	@BeforeClass
	public void startBrowser() {
		driver = new FirefoxDriver();
		driver.manage().timeouts()
			.pageLoadTimeout(MAX_PAGE_LOADING_TIME, TimeUnit.MILLISECONDS)
			.implicitlyWait(MAX_IMPLICIT_WAIT, TimeUnit.MILLISECONDS)
			.setScriptTimeout(MAX_IMPLICIT_WAIT_JS, TimeUnit.MILLISECONDS);
	}

	@AfterClass
	public void closeBrowser() {
		if (driver != null) {
			driver.close();
		}
	}

	// Test methods

	@Test
	public void testLoginLink() {
		driver.get(URL_PAGE);

		final WebElement loginElement = driver.findElement(By.id(ID_LOGIN));
		loginElement.click();
	

		final String result = driver.getCurrentUrl();
		Assert.assertEquals(result, "http://localhost:8080/webshop/login.jsp");
	}
	@Test
	public void testCreateItemLink() {
		driver.get(URL_PAGE);

		final WebElement loginElement = driver.findElement(By.id(ID_CREATE_ITEM));
		loginElement.click();
		
		
		

		final String result = driver.getCurrentUrl();
		Assert.assertEquals(result, "http://localhost:8080/webshop/create.jsp");
	}
	

	@Test
	public void testCreateCatLink() {
		driver.get(URL_PAGE);

		final WebElement loginElement = driver.findElement(By.id(ID_CREATE_CAT));
		loginElement.click();
	

		final String result = driver.getCurrentUrl();
		Assert.assertEquals(result, "http://localhost:8080/webshop/categories.jsp");
	}
	
	
	@Test
	public void testHomeLink() {
		driver.get(URL_PAGE);

		final WebElement loginElement = driver.findElement(By.id(ID_HOME));
		loginElement.click();
	

		final String result = driver.getCurrentUrl();
		Assert.assertEquals(result, "http://localhost:8080/webshop/");
	}
	
	
	@Test
	public void testLogoutNavBar(){
		logoutHelper();
		WebElement userNavBar = driver.findElement(By.id("userNavBar"));
		String result = userNavBar.getText();
		Assert.assertEquals("guest", result);
		
	}
	
	@Test
	public void testLogoutFeedback(){
		logoutHelper();
		WebElement feedback = driver.findElement(By.id("feedback"));
		String result = feedback.getText();
		Assert.assertEquals("Logout successfull", result);
	}
	
	
	@Test
	public void testLoginNavBar(){
		//make sure no user is logged in
		logoutHelper();
		//login Process
		loginHelper("bal", "123");
		//on main page again
		WebElement userNavBar = driver.findElement(By.id("userNavBar"));
		String result = userNavBar.getText();
		Assert.assertEquals("bal", result);
		
		
		}
	
	@Test
	public void testLoginFeedback(){
		//make sure no user is logged in
		logoutHelper();
		//login Process
		loginHelper("bal", "123");
		//on main page again
		WebElement feedback = driver.findElement(By.id("feedback"));
		String result = feedback.getText();
		Assert.assertEquals("Login successfull", result);
		
		
	}
	@Test
	public void allItemsLoaded(){
		int size = -1;
		try {
			size = Sqlite.getInstance().getItems().size();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.get(URL_PAGE);
		int numItems =driver.findElements(By.className("row")).size();
		Assert.assertEquals(size , numItems -1);
	}
	
	
	
	@Test
	public void createItem(){
		logoutHelper();
		driver.get(URL_CREATE_ITEM);
		final WebElement title = driver.findElement(By.name("title"));
		final WebElement description = driver.findElement(By.name("description"));
		final WebElement price = driver.findElement(By.name("price"));
		//final WebElement category = driver.findElement(By.name("category"));
		final WebElement submit = driver.findElement(By.name("submit"));
		Random rd = new Random();
		int num = rd.nextInt(1000);
		title.sendKeys("Testitem"+num);
		description.sendKeys("Just a description for an Item: random Number:"+num);
		price.sendKeys(Integer.toString(rd.nextInt(10000)));
		submit.click();
		final WebElement feedback = driver.findElement(By.id("feedback"));
		Assert.assertEquals("Item successfully created", feedback.getText() );
		
		}
	
	@Test
	public void createCategory(){
		driver.get(URL_CREATE_CAT);
		final WebElement name = driver.findElement(By.name("name"));
		final WebElement description = driver.findElement(By.name("description"));
		final WebElement submit = driver.findElement(By.name("submit"));
		
		name.sendKeys("TestCategory");
		description.sendKeys("Describtionasjdsjkdjkajk");
		submit.click();
		final WebElement feedback = driver.findElement(By.id("feedback"));
		Assert.assertEquals("Category successfully created", feedback.getText() );
	}
	
	
	private void logoutHelper(){
		driver.get(URL_PAGE);
		
		final WebElement logoutElement = driver.findElement(By.id(ID_LOGOUT));
		logoutElement.click();
		
	}
	private void loginHelper(String username, String password){
		driver.get(URL_PAGE);
		final WebElement loginElement = driver.findElement(By.id(ID_LOGIN));
		loginElement.click();
		//in login.jsp
		final WebElement nameInputField = driver.findElement(By.name("name"));
		final WebElement pwInputField = driver.findElement(By.name("password"));
		final WebElement submitButton = driver.findElement(By.name("submit"));
		//valid Login
		nameInputField.sendKeys(username);
		pwInputField.sendKeys(password);
		submitButton.click();
	}

}
