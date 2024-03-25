package edu.ycp.cs320.lab02.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.lab02.controller.ShotController;
import edu.ycp.cs320.lab02.model.Shot;
import edu.ycp.cs320.lab02.model.Frame;
import edu.ycp.cs320.lab02.model.Ball;
import edu.ycp.cs320.lab02.model.BallArsenal;

public class ShotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private List<String> ballArsenal = new ArrayList<>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Shot Servlet: doGet");	
		
		HttpSession session = req.getSession();
		long createTime = session.getCreationTime();
		
		//get last access time of this webpage
		long lastAccessTime = session.getLastAccessedTime();
		String userIDKey = new String("userID");
		String userID = new String("ABCD");
		
		int currentGameNumber = 1;
		ArrayList<Frame> currentFrames = new ArrayList<>();
		
		//check is new comer on webpage
		String shotKey = new String("shotKey");
		Frame model = new Frame(currentGameNumber, currentFrames);
		
		if(session.isNew()) {
			session.setAttribute(userIDKey, userID);
			session.setAttribute(shotKey, model);
		}
		
		//get model and userID from jsp
		userID = (String)session.getAttribute(userIDKey);
		
		if(session.getAttribute("gameNumber") == null) {
			session.setAttribute("gameNumber", 1);
			session.setAttribute("frameNumber", 1);
		}
		
		if(session.getAttribute("ballArsenal") == null || ((List<Ball>)session.getAttribute("ballArsenal")).isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/_view/ballArsenal.jsp");
		}
		
		//get ballArsenal from the session
		List<Ball> ballArsenal = (List<Ball>)session.getAttribute("ballArsenal");
		
		req.setAttribute("ballArsenal", ballArsenal);
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    HttpSession session = req.getSession();
	    
	    String ballName = req.getParameter("ball");
	    String shotType = req.getParameter("shotType");
	    String pinsKnockedDown = req.getParameter("pinsKnockedDown");
	    
	    Shot shot = new Shot();
	    shot.setBallName(ballName);
	    shot.setType(shotType);
	    shot.setKnockedOver(Integer.parseInt(pinsKnockedDown));
	    session.setAttribute("shot" + session.getAttribute("frameNumber"), shot);
	    
	    int frameNumber = (int) session.getAttribute("frameNumber");
	    session.setAttribute("frameNumber", frameNumber+1);
	        
		req.getRequestDispatcher("/_view/shot.jsp").forward(req, resp);
	}
}
