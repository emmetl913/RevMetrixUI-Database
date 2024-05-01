package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.RevMetrix.controller.EventController;
import edu.ycp.cs320.RevMetrix.model.Account;
import edu.ycp.cs320.RevMetrix.model.Event;

public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(!AccountServlet.validLogin()) {
            req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
        }
		
		System.out.println("Index Servlet: doGet");
		
		HttpSession session = req.getSession();		
		Account acc = (Account) session.getAttribute("currAccount");

		Event model = new Event();
		EventController controller = new EventController(acc.getAccountId());
		controller.setModel(model);
		ArrayList<Event> events = controller.getEvents();
		
		req.setAttribute("event", events);
		req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Index Servlet: doPost");
		
		HttpSession session = req.getSession();		
		Account acc = (Account) session.getAttribute("currAccount");

		Event model = new Event();
		EventController controller = new EventController(acc.getAccountId());
		controller.setModel(model);
		ArrayList<Event> events = controller.getEvents();
		
		req.setAttribute("event", events);
		req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
		
	}
	
}
