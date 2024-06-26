package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.lab02.controller.BallArsenalController;
import edu.ycp.cs320.lab02.model.BallArsenal;

public class BallArsenalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("BallArsenal Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/ballArsenal.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("BallArsenal Servlet: doPost");
		
		req.getRequestDispatcher("/_view/ballArsenal.jsp").forward(req, resp);
		
	}
}
