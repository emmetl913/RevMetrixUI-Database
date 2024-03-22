package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.RevMetrix.controller.AccountController;
import edu.ycp.cs320.RevMetrix.model.Account;

public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Account Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Account Servlet: doPost");
		
		boolean validLogin = false;

		// holds the error message text, if there is any
		String errorMessage = null;
		Account model = new Account(null, null, null);
		AccountController controller = new AccountController();
		controller.setModel(model);
		model.setUsername("Kevin");
		model.setPassword("Kevin1");
		// result of calculation goes here
		// decode POSTed form parameters and dispatch to controller
			
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		// check for errors in the form data before using is in a calculation
		if (username.length() < 5 || password.length()<5) {
			errorMessage = "Please enter a username and/or password that are both longer than 5 characters";
		}
		// otherwise, data is good, do the log in
		// must create the controller each time, since it doesn't persist between POSTs
		// the view does not alter data, only controller methods should be used for that
		// thus, always call a controller method to operate on the data
		else if(!model.getUsername().equals(username) || !model.getPassword().equals(password)) {
				errorMessage = "Username or password is incorrect.";
			}
		else {
			errorMessage = "I (Sir RevMetrix III) will register this username to this password";
			model.setUsername(username);
			model.setPassword(password);
			validLogin = controller.getValidLogin(username, password);
			}

		if (req.getParameter("logIn") != null && validLogin) {
			req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
		}
		if (req.getParameter("registerButton") != null) {
			req.getRequestDispatcher("/_view/signUp.jsp").forward(req, resp);
		}
		// Add parameters as request attributes
		// this creates attributes named "first" and "second for the response, and grabs the
		// values that were originally assigned to the request attributes, also named "first" and "second"
		// they don't have to be named the same, but in this case, since we are passing them back
		// and forth, it's a good idea
		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("game", model);
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
	}
}
