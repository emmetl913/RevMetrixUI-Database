package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.lab02.controller.EstablishmentRegController;
import edu.ycp.cs320.lab02.model.Establishment;
import edu.ycp.cs320.lab02.model.EstablishmentArray;


public class EstablishmentRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("establishmentReg Servlet: doGet");	
		
		HttpSession session = req.getSession();
	    long createTime = session.getCreationTime();
		   
		// Get last access time of this Webpage.
		long lastAccessTime = session.getLastAccessedTime();
		String userIDKey = new String("userID");
		String userID = new String("ABD");

		   // Check if this is new comer on your Webpage.
		String establishmentRegKey = new String("establishmentRegKey");
		EstablishmentArray model = (EstablishmentArray) session.getAttribute(establishmentRegKey);

		// If first visit: new session id
		if (session.isNew() ){
	      session.setAttribute(userIDKey, userID);
		  session.setAttribute(establishmentRegKey,  model);
		} 
		//Get model and userID from jsp
		//model = (EstablishmentArray)session.getAttribute(establishmentRegKey);

		userID = (String)session.getAttribute(userIDKey);

		//controller.setModel(model);
		
		if (model == null) {
		    model = new EstablishmentArray();
		    session.setAttribute(establishmentRegKey, model);
		}
		ArrayList<Establishment> establishments = model.getEstablishments();
		establishments = model.getEstablishments();

        
		// Set the ArrayList as a request attribute
		req.setAttribute("esta", establishments);
		session.setAttribute(establishmentRegKey, model); //update session model

		
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/establishmentReg.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("establishmentReg Servlet: doPost");
		
		
		// holds the error message text, if there is any
		String errorMessage = null;

		EstablishmentArray model = new EstablishmentArray();
		EstablishmentRegController controller = new EstablishmentRegController();
				
		// Get session creation time.
				HttpSession session = req.getSession();
			    long createTime = session.getCreationTime();
				   
				// Get last access time of this Webpage.
				long lastAccessTime = session.getLastAccessedTime();
				String userIDKey = new String("userID");
				String userID = new String("ABCD");

				String establishmentRegKey = new String("establishmentRegKey");
				
				   // Check if this is new comer on your Webpage.
				if (session.isNew() ){
			      session.setAttribute(userIDKey, userID);
				  session.setAttribute(establishmentRegKey,  model);
				} 
				model = (EstablishmentArray)session.getAttribute(establishmentRegKey);
				userID = (String)session.getAttribute(userIDKey);
				//end session shenanigans
		
		controller.setModel(model);
        ArrayList<Establishment> establishments = model.getEstablishments(); //get ball ArrayList from session updated model
		if(establishments == null) {
			establishments = new ArrayList<Establishment>();
			establishments.add(new Establishment("FirstBall", "FirstAddress"));
		}
		//on button press
		String newEstablishmentName = req.getParameter("establishmentName");
		String newEstablishmentAddress = req.getParameter("address");
		String removeEstablishmentName = req.getParameter("removeEstablishmentName");
		
		if (req.getParameter("submitEstab") != null ) {
			//System.out.println(model.getBallAtIndex(0).getName());
			controller.addEstablishment(newEstablishmentName, newEstablishmentAddress);
		}
		if(req.getParameter("submitRemoveEstab") != null) {
			//controller.removeBall(removeBallName);
			 Iterator<Establishment> iterator = establishments.iterator();
			    while (iterator.hasNext()) {
			    	Establishment establishment = iterator.next();
			        if (establishment.getEstablishmentName().equals(removeEstablishmentName)) {
			            iterator.remove(); // Remove the ball from the ArrayList
			            break; // Exit the loop after removing the ball
			        }
			    }
		}
		
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("esta", establishments);
		session.setAttribute(establishmentRegKey, model); //update session model
		
		req.getRequestDispatcher("/_view/establishmentReg.jsp").forward(req, resp);
	}

	
	// gets double from the request with attribute named s
}
