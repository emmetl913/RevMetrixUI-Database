package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
		
		
		Session model = null;
		SessionController controller = new SessionController();
		controller.setModel(model);
		
		
		
		req.setAttribute("model", model);
		
		req.getRequestDispatcher("/_view/session.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException
	{
		System.out.println("Session Servlet: doGet");
		
		String errorMessage = null;
		
		Session model = new Session(0, 0, "", "", "", 0);
		SessionController controller = new SessionController();
		
		HttpSession session = req.getSession();
		Account acc = (Account) session.getAttribute("currAccount");
		int eventID = 0; //(int) session.getAttribute("currEventID");
		
		controller.setModel(model);
		
		
		String time = req.getParameter("timeType");
		System.out.println(time);
		if ("Current Time".equals(time))
		{
			LocalTime currentTime = LocalTime.now();

	        // Format the time using a DateTimeFormatter
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");
	        String formattedTime = currentTime.format(formatter);
	        System.out.println(formattedTime);
	        
			model.setTime(formattedTime);
		} else if ("Other Time".equals(time))
		{
			model.setTime(req.getParameter("inputTime"));
		}
		
		
		String bowlType = req.getParameter("bowlType");
		System.out.println(bowlType);
		if ("Team Opponent".equals(bowlType)) {
	        String opponentName = req.getParameter("opponentName");
	        model.setOpp(opponentName);
	        model.setOppType("team");
		} else if ("Individual Opponent".equals(bowlType)) {
	        String opponentName = req.getParameter("opponentName");
	        model.setOpp(opponentName);
	        model.setOppType("individual");
	    } else if ("Solo Bowl".equals(bowlType)) {
	    	String opponentName = "solo";
	    	model.setOpp(opponentName);
	    	model.setOppType("self");
	    }
	    

	    
		
		System.out.println(model.getOpponent());
		System.out.println(model.getTime());
		
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("model", model);
		
		if(req.getParameter("submit") != null) {
			controller.insertNewSession(eventID, model.getTime(), model.getOppType(), model.getName(), 0);
    		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
        }

		
		req.getRequestDispatcher("/_view/session.jsp").forward(req, resp);
	}
}
