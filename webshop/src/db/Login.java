package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;
import resources.Resource;


/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String dbPath = new DatabasePath().getPath();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String usrn = request.getParameter("name");
		String pass = request.getParameter("password");
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");

			connection = DriverManager
					.getConnection(dbPath);
			PreparedStatement pstmt = connection
					.prepareStatement("Select * from user where user_name like ? and password like ?;");
			pstmt.setString(1, usrn);
			pstmt.setString(2, pass);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Resource.setFeedback("Login successfull");
				response.sendRedirect("/webshop/index.jsp");
				User user = User.getInstance();
				user.logIn(rs.getInt(1), rs.getString(2), rs.getInt(4));
			} else {
				Resource.setError("Login failed");
				response.sendRedirect("/webshop/login.jsp");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
	}

}
