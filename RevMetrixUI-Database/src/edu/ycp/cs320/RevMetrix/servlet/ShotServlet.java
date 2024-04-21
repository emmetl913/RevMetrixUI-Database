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
		
		if(!AccountServlet.validLogin()) {
            req.getRequestDispatcher("/_view/logIn.jsp").forward(req, resp);
        }

		System.out.println("Shot Servlet: doGet");	
		
		//getSession = creates information based on the user
		
		HttpSession session = req.getSession();
		long createTime = session.getCreationTime();
		
		//get last access time of this webpage
		long lastAccessTime = session.getLastAccessedTime();
		String userIDKey = new String("userID");
		String userID = new String("ABCD");
		
		int gameNumber = 1;
		int frameNumber = 1;
		
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
		
		//get model and userID from jsp
		userID = (String)session.getAttribute(userIDKey);
//		frames = (ArrayList<Frame>) session.getAttribute(shotKey);
//		
		if(session.getAttribute("gameNumber") == null) {
			session.setAttribute("gameNumber", gameNumber);
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
		
		//get ballArsenal from the session
		List<Ball> ballArsenal = (List<Ball>)session.getAttribute("ballArsenal");
		
		req.setAttribute("ballArsenal", ballArsenal);
//		session.setAttribute(shotKey, frames);
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    
		System.out.println("Game Servlet: doPost");
		
		HttpSession session = req.getSession();
	    long createTime = session.getCreationTime();
		
		String errorMessage = null;
		Object sessionShot = session.getAttribute("shotKey");
		
//		if(sessionShot instanceof Shot) {
//			shot = (Shot) sessionShot;
//		}else {
//			errorMessage = "Session does not contain a valid Shot object";
//		}
		
		Frame frame = new Frame(1, 1, 1);
		ShotController controller = new ShotController();
		   
		// Get last access time of this Webpage.
		long lastAccessTime = session.getLastAccessedTime();
		String userIDKey = new String("userID");
		String userID = new String("ABCD");

		String shotKey = new String("shotKey");
		ArrayList<Frame> frames = new ArrayList<Frame>();
		
		   // Check if this is new comer on your Webpage.
		if (session.isNew() ){
	      session.setAttribute(userIDKey, userID);
	      
	      frames.add(new Frame(1,1));
		  session.setAttribute(shotKey,  frames);
		} 
		
		userID = (String)session.getAttribute(userIDKey);
		frames = (ArrayList<Frame>)session.getAttribute(shotKey);
		
		//retreive or create a Frame object in the session
		if(frames == null) {
			frame = new Frame(0, 0, 0);
		}
	    
		//prevents null pointer exceptions
		//retreive shot details
	    String ballName = req.getParameter("ball");
	    String shotType = req.getParameter("shotType");
	    
	    String pinsParam = req.getParameter("pins");
	    int pins = 0;
	    Shot shot = new Shot(0, 0, 0, 0, "", 0, "");
	    
	    if(pinsParam != null) {
	    	pins = Integer.parseInt(pinsParam);
	    }
	    
	    if(ballName != null && shotType != null) {
	    	shot.setBallName(ballName);
	    	shot.setType(shotType);
	    }
	    
	    //creates a new shot object
	    Shot shots = new Shot(ballName, shotType, pins);
	    
	    //add the shot to the frame
//	    if(frames != null) {
//	    	 frames.add(new Frame());
//	    }
	    
//	    int firstShot = Integer.parseInt(req.getParameter("firstShot"));
//	    int secondShot = Integer.parseInt(req.getParameter("secondShot"));
//	   
	    int totalScore = controller.calculateScore(session);
	    session.setAttribute("totalScore", totalScore);
	    
	    if("incrementFrameNumber".equals(req.getParameter("action"))) {
	    	Integer frameNumber = (Integer) session.getAttribute("frameNumber");
	    	if(frameNumber == null) {
		    	frameNumber = 1;	//initialize
		    }else {
		    	frameNumber++;		//increment
		    }
		    session.setAttribute("frameNumber",  frameNumber);
		    resp.getWriter().write(String.valueOf(frameNumber));
	    }
	    
	    req.setAttribute("errorMessage", errorMessage);
	    session.setAttribute(shotKey, shot);
	    
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
	}
}
