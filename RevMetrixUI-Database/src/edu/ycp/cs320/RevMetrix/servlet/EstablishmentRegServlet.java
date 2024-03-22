package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.RevMetrix.controller.EstablishmentRegController;
import edu.ycp.cs320.RevMetrix.model.Establishment;

public class EstablishmentRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("establishmentReg Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/establishmentReg.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("establishmentReg Servlet: doPost");
		
		
		// holds the error message text, if there is any
		String errorMessage = null;
		Establishment model = new Establishment(req.getParameter("establishmentName"),req.getParameter("email"),req.getParameter("address") );
		EstablishmentRegController controller = new EstablishmentRegController();
		controller.setModel(model);
		// result of calculation goes here
		// decode POSTed form parameters and dispatch to controller
		try {
			String first = req.getParameter("establishmentName");
			String second = req.getParameter("email");
			String third = req.getParameter("address");
			// check for errors in the form data before using is in a calculation
			if (first.equals("") || second.equals("") || third.equals("")) {
				errorMessage = "Please fill out all fields";
			}
			// otherwise, data is good, do the calculation
			// must create the controller each time, since it doesn't persist between POSTs
			// the view does not alter data, only controller methods should be used for that
			// thus, always call a controller method to operate on the data
			else {
				controller.setStrings(first, second, third);
				errorMessage = "Submitted";
			}
		} catch (NumberFormatException e) {
			errorMessage = "Invalid String";
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
		req.getRequestDispatcher("/_view/establishmentReg.jsp").forward(req, resp);
	}

	
	// gets double from the request with attribute named s
}
