package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import edu.ycp.cs320.lab02.controller.ShotController;
import edu.ycp.cs320.lab02.model.Shot;
import edu.ycp.cs320.lab02.model.Ball;
import edu.ycp.cs320.lab02.model.BallArsenal;

public class ShotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Shot Servlet: doGet");	
		
		BallArsenal ballArsenal = new BallArsenal();
		
		req.setAttribute("ballArsenal", ballArsenal);
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Shot Servlet: doPost");
		
		String errorMessage = null;
		
		Shot model = null;
		ShotController controller = new ShotController();
		
		try {
			String ballName = req.getParameter("ballName");
			
			//retrieve the ballArsenal
			BallArsenal ballArsenal = (BallArsenal) req.getAttribute("ballArsenal");
			
			Ball ball = null;
			for(Ball b : ballArsenal.getBalls()) {
				if(b.getName().equals(ballName)) {
					ball = b;
					break;
				}
			}
			
		}catch(NumberFormatException e) {
			errorMessage = "Invaid";
		}
		
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("ballArsenal", model);
		
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
		
	}
}
