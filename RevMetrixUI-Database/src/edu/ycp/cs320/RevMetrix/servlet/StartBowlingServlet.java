package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.controller.BowlingController;
import edu.ycp.cs320.RevMetrix.model.Bowling;
import edu.ycp.cs320.RevMetrix.model.startBowling.Session;

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

		Session session = new Session();
		BowlingController controller = new BowlingController();
	


		resp.setContentType("text/html");

		String errorMessage = null;
		req.setAttribute("errorMessage", errorMessage);
		
	}
	
	
}
