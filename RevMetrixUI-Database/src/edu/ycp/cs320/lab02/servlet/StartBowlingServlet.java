package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		System.out.println("StartBowling Servlet: doPost");
		
		Bowling model = new Bowling();
		BowlingController controller = new BowlingController();
		controller.setModel(model);
		
		
		String errorMessage = null;
		
		req.getRequestDispatcher("/_view/startBowling.jsp").forward(req, resp);
	}
	
}
