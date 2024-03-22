package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.lab02.controller.BowlingController;
import edu.ycp.cs320.lab02.model.Ball;
import edu.ycp.cs320.lab02.model.Bowling;

import edu.ycp.cs320.lab02.controller.EventController;
import edu.ycp.cs320.lab02.model.Event;
import edu.ycp.cs320.lab02.model.EventArray;

public class StartBowlingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("StartBowling Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/startBowling.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("StartBowling Servlet: doPost");
		
		EventArray model = new EventArray();
		EventController controller = new EventController();
		controller.setModel(model);
		
		String type = null;
		
		if (req.getParameter("practice") != null) {
			type = "practice";
		} else if (req.getParameter("tournament") != null) {
			type = "tournament";
		} else if (req.getParameter("leauge") != null) {
			type = "leauge";
		} else {
			throw new ServletException("Unknown command");
		}
		
		
		
		ArrayList<Event> events = model.getEvents(); //get ball ArrayList from session updated model
		if(events == null) {
			
		}
		
		
		String errorMessage = null;
		
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("game", model);
		
		req.getRequestDispatcher("/_view/startBowling.jsp").forward(req, resp);
	}
	
	private Integer getIntegerFromParameter(String s) {
		if (s == null || s.equals("")) {
			return null;
		} else {
			return Integer.parseInt(s);
		}
	}
	
}
