package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.lab02.controller.BowlingController;
import edu.ycp.cs320.lab02.model.Bowling;

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
		doRequest(req, resp);
		System.out.println("StartBowling Servlet: doPost");
		
		Bowling model = new Bowling();
		BowlingController controller = new BowlingController();
		controller.setModel(model);
		
	    
		resp.setContentType("text/html");
		
		String errorMessage = null;
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("model", model);	
		
		
	}
	private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		// Get baseline parameters
		String event = getBaseline(req, "name");
		String session = getBaseline(req, "sType");
		String game = getBaseline(req, "gStatus");
		String frame = getBaseline(req, "frame");
		
		// Process information into model & controller
		Bowling model = new Bowling();
		BowlingController controller = new BowlingController();
		controller.setModel(model);
		controller.setInfo(event, session, game, frame);
	}
	
	private String getBaseline(HttpServletRequest req, String name) 
	{
		String val = req.getParameter(name);
		if(val == null)
		{
			return null;
		} else 
		{
			return val;
		}
	}
	
}
