package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.controller.EstablishmentRegController;
import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Establishment;


public class EstablishmentRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(!AccountServlet.validLogin()) {
            req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
        }

		System.out.println("establishmentReg Servlet: doGet");	
		
		HttpSession session = req.getSession();

		// If first visit: new session id
		//Get model and userID from jsp
	    
		Account acc = (Account) session.getAttribute("currAccount");
		Establishment model = new Establishment();
		EstablishmentRegController controller = new EstablishmentRegController(acc.getAccountId());
		controller.setModel(model);
		
		ArrayList<Establishment> establishments = controller.getEstablishments();
		
		try {
			for(Establishment esta : establishments) {
				System.out.println(esta.getEstablishmentName());
			}
		}catch(NullPointerException e) {
			
		}
		
		establishments = controller.getEstablishments();
        
		// Set the ArrayList as a request attribute
		req.setAttribute("esta", establishments);
		req.setAttribute("estaArray", controller.getEstablishments());
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/establishmentReg.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(!AccountServlet.validLogin()) {
            req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
        }
		
		System.out.println("establishmentReg Servlet: doPost");
		
		
		// holds the error message text, if there is any
		String errorMessage = null;
		HttpSession session = req.getSession();

		Account acc = (Account) session.getAttribute("currAccount");
		System.out.print(acc.getAccountId());
		Establishment model = new Establishment();
		EstablishmentRegController controller = new EstablishmentRegController(acc.getAccountId());
		controller.setModel(model);
				
				   // Check if this is new comer on your Webpage.
				//end session shenanigans
		
        ArrayList<Establishment> establishments = controller.getEstablishments(); //get ball ArrayList from session updated model
		if(establishments == null) {
			establishments = new ArrayList<Establishment>();
			establishments.add(new Establishment(0, 0, "FirstBall", "FirstAddress"));
		}
		//on button press
		String newEstablishmentName = req.getParameter("establishmentName");
		String newEstablishmentAddress = req.getParameter("address");
		String removeEstablishmentName = req.getParameter("removeEstablishmentName");
		
		if (req.getParameter("submitEstab") != null ) {
			//System.out.println(model.getBallAtIndex(0).getName());
			controller.addEstablishment(acc.getAccountId(),newEstablishmentName, newEstablishmentAddress);
		}
		
		if(req.getParameter("submitRemoveEstab") != null) {
			//controller.removeBall(removeBallName);
			 controller.removeEstablishment(acc.getAccountId(), removeEstablishmentName);
		}
		
        establishments = controller.getEstablishments(); 
		
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("esta", establishments);
		req.setAttribute("estaArray", controller.getEstablishments());
		req.getRequestDispatcher("/_view/establishmentReg.jsp").forward(req, resp);
	}

	
	// gets double from the request with attribute named s
}