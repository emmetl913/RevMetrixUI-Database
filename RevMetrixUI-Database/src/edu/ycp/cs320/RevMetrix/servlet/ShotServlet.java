package edu.ycp.cs320.RevMetrix.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.RevMetrix.controller.FrameController;
import edu.ycp.cs320.RevMetrix.controller.ShotController;
import edu.ycp.cs320.RevMetrix.model.Shot;
import edu.ycp.cs320.RevMetrix.model.Frame;
import edu.ycp.cs320.RevMetrix.model.Ball;
import edu.ycp.cs320.RevMetrix.model.BallArsenal;

import edu.ycp.cs320.RevMetrix.controller.ShotController;


public class ShotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private List<String> ballArsenal = new ArrayList<>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
//		if(!AccountServlet.validLogin()) {
//            req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
//        }

		System.out.println("Shot Servlet: doGet");	
		
		//getSession = creates information based on the user
		
		HttpSession session = req.getSession();
		long createTime = session.getCreationTime();
		
		//get last access time of this webpage
		long lastAccessTime = session.getLastAccessedTime();
		String userIDKey = new String("userID");
		String userID = (String) session.getAttribute("userID");
		
		//int gameNumber = (int) session.getAttribute("gameNumber");
		Integer frameNumber = (Integer) session.getAttribute("frameNumber");
		
		//check is new comer on webpage
		String shotKey = new String("shotKey");
//		ArrayList<Frame> frames = (ArrayList<Frame>) session.getAttribute(shotKey);
//		
		if(session.isNew()) {
			session.setAttribute(userIDKey, userID);
			//session.setAttribute(shotKey, model);
//			if(frames == null) {
//				frames = new ArrayList<Frame>();
//				
//				frames.add(new Frame(1,1));
//				session.setAttribute(shotKey, frames);
//			}
		}
		
		if(session.getAttribute("gameNumber") == null) {
			//session.setAttribute("gameNumber", gameNumber);
			session.setAttribute("frameNumber", frameNumber);
		}
		
//		if(session.getAttribute("ballArsenal") == null || ((List<Ball>)session.getAttribute("ballArsenal")).isEmpty()) {
//			resp.sendRedirect(req.getContextPath() + "/_view/ballArsenal.jsp");
//		}else {
//			//get ballArsenal from the session
//			List<Ball> ballArsenal = (List<Ball>)session.getAttribute("ballArsenal");
//			
//			req.setAttribute("ballArsenal", ballArsenal);
//			
//			// call JSP to generate empty form
//			req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
//		}
		
		if(frameNumber == null) {
			frameNumber = 1;
			session.setAttribute("frameNumber", frameNumber);
		}
		
		//initialize the frames ArrayList
		ArrayList<Frame> frames = (ArrayList<Frame>) session.getAttribute("frame");
		if(frames == null) {
			frames = new ArrayList<Frame>();
			session.setAttribute("frames", frames);
		}
		
		String action = req.getParameter("action");
		if("nextFameBtn".equals(action)) {
			if(frameNumber < 10) {
				frameNumber++;
			}
		}else if("previousFrameBtn".equals(action)) {
			if(frameNumber > 1) {
				frameNumber--;
			}
		}
		
		//add new frame object to the arraylist
		frames.add(new Frame(1, 1));
		
		//update frame number in session
		session.setAttribute("frameNumber", frameNumber);
		
		//get ballArsenal from the session
		List<Ball> ballArsenal = (List<Ball>)session.getAttribute("ballArsenal");
		
		req.setAttribute("userID", userID);
		req.setAttribute("ballArsenal", ballArsenal);
		req.setAttribute("frameNumber", frameNumber);
//		
		
		//if the frame is out of range, it sends an error message to the user
		boolean outOfRange = (frameNumber < 1 || frameNumber > 10);
		req.setAttribute("outOfRange", outOfRange);
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    
		System.out.println("Game Servlet: doPost");
		
		HttpSession session = req.getSession();
		
		//handle form submission for next frame action
		String action = req.getParameter("action");
		if("nextFrameBtn".equals(action)) {
			//increment frame number
			Integer frameNumber = (Integer) session.getAttribute("frameNumber");
			if(frameNumber == null) {
				frameNumber = 1; //initialize frame number
			}else if(frameNumber < 10) {
				frameNumber++; //increment frame number is not exceeding 10
			}
			session.setAttribute("frameNumber", frameNumber);
		}
		
		//get ball name, shot type, and pins from form submission
		//form submission = next Frame button
		String ballName = req.getParameter("ball");
		String shotType = req.getParameter("shotType");
		int pins = Integer.parseInt(req.getParameter("pins"));
		
		//get first and second shot from the user
		String firstShotScore = req.getParameter("firstShotScore");
		String secondShotScore = req.getParameter("secondShotScore");
		
//		String pinsParam = req.getParameter("pins");
//		int pins = 0;
//		if(pinsParam != null && !pinsParam.isEmpty()) {
//			try {
//				pins = Integer.parseInt(pinsParam);
//			}catch(NumberFormatException e) {
//				e.printStackTrace();
//			}
//		}
//		
		//create a new Shot object with submitted data
		Shot shot = new Shot(ballName, shotType, pins);
		
		//add shot object to session
		session.setAttribute("shot", shot);
		session.setAttribute("firstShotScore", firstShotScore);
		session.setAttribute("secondShotScore", secondShotScore);
		
		//calculate the total score using the ShotController
		ShotController controller = new ShotController();
		int totalScore = controller.calculateScore(session);
		session.setAttribute("totalScore", totalScore);
		
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
	}
}
