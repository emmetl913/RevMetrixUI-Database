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

public class AccountSignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String errorMessage = null;

		
		System.out.println("Account SignUp Servlet: doGet");	
		HttpSession session = req.getSession();
		//Account kevin=  new Account("Kevin", "Kevin1","KevinsEmail@gmail.com");
		Account acc = null;
		session.setAttribute("currAccount", acc);
//		ArrayList<Account> accList = new ArrayList<Account>();
//		if (session.isNew() ){
//		accList.add(kevin);
//		session.setAttribute("accountListKey", accList);
//		}
//		if(accList.isEmpty()) {
//			accList.add(kevin);
//			session.setAttribute("accountListKey", accList);		
//		}
		// call JSP to generate empty form
		errorMessage = null;
		req.setAttribute("errorMessage", errorMessage);
		req.getRequestDispatcher("/_view/signUp.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Account SignUp Servlet: doPost");
		
		// holds the error message text, if there is any
		HttpSession session = req.getSession();

		String errorMessage = null;
		Account model = new Account(null, null, null);
		AccountController controller = new AccountController();
		controller.setModel(model);
		// result of calculation goes here
		// decode POSTed form parameters and dispatch to controller
		boolean signedUp = false;
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String password2 = req.getParameter("password2");
		Account kevin=  new Account("Kevin", "Kevin1","KevinsEmail@gmail.com");

//		ArrayList<Account> accList = new ArrayList<Account>();
//		if (session.isNew() ){
//		accList.add(kevin);
//		session.setAttribute("accountListKey", accList);
//		}
//		if(accList.isEmpty()) {
//			accList.add(kevin); //kevin fooooooooooreverrrrrrrr (dont worry he lives on in the database)
//			session.setAttribute("accountListKey", accList);		
//		}
		
		// check for errors in the form data before using is in a calculation
		if (username.length() < 5 || password.length()<5 || password2.length() < 5) {
			errorMessage = "Please enter a username and/or password that are both longer than 5 characters";
		}
		
	
		
		//Check to see if the account already exists in the database by username
		if(errorMessage == null) {
			List<Account> existingAccount = controller.getAccountByUsername(username);
			if(existingAccount != null) {
				
				errorMessage = "Account with username already exists";
				
			}
		}
		
		//Check to see if the account already exists in the database by email
		if(errorMessage == null) {
			List<Account> existingAccountEmail = controller.getAccountByEmail(email);
			if(existingAccountEmail != null) {
				
				System.out.println(existingAccountEmail.get(0).getEmail());
				errorMessage = "Account with email already exists";
				
			}
		}
		if(errorMessage == null && !signedUp) {
			if(password.equals(password2)) {
				controller.signUp(username, password, email); //create an account with SignUp
				
				//accList = (ArrayList<Account>)session.getAttribute("accountListKey");
				controller.insertAccountinDB(email, password, username);
				
				//Pretty sure "accList" is deprecated now because the accs are stored in the db
				//accList.add(new Account(username, password, email));
				//System.out.println(accList.get(1).getUsername());

				signedUp = true;
			}
			else {
				errorMessage = "Passwords do not match.";
			}
		}
		
		if (req.getParameter("signUp") != null && signedUp) {
			//session.setAttribute("accountListKey", accList);	
			//System.out.println(accList.get(0).getUsername());
			//System.out.println(accList.get(1).getUsername());
			req.setAttribute("errorMessage", errorMessage);
			req.setAttribute("game", model);

			req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
		}
		else if (req.getParameter("logIn") != null) {
			//accList = (ArrayList<Account>)session.getAttribute("accountListKey");
			//session.setAttribute("accountListKey", accList);
			//System.out.println(accList.get(0).getUsername());
		//	System.out.println(accList.get(1).getUsername());
			req.setAttribute("errorMessage", errorMessage);
			req.setAttribute("game", model);
			req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
			
		}
		else{
			req.setAttribute("errorMessage", errorMessage);
			req.setAttribute("game", model);
			req.getRequestDispatcher("/_view/signUp.jsp").forward(req, resp);
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
