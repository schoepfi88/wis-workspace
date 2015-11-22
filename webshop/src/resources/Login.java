package resources;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.Sqlite;
import models.User;


/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Sqlite db;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		db = Sqlite.getInstance();
	}
	
	public Login(Sqlite db){
		super();
		this.db = db;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String usrn = request.getParameter("name");
		String pass = request.getParameter("password");
		ArrayList<String> userData = null;
		try {
			userData = db.login(usrn, pass);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (userData.size() > 0) {
			Resource.setFeedback("Login successfull");
			User user = User.getInstance();
			user.setUser(Integer.parseInt(userData.get(0)), userData.get(1), Integer.parseInt(userData.get(2)));
			response.sendRedirect("/webshop/index.jsp");
		} else {
			User.getInstance().unsetUser();
			Resource.setError("Login failed");
			response.sendRedirect("/webshop/login.jsp");
		}
	}

}
