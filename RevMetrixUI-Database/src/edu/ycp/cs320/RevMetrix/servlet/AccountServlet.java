package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		
		//When we sign out, doGet of AccountServlet is ran
		//So we set the currentAccount to null here.
		Account acc = null;
		session.setAttribute("currAccount", acc);

		
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
		
		//make session
		HttpSession session = req.getSession();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		if(!username.equals("")) {
			validLogin = controller.getValidLogin(username, password);
			if(validLogin) {
				List<Account> newList = controller.getAccountByUsernameAndPassword(username, password);
				Account acc = newList.get(0);
				
				//set our current account to the successfully logged in account
				session.setAttribute("currAccount", acc);
			}
		}
		else if(!validLogin) {
			errorMessage = "Username or password is incorrect.";
		}
		
		// check for errors in the form data before using is in a calculation
		if (username.length() < 5 || password.length()<5) {
			//errorMessage = "Please enter a username and/or password that are both longer than 5 characters";
			errorMessage = " ";
		}
		

		if (req.getParameter("logIn") != null && validLogin) {
			req.setAttribute("errorMessage", errorMessage);
			req.setAttribute("game", model);
			//session.setAttribute("accountListKey", accList);	
			req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
		}
		else if (req.getParameter("registerButton") != null) {
			req.setAttribute("errorMessage", errorMessage);
			req.setAttribute("game", model);
			//session.setAttribute("accountListKey", accList);	
			req.getRequestDispatcher("/_view/signUp.jsp").forward(req, resp);
		}
		else {
			req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
		}
	}
}