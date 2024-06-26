package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.controller.SessionController;
import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Session;

public class SessionServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(!AccountServlet.validLogin()) {
            req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
        }
		
		System.out.println("Session Servlet: doGet");
		HttpSession session = req.getSession();
		
		Account acc = (Account) session.getAttribute("currAccount");
		Integer eventID = (Integer) session.getAttribute("eventID"); //(int) session.getAttribute("currEventID");
		
		Session model = null;
		SessionController controller = new SessionController();
		controller.setModel(model);
		System.out.println("Event ID for this sessions page is: "+eventID);
		List<Session> sessions = controller.getSessionByEventID(eventID);
		
		req.setAttribute("sessions", sessions);
		
		req.setAttribute("model", model);
		
		req.getRequestDispatcher("/_view/session.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException
	{
		System.out.println("Session Servlet: doPost");
		
		String errorMessage = null;
		
		Session model = new Session(0, 0, "", "", "", 0);
		SessionController controller = new SessionController();
		
		HttpSession session = req.getSession();
		Account acc = (Account) session.getAttribute("currAccount");
		Integer eventID = (Integer) session.getAttribute("eventID"); //(int) session.getAttribute("currEventID");
		String selectedSession = req.getParameter("selectedSession");
		System.out.println(selectedSession + " testing this value");
		model.setEventID(eventID);
		controller.setModel(model);
		
		if(selectedSession != null)
		{
			Integer sessionID = getIntegerFromParameter(req.getParameter("selectedSession"));
			System.out.println("Session id for selected session: "+sessionID);
			session.setAttribute("sessionID", sessionID);
		} else
		{
			String inputDate = (String) req.getParameter("sessionDate");
			model.setDate(inputDate);
			System.out.println("Date entered: "+inputDate);
			
			
			
			String bowlType = req.getParameter("bowlType");
			System.out.println(bowlType);
			if ("Team Opponent".equals(bowlType)) {
		        String opponentName = req.getParameter("opponentName");
		        model.setName(opponentName);
		        model.setOppType("team");
			} else if ("Individual Opponent".equals(bowlType)) {
		        String opponentName = req.getParameter("opponentName");
		        model.setName(opponentName);
		        model.setOppType("individual");
		    } else if ("Solo Bowl".equals(bowlType)) {
		    	String opponentName = "solo";
		    	model.setName(opponentName);
		    	model.setOppType("self");
		    }
			
			if(errorMessage != "")
			{
				System.out.println("Inserting owo");
				Integer sessionID = controller.insertNewSession(model.getEventID(), model.getDate(),  model.getOppType(), model.getName(), 0);
	    		session.setAttribute("sessionID", sessionID);
			}
		}

	    
		
		System.out.println(model.getOpponent());
		System.out.println(model.getTime());
		
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("model", model);
		
		if(req.getParameter("submit") != null || req.getParameter("SubmitCurrentSession") != null && errorMessage == null) {
			System.out.println("Session submit button is pressed");
    		resp.sendRedirect(req.getContextPath() + "/game");
        }
		else {		
			req.getRequestDispatcher("/_view/session.jsp").forward(req, resp);}
		}
	
	private Integer getIntegerFromParameter(String s) {
		if (s == null || s.equals("")) {
			return null;
		} else {
			return Integer.parseInt(s);
		}
	}
}