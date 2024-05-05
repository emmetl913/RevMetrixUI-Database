package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Event;
import edu.ycp.cs320.RevMetrix.model.stat;
import edu.ycp.cs320.RevMetrix.controller.EventController;
import edu.ycp.cs320.RevMetrix.controller.statController;

public class StatsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Stat Servlet: doGet");	

		
		if(!AccountServlet.validLogin()) {
            req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
        }
		
			HttpSession session = req.getSession();
			Account acc = (Account) session.getAttribute("currAccount");
			statController controller = new statController(acc.getAccountId());
			
			Event eventModel = new Event();
			EventController eventController = new EventController(acc.getAccountId());
			eventController.setModel(eventModel);
			
			ArrayList<Event> events = eventController.getEvents();
			ArrayList<stat> sessionTable = new ArrayList<stat>();
			
	    
		req.setAttribute("formSubmitted", false);
		req.setAttribute("events", events);
		req.setAttribute("sessionTable", sessionTable);
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/stats.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Stat Servlet: doPost");
		
		try {
			HttpSession session = req.getSession();
			Account acc = (Account) session.getAttribute("currAccount");
			statController controller = new statController(acc.getAccountId());
			
			Event eventModel = new Event();
			EventController eventController = new EventController(acc.getAccountId());
			eventController.setModel(eventModel);
			
			ArrayList<Event> events = eventController.getEvents();
		    String selectedEvent = req.getParameter("selectedEvent");
		    int eventID = -1;
		    
		    for(Event event:events){
		    	if (selectedEvent.equals(event.getEventName())) {
		    	    eventID = event.getEventID();
		    	}
		    }
			
			ArrayList<stat> sessionTable = new ArrayList<stat>();
			ArrayList<Integer> sessions = controller.getSessionsByEvent(eventID);
			
			for(int i = 1; i <= sessions.size(); i++) {
				sessionTable.add(new stat(controller.getSessionDate(i), controller.getEventName(eventID), controller.getCurrentGameLane(i*3), controller.getGameStatsBySession(i),controller.getSessionScore(sessions.get(i-1)),i*3,0.0));
			}
			req.setAttribute("events", events);
			req.setAttribute("sessionTable", sessionTable);
		}catch(NullPointerException e) {
			resp.sendRedirect(req.getContextPath()+ "/logIn");
		}	
		// call JSP to generate empty form
        req.setAttribute("formSubmitted", true);
		req.getRequestDispatcher("/_view/stats.jsp").forward(req, resp);
	}
	
}
