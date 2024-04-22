package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Establishment;
import edu.ycp.cs320.RevMetrix.model.EstablishmentArray;
import edu.ycp.cs320.RevMetrix.controller.BallArsenalController;
import edu.ycp.cs320.RevMetrix.controller.EstablishmentRegController;
import edu.ycp.cs320.RevMetrix.controller.EventController;
import edu.ycp.cs320.RevMetrix.model.Event;
import edu.ycp.cs320.RevMetrix.model.EventArray;
import edu.ycp.cs320.RevMetrix.model.Game;

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

		//controller.setModel(model);
		Establishment e1 = new Establishment(0, 0, "Colony Park Lanes & Games", "1900 Pennsylvania Ave, York, PA 17404");

		
		ArrayList<Event> events = model.getEvents();
        if(model.getEvents() == null) {
        	events = new ArrayList<Event>();
        	events.add(new Event(1, 1, "", 1, "", 1));
		}
        else {
        	events = model.getEvents();
        }
        EstablishmentArray estaModel = new EstablishmentArray(acc.getAccountId());
		EstablishmentRegController estaController = new EstablishmentRegController();
		estaController.setModel(estaModel);
		
		EventArray model = new EventArray();
		EventController controller = new EventController();
		controller.setModel(model);
		
        ArrayList<Establishment> estabs = estaModel.getEstablishments();
        
		ArrayList<Event> events = model.getEvents();
		// Set the ArrayList as a request attribute
		req.setAttribute("event", events);
		req.setAttribute("esta", estabs);
		
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
		Account acc = (Account) session.getAttribute("currAccount");

		
		controller.setModel(model);
		ArrayList<Event> events = model.getEvents();
        
		//on button press
		String newEventName = req.getParameter("eventName");
		int newStanding = Integer.valueOf(req.getParameter("standing"));
		String EstaName = req.getParameter("establishment");
        String type = null;
        int esstabID = -1;
        
        EstablishmentArray estaModel = new EstablishmentArray(acc.getAccountId());
		EstablishmentRegController estaController = new EstablishmentRegController();
		estaController.setModel(estaModel);
        ArrayList<Establishment> estabs = estaModel.getEstablishments(); //get ball ArrayList from session updated model
		
        
		
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
			
			for(Establishment esta : estabs) {
				if(EstaName.equals(esta.getEstablishmentName()))
					esstabID = esta.getEstaId();
			}
						
		controller.addEvent(acc.getAccountId(), esstabID , 9, newEventName,type, newStanding);
		System.out.print("Account id:"+acc.getAccountId()+" esstabID:" + esstabID+" time:" + " 9 " +" newEventName:" + newEventName+" type:" + type+" newStanding:" + newStanding);

		}catch(NullPointerException e) {
			errorMessage = "Invalid Input";
		}
		
		estabs = estaModel.getEstablishments();
        
		events = model.getEvents();
			
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("event", events);
		req.setAttribute("esta", estabs);

		if(req.getParameter("Submit") != null) {
    		req.getRequestDispatcher("/_view/session.jsp").forward(req, resp);
        }
        
		
		req.getRequestDispatcher("/_view/event.jsp").forward(req, resp);
	}
}
