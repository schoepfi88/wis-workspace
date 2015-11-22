package resources;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.Sqlite;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Sqlite db;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        db = Sqlite.getInstance();
    }
    
    public Register(Sqlite db){
    	super();
    	this.db = db;
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usrn = request.getParameter("name");
		String pass = request.getParameter("password");
		int success = 0;
		try {
			success = db.register(usrn, pass);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		if (success > 0) {
			Resource.setFeedback("Registration successfull");
			response.sendRedirect("/webshop");
		} else {
			Resource.setError("Registration failed");
			response.sendRedirect("/webshop/register.jsp");
		}
		
	}

}
