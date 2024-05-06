package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Event;
import edu.ycp.cs320.RevMetrix.model.Game;
import edu.ycp.cs320.RevMetrix.model.Session;
import edu.ycp.cs320.RevMetrix.model.stat;
import edu.ycp.cs320.RevMetrix.controller.EventController;
import edu.ycp.cs320.RevMetrix.controller.GameController;
import edu.ycp.cs320.RevMetrix.controller.SessionController;
import edu.ycp.cs320.RevMetrix.controller.StatsController;
import edu.ycp.cs320.RevMetrix.controller.statController;

public class StatsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean hasEvent = false;
	private boolean hasSession = false;
	private boolean hasGame = false;
	
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
			
			ArrayList<Event> events = (ArrayList<Event>) eventController.getEvents();
			ArrayList<stat> sessionTable = new ArrayList<stat>();
		
		req.setAttribute("formSubmitted", false);
		req.setAttribute("shotFormSubmitted", false);
		req.setAttribute("events", events);
		req.setAttribute("sessionTable", sessionTable);
//		req.setAttribute("eventSessions", eventSessions);
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
			
		    //gets the league container
			ArrayList<stat> sessionTable = new ArrayList<stat>();
			ArrayList<Integer> sessions = controller.getSessionsByEvent(eventID);
			
			for(int i = 1; i <= sessions.size(); i++) {
				sessionTable.add(new stat(controller.getSessionDate(i), controller.getEventName(eventID), controller.getCurrentGameLane(i*3), controller.getGameStatsBySession(i),controller.getSessionScore(sessions.get(i-1)),i*3,0.0));
			}
			req.setAttribute("events", events);
			req.setAttribute("sessionTable", sessionTable);
			
		}catch(NullPointerException e) {
//			resp.sendRedirect(req.getContextPath()+ "/logIn");
		}	
		
//		try {
//			HttpSession session = req.getSession();
//			Account acc = (Account) session.getAttribute("currAccount");
//			
//			Event eventModel = new Event();
//			EventController eventController = new EventController(acc.getAccountId());
//			eventController.setModel(eventModel);
//			
//			ArrayList<Event> events = eventController.getEvents();
//		    String selectedEvent = req.getParameter("eventSelected");
//		    int eventID = -1;
//		    
//		    for(Event event:events){
//		    	if (selectedEvent.equals(event.getEventName())) {
//		    	    eventID = event.getEventID();
//		    	}
//		    }
//		    
//		    String selectedEventID = req.getParameter("eventSelected");
//		    String selectedSessionID = req.getParameter("sessionID");
//		    String selectedGameID = req.getParameter("gameID");
//		    
//		    if(selectedEventID != null && !selectedEventID.isEmpty()) {
//		    	eventID = Integer.parseInt(selectedEventID);
//		    	
//		    	SessionController sessionController = new SessionController();
//		    	List<Session> eventSessions = sessionController.getSessionByEventID(eventID);
//		    	
//		    	req.setAttribute("eventSessions", eventSessions);
//		    }
//		    
//		    if(selectedSessionID != null && !selectedSessionID.isEmpty()) {
//		    	int sessionID = Integer.parseInt(selectedSessionID);
//		    	
//		    	SessionController sessionController = new SessionController();
//		    	List<Session> sessionGames = sessionController.getSessionByEventID(sessionID);
//		    	
//		    	req.setAttribute("sessionGames", sessionGames);
//		    }
//			
//		    if(selectedSessionID != null && !selectedSessionID.isEmpty() && selectedGameID != null && !selectedGameID.isEmpty()) {
//		    	int sessionID = Integer.parseInt(selectedSessionID);
//		    	int gameID = Integer.parseInt(selectedGameID);
//		    	
//		    	StatsController statsController = new StatsController();
//		    	
//		    	req.setAttribute("strike", statsController.strikesPercentage(gameID, sessionID));
//		    	req.setAttribute("spare", statsController.getSparesFromAccount(gameID, sessionID));
//		    	req.setAttribute("leave", statsController.getTotalLeavesFromGameAndSession(gameID, sessionID));
//		    }
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
		
		HttpSession session = req.getSession();
		Account acc = (Account) session.getAttribute("currAccount");
		
		Event eventModel = new Event();
		EventController eventController = new EventController(acc.getAccountId());
		eventController.setModel(eventModel);
		
		ArrayList<Event> events = eventController.getEvents();
	    String selectedEvent = req.getParameter("eventSelected");
	    int eventID = -1;
	    
	    for(Event event:events){
	    	if (selectedEvent.equals(event.getEventName())) {
	    	    eventID = event.getEventID();
	    	}
	    }
	    
	    String selectedEventID = req.getParameter("eventSelected");
	    String selectedSessionID = req.getParameter("sessionID");
	    String selectedGameID = req.getParameter("gameID");
	    
	    if(selectedEventID != null && !selectedEventID.isEmpty()) {
	    	eventID = Integer.parseInt(selectedEventID);
	    	
	    	SessionController sessionController = new SessionController();
	    	List<Session> eventSessions = sessionController.getSessionByEventID(eventID);
	    	
	    	for(Session s : eventSessions) {
	    		System.out.println("Session ID: " +s.getSessionID());
	    	}
	    	
	    	req.setAttribute("eventSessions", eventSessions);
	    }
	    
	    if(selectedSessionID != null && !selectedSessionID.isEmpty()) {
	    	int sessionID = Integer.parseInt(selectedSessionID);
	    	
	    	GameController gameController = new GameController();
	    	List<Game> sessionGames = gameController.getGameBySessionID(sessionID);
	    	
	    	for(Game s : sessionGames) {
	    		System.out.println("Game ID: " +s.getGameID());
	    	}
	    	
	    	req.setAttribute("sessionGames", sessionGames);
	    }
		
	    if(selectedSessionID != null && !selectedSessionID.isEmpty() && selectedGameID != null && !selectedGameID.isEmpty()) {
	    	int sessionID = Integer.parseInt(selectedSessionID);
	    	int gameID = Integer.parseInt(selectedGameID);
	    	
	    	StatsController statsController = new StatsController();
	    	
	    	req.setAttribute("strike", statsController.strikesPercentage(gameID, sessionID));
	    	req.setAttribute("spare", statsController.getSparesFromAccount(gameID, sessionID));
	    	req.setAttribute("leave", statsController.getTotalLeavesFromGameAndSession(gameID, sessionID));
	    }
		
		// call JSP to generate empty form
		//if-else statement for when the user submits a form
        
		String buttonClicked = req.getParameter("submitButtonLeagueChart");
		
		if(buttonClicked != null) {
			req.setAttribute("formSubmitted", true);
			req.setAttribute("shotFormSubmitted", false);
		}else {
				req.setAttribute("shotFormSubmitted", true);
				req.setAttribute("formSubmitted", false);

		}
		
		req.getRequestDispatcher("/_view/stats.jsp").forward(req, resp);
	}
	
}
