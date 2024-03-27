package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.model.EstablishmentArray;
import edu.ycp.cs320.RevMetrix.controller.BallArsenalController;
import edu.ycp.cs320.RevMetrix.controller.EstablishmentRegController;
import edu.ycp.cs320.RevMetrix.controller.EventController;
import edu.ycp.cs320.RevMetrix.model.Event;
import edu.ycp.cs320.RevMetrix.model.EventArray;

public class EventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Event Servlet: doGet");	
		HttpSession session = req.getSession();
	    long createTime = session.getCreationTime();
		   
		// Get last access time of this Webpage.
		long lastAccessTime = session.getLastAccessedTime();
		String userIDKey = new String("userID");
		String userID = new String("ABCD");

		   // Check if this is new comer on your Webpage.
		String eventKey = new String("eventKey");
		EventArray model = new EventArray();
		
		// If first visit: new session id
		if (session.isNew() ){
	      session.setAttribute(userIDKey, userID);
		  session.setAttribute(eventKey,  model);
		} 
		//Get model and userID from jsp
		userID = (String)session.getAttribute(userIDKey);

		//controller.setModel(model);
		Establishment e1 = new Establishment("Colony Park Lanes & Games", "1900 Pennsylvania Ave, York, PA 17404");

		
		ArrayList<Event> events = model.getEvents();
        if(model.getEvents() == null) {
        	events = new ArrayList<Event>();
        	events.add(new Event("FirstName", "FirstType", 0, e1));
		}
        else {
        	events = model.getEvents();
        }
        EstablishmentArray estaModel = new EstablishmentArray();
		EstablishmentRegController estaController = new EstablishmentRegController();
		estaController.setModel(estaModel);
        ArrayList<Establishment> estabs = estaModel.getEstablishments();
		// Set the ArrayList as a request attribute
		req.setAttribute("event", events);
		req.setAttribute("esta", estabs);
		session.setAttribute(eventKey, model); //update session model
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/event.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Event Servlet: doPost");
		String errorMessage = null;

		EventArray model = new EventArray();
		EventController controller = new EventController();
				
		// Get session creation time.
				HttpSession session = req.getSession();
			    long createTime = session.getCreationTime();
				   
				// Get last access time of this Webpage.
				long lastAccessTime = session.getLastAccessedTime();
				String userIDKey = new String("userID");
				String userID = new String("ABCD");

				String eventKey = new String("eventKey");

				   // Check if this is new comer on your Webpage.
				if (session.isNew() ){
			      session.setAttribute(userIDKey, userID);
				  session.setAttribute(eventKey,  model);
				} 
				model = (EventArray)session.getAttribute(eventKey);
				userID = (String)session.getAttribute(userIDKey);
				//end session shenanigans
		
		controller.setModel(model);
		Establishment e1 = new Establishment(null, null);

		
		ArrayList<Event> events = model.getEvents();
        if(model.getEvents() == null) {
        	events = new ArrayList<Event>();
        	events.add(new Event("FirstName", "FirstType", 0, e1));
		}
		//on button press
		String newEventName = req.getParameter("eventName");
		int newStanding = Integer.valueOf(req.getParameter("standing"));
		String EstaName = req.getParameter("establishment");
		Establishment newEstab = e1;
		

        String type = null;
        
        EstablishmentArray estaModel = new EstablishmentArray();
		EstablishmentRegController estaController = new EstablishmentRegController();
		estaController.setModel(estaModel);
        ArrayList<Establishment> estabs = estaModel.getEstablishments(); //get ball ArrayList from session updated model
		
        Iterator<Establishment> iterator = estabs.iterator();
	    for (int x = 0; x < estabs.size(); x++) {
	    	iterator.hasNext();
	    	Establishment estab = iterator.next();
	        if (estab.getEstablishmentName().equals(EstaName)) {
	        	newEstab = estaModel.getEstablishmentAtIndex(x);
	        }
	    }
        
		// Set the ArrayList as a request attribute
		
		
		try {
			
			if (req.getParameter("newType").equals("Practice")) {
				type = "practice";
			} else if (req.getParameter("newType").equals("Tournament")) {
				type = "tournament";
			} else if (req.getParameter("newType").equals("Leauge")) {
				type = "leauge";
			} else {
				type = null;
			}
			
		controller.addEvent(newEventName, type, newStanding, newEstab);
		System.out.print(""+newEventName+", " + type+", " +newStanding+", " +newEstab.getEstablishmentName());

		}catch(NullPointerException e) {
			System.out.print(""+newEventName+", " + type+", " +newStanding+", " +newEstab.getEstablishmentName());
			errorMessage = "Invalid Input";
		}
			
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("event", events);
		req.setAttribute("esta", estabs);

		
		req.getRequestDispatcher("/_view/event.jsp").forward(req, resp);
	}
}
