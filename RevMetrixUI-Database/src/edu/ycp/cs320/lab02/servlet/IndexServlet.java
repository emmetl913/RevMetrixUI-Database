package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Index Servlet: doGet");
		
		req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("MultiplyNumbers Servlet: doPost");
		
		String action = req.getParameter("action");
		if("AddNumbers".equals(action))
			req.getRequestDispatcher("/_view/addNumbers.jsp").forward(req, resp);
		else if("MultiplyNumbers".equals(action))
			req.getRequestDispatcher("/_view/MultiplyNumbers.jsp").forward(req, resp);
		else if("GuessingGame".equals(action))
			req.getRequestDispatcher("/_view/guessingGame.jsp").forward(req, resp);
		else if("BallArsenal".equals(action))
			req.getRequestDispatcher("/_view/ballArsenal.jsp").forward(req, resp);
	}
	
}
