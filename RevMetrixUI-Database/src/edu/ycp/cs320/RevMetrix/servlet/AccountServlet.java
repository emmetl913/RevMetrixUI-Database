package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import edu.ycp.cs320.RevMetrix.controller.AccountController;
import edu.ycp.cs320.RevMetrix.model.Account;

public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static boolean validLogin = false;
	
	public static boolean validLogin() {
		return validLogin;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Account Servlet: doGet");	
		
		HttpSession session = req.getSession();
		Account kevin=  new Account("Kevin", "Kevin1","KevinsEmail@gmail.com");

		ArrayList<Account> accList = new ArrayList<Account>();
		
		if (session.isNew() ){
		accList.add(kevin);
		session.setAttribute("accountListKey", accList);
		}
		if(accList == null) {
			accList.add(kevin);
			session.setAttribute("accountListKey", accList);
		}
		if(accList.isEmpty()) {
			accList.add(kevin);
			session.setAttribute("accountListKey", accList);		
		}
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
		validLogin = false;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Account Servlet: doPost");
		validLogin = false;
		

		// holds the error message text, if there is any
		String errorMessage = null;
		Account model = new Account(null, null, null);
		AccountController controller = new AccountController();
		controller.setModel(model);
		
		// result of calculation goes here
		// decode POSTed form parameters and dispatch to controller
		HttpSession session = req.getSession();
	    long createTime = session.getCreationTime();
		long lastAccessTime = session.getLastAccessedTime();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		Account kevin=  new Account("Kevin", "Kevin1","KevinsEmail@gmail.com");

		ArrayList<Account> accList =  new ArrayList<Account>();

		if (session.isNew() ){
		accList = new ArrayList<Account>();
		accList.add(kevin);
		session.setAttribute("accountListKey", accList);
		}
		if(accList == null) {
			accList.add(kevin);
			session.setAttribute("accountListKey", accList);
		}
		if(accList.isEmpty()) {
			accList.add(kevin);
			session.setAttribute("accountListKey", accList);		
		}
		else {

		}
		for(Account account: accList) {
			
			 if(!validLogin) {
				controller.setModel(account);
				
				if(controller.getValidLogin(username, password)) {
					validLogin = controller.getValidLogin(username, password);
					session.setAttribute("currAccount", account);
				}
				System.out.println("user: " +account.getUsername()+ ", password: " + account.getPassword()
				+ ", isValidLogin: "+ controller.getValidLogin(username, password));

			}
			if(validLogin) {
				break;
			}
					
		}
		if(!validLogin) {
			errorMessage = "Username or password is incorrect.";
		}
		
		// check for errors in the form data before using is in a calculation
		if (username.length() < 5 || password.length()<5) {
			errorMessage = "Please enter a username and/or password that are both longer than 5 characters";
		}
		

		if (req.getParameter("logIn") != null && validLogin) {
			req.setAttribute("errorMessage", errorMessage);
			req.setAttribute("game", model);
			session.setAttribute("accountListKey", accList);	
			req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
		}
		else if (req.getParameter("registerButton") != null) {
			req.setAttribute("errorMessage", errorMessage);
			req.setAttribute("game", model);
			session.setAttribute("accountListKey", accList);	
			req.getRequestDispatcher("/_view/signUp.jsp").forward(req, resp);
		}
		else {
			req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
		}
		// Add parameters as request attributes
		// this creates attributes named "first" and "second for the response, and grabs the
		// values that were originally assigned to the request attributes, also named "first" and "second"
		// they don't have to be named the same, but in this case, since we are passing them back
		// and forth, it's a good idea
		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
			

		// Forward to view to render the result HTML document
		

	}
}
