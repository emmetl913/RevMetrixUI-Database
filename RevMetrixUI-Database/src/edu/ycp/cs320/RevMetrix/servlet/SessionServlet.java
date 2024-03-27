package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		long createTime = session.getCreationTime();
		
		long lastAccessTime = session.getLastAccessedTime();
		String userIDKey = new String("userID");
		String userID = new String("ABCD");
		
		String sessionKey = new String("sessionKey");
		Session model = new Session();
		
		if (session.isNew() ) 
		{
			session.setAttribute(userIDKey, userID);
			session.setAttribute(sessionKey, model);
		}
		
		userID = (String)session.getAttribute(userIDKey);
		
		if(model == null)
		{
			model = new Session();
			session.setAttribute(sessionKey, model);
		}
		
		req.setAttribute("model", model);
		session.setAttribute(sessionKey, model);
		
		req.getRequestDispatcher("/_view/session.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException
	{
		System.out.println("Session Servlet: doGet");
		
		String errorMessage = null;
		
		Session model = new Session();
		
		HttpSession session = req.getSession();
		long createTime = session.getCreationTime();
		
		long lastAccessTime = session.getLastAccessedTime();
		String userIDKey = new String("userID");
		String userID = new String("ABCD");
		
		String sessionKey = new String("sessionKey");
		
		if (session.isNew() ){
		      session.setAttribute(userIDKey, userID);
			  session.setAttribute(sessionKey,  model);
			} 
		model = (Session)session.getAttribute(sessionKey);
		userID = (String)session.getAttribute(userIDKey);
		
		String time = req.getParameter("timeType");
		System.out.println(time);
		if ("Current Time".equals(time))
		{
			LocalTime currentTime = LocalTime.now();

	        // Format the time using a DateTimeFormatter
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");
	        String formattedTime = currentTime.format(formatter);
	        
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
		} else if ("Individual Opponent".equals(bowlType)) {
	        String opponentName = req.getParameter("opponentName");
	        model.setOpp(opponentName);
	    } else if ("Solo Bowl".equals(bowlType)) {
	    	String opponentName = "solo";
	    	model.setOpp(opponentName);
	    }
	    

	    
		
		System.out.println(model.getOpponent());
		System.out.println(model.getTime());
		
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("model", model);
		session.setAttribute(sessionKey, model);
		
		if(req.getParameter("submit") != null) {
    		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
        }

		
		req.getRequestDispatcher("/_view/session.jsp").forward(req, resp);
	}
}
