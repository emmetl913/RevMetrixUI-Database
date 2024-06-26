package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.controller.EstablishmentRegController;
import edu.ycp.cs320.RevMetrix.controller.EventController;
import edu.ycp.cs320.RevMetrix.model.Event;

public class EventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(!AccountServlet.validLogin()) {
            req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
        }

		System.out.println("Event Servlet: doGet");	
		HttpSession session = req.getSession();		
		
		Account acc = (Account) session.getAttribute("currAccount");

        Establishment estaModel = new Establishment();
		EstablishmentRegController estaController = new EstablishmentRegController(acc.getAccountId());
		estaController.setModel(estaModel);
		
		Event model = new Event();
		EventController controller = new EventController(acc.getAccountId());
		controller.setModel(model);
		
        ArrayList<Establishment> estabs = estaController.getEstablishments();
        
		ArrayList<Event> events = controller.getEvents();
		// Set the ArrayList as a request attribute
		req.setAttribute("event", events);
		req.setAttribute("esta", estabs);
		
		//session.setAttribute("currEventID", );

		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/event.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(!AccountServlet.validLogin()) {
            req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
        }
		
		System.out.println("Event Servlet: doPost");
		String errorMessage = null;
		
		HttpSession session = req.getSession();
		Account acc = (Account) session.getAttribute("currAccount");

		Event model = new Event();
		EventController controller = new EventController(acc.getAccountId());
		
		
				
		// Get session creation time.

		
		controller.setModel(model);
		ArrayList<Event> events = controller.getEvents();
		
		Establishment estaModel = new Establishment();
		EstablishmentRegController estaController = new EstablishmentRegController(acc.getAccountId());
		estaController.setModel(estaModel);
        ArrayList<Establishment> estabs = estaController.getEstablishments(); //get ball ArrayList from session updated model
	    String selectedEvent = req.getParameter("selectedEvent");

		
		if(selectedEvent != null) {
			Integer eventID = getIntegerFromParameter(req.getParameter("selectedEvent"));
			session.setAttribute("eventID", eventID);	
			
			//set Event Seession stuff here
		}else {
			try {
				
				String newEventName = req.getParameter("eventName");
				String newStanding = req.getParameter("standing");
				String EstaName = req.getParameter("establishment");
				String EstaDate = req.getParameter("eventdate");
		        String type = null;
		        int standing = -1;
		        int esstabID = -1;
				
		        if(newEventName == null || newEventName.equals(""))
		        {
		        	errorMessage = "Please enter an Event Name";
		        }
		        if(EstaDate == null || EstaDate.equals(""))
		        {
		        	errorMessage = "Please enter an Event Date";
		        }
		        if(newStanding == null || newStanding.equals(""))
		        {
		        	errorMessage = "Please enter your current position in the event";
		        } else 
		        {
		        	standing = Integer.parseInt(newStanding);
		        }
		        
		        
				if (req.getParameter("newType").equals("Practice")) {
					type = "practice";
				} else if (req.getParameter("newType").equals("Tournament")) {
					type = "tournament";
				} else if (req.getParameter("newType").equals("Leauge")) {
					type = "leauge";
				} else {
					errorMessage = "Please enter the event type";
				}
				
				
				for(Establishment esta : estabs) {
					if(EstaName.equals(esta.getEstablishmentName()))
						esstabID = esta.getEstaId();
				}	
			
			if (errorMessage == null)
			{
				Integer eventID = controller.addEvent(acc.getAccountId(), esstabID , EstaDate, newEventName,type, standing);
				session.setAttribute("eventID", eventID);	
			}
			
			}catch(NullPointerException e) {
				errorMessage = "Invalid Input";
			}
			
			//set Event Seession stuff here

		}
        
		//on button press
		
		estabs = estaController.getEstablishments();
		events = controller.getEvents();
		
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("event", events);
		req.setAttribute("esta", estabs);
		
		if(req.getParameter("Submit") != null || req.getParameter("SubmitCurrentEvent") != null && errorMessage == null) {
    		resp.sendRedirect(req.getContextPath() + "/session");
    		System.out.println("This is my event ID "+session.getAttribute("eventID"));
        }else {
    		req.getRequestDispatcher("/_view/event.jsp").forward(req, resp);
        }
        
		
	}
	
	private Integer getIntegerFromParameter(String s) {
		if (s == null || s.equals("")) {
			return null;
		} else {
			return Integer.parseInt(s);
		}
	}
}