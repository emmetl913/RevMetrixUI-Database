package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.lab02.model.Establishment;
import edu.ycp.cs320.lab02.model.EstablishmentArray;
import edu.ycp.cs320.lab02.controller.EstablishmentRegController;
import edu.ycp.cs320.lab02.controller.EventController;
import edu.ycp.cs320.lab02.model.Event;
import edu.ycp.cs320.lab02.model.EventArray;

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
		
		ArrayList<Establishment> establishment = new ArrayList<>();
		Establishment e1 = new Establishment("Colony Park Lanes & Games", "1900 Pennsylvania Ave, York, PA 17404");

		
		ArrayList<Event> events = model.getEvents();
        if(model.getEvents() == null) {
        	events = new ArrayList<Event>();
        	events.add(new Event("FirstName", "FirstType", 0, e1));
		}
        else {
        	events = model.getEvents();
        }
        
		// Set the ArrayList as a request attribute
		req.setAttribute("event", events);
		session.setAttribute(eventKey, model); //update session model
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/event.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		System.out.println("Event Servlet: doPost");		
		
		// holds the error message text, if there is any
		String errorMessage = null;

		EventArray model = new EventArray();
		EventController controller = new EventController();
		controller.setModel(model);
				
		// Get session creation time.
		HttpSession session = req.getSession();
	    long createTime = session.getCreationTime();
				   
				// Get last access time of this Webpage
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
				
		ArrayList<Establishment> establishment = new ArrayList<>();
		Establishment e1 = new Establishment("Colony Park Lanes & Games", "1900 Pennsylvania Ave, York, PA 17404");
		
		controller.setModel(model);
        ArrayList<Event> events = model.getEvents(); //get ball ArrayList from session updated model
		if(events == null) {
			events = new ArrayList<Event>();
        	events.add(new Event("FirstName", "FirstType", 0, e1));
		}
		//on button press
		String newEventName = req.getParameter("eventName");
		
		String newType = null;
		
		
		if (req.getParameter("eventType") != null) {
			newType = "practice";
		} else if (req.getParameter("eventType") != null) {
			newType = "tournament";
		} else if (req.getParameter("eventType") != null) {
			newType = "leauge";
		} 
		
		String currentEstablishmentName = req.getParameter("establishment");
    	Integer newStanding = getIntegerFromParameter(req.getParameter("standing"));

		
		try {
			
        	newStanding = getIntegerFromParameter(req.getParameter("standing"));
    		currentEstablishmentName = req.getParameter("establishment");
    		
        }catch (NumberFormatException e) {
			errorMessage = "Invalid input";
		}

		EstablishmentArray Estamodel = new EstablishmentArray();
		EstablishmentRegController Estacontroller = new EstablishmentRegController();
		
		Estacontroller.setModel(Estamodel);
        ArrayList<Establishment> establishments = Estamodel.getEstablishments();
        
        Establishment currentEstablishment = e1;
		
        Iterator<Establishment> iterator = establishments.iterator();
        for(int x = 0; x < Estamodel.getNumberOfEstablishment(); x++) {
	    	Establishment establishment1 = iterator.next();
	        if (establishment1.getEstablishmentName().equals(currentEstablishmentName)) {
	        	currentEstablishment = Estamodel.getEstablishmentAtIndex(x);
	            break; // Exit the loop after removing the ball
	        }
	    }
        
        //output
        if (req.getParameter("Submit") != null ) {
			//System.out.println(model.getBallAtIndex(0).getName());
			controller.addEvent(newEventName, newType, newStanding, currentEstablishment);
        }
        
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("event", events);
		session.setAttribute(eventKey, model); //update session model
		req.getRequestDispatcher("/_view/event.jsp").forward(req, resp);
	}
	
	private Integer getIntegerFromParameter(String s) {
		if (s == null || s.equals("")) {
			return null;
		} else {
			return Integer.parseInt(s);
		}
	}
}
