<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import= "edu.ycp.cs320.lab02.model.Ball" %>
<%@ page import="edu.ycp.cs320.lab02.model.BallArsenal" %>
<%@ page import = "java.io.*,java.util.*" %>

<html>
	<head>
		<title>Bowling Ball Arsenal</title>
		<style type="text/css">
			body{
				font-family: Arial, Helvetica, sans-serif;
			}

			
			h1{
				font-size: 50px;
				color: black;
				text-align: center;
			}

			#ball-list{
				margin-top: 20px;
			}

			#add-ball-form, #remove-ball-form{
				margin-bottom: 10px;
			}

			#add-ball-form input, #remove-ball-form input{
		
			}

			header {
    text-align: center;
    position: fixed; /* Change to fixed positioning */
    top: 0; /* Place the header at the top of the viewport */
    width: 100%; /* Ensure the header spans the full width */
    background-color: white; /* Ensure the header remains visible */
    z-index: 1000; /* Ensure the header appears above other elements */
}

.ball-box {
    width: 500px;
    align-items: center;
    text-align: center;
    position: relative; /* Change to relative positioning */
    top: 100px; /* Add some top margin to create space for the header */
    left: 50%;
    transform: translateX(-50%);
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: white;
    box-shadow: 2px;
    z-index: 1; /* Ensure the ball box appears below the header */
}

			.bowling-ball-img{
				width: 150px;	/*Adjust the size of the image*/
				height: auto;
				margin-bottom: 10px;
			}

			/* Style for the black sidebar */
.sidebar {
  height: 100%;
  width: 250px;
  position: fixed;
  top: 0;
  left: 0;
  background-color: black;
  padding-top: 20px;
}

/* Links in the sidebar */
.sidebar a {
  padding: 10px 20px;
  text-decoration: none;
  color: white;
  display: block;
}

/* Change color of links on hover */
.sidebar a:hover {
  background-color: #333;
}

/* Style the main content */
.main-content {
  margin-left: 250px; /* Same width as the sidebar */
  padding: 20px;
}
 
   

/* Responsive layout - when the screen is less than 600px wide, make the sidebar and the main content stack on top of each other */
@media screen and (max-width: 600px) {
  .sidebar {
    width: 100%;
    height: auto;
    position: relative;
  }
  .sidebar a {float: left;}
  div.content {margin-left: 0;}
}
.ball-section {
        border: 1px solid black; /* Add border around each ball section */
        margin-bottom: 10px; /* Add some space between ball sections */
        padding: 10px; /* Add padding inside each ball section */
    }
		</style>
	</head>

	<body>
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>

		<div class="sidebar">
			<a href="${pageContext.servletContext.contextPath}/index">
				<img src="${pageContext.request.contextPath}/_view/BowlingBall.png"width="100" height="100">
			  </a>
			<a href="${pageContext.servletContext.contextPath}/startBowling">Start Bowling</a>
			<a href="${pageContext.servletContext.contextPath}/establishmentReg">Establishment Registration</a>
		    <a href="${pageContext.servletContext.contextPath}/logIn">Sign Out</a>
        <a href="${pageContext.servletContext.contextPath}/shot">Shot</a>
        <a href="${pageContext.servletContext.contextPath}/ballArsenal">Ball Arsenal</a>
        <a href="https://github.com/emmetl913/RevMetrixUI-Database">GitHub</a>
		  </div>
	
		<form action="${pageContext.servletContext.contextPath}/ballArsenal" method="post">
			<header><h1>Bowling Ball Arsenal</h1></header>	

			<div class="ball-box">
				<div id="add-ball-form">
					<input type="text" name="ballName" placeholder="Ball Name">
					<button text="Add Ball" name="addBall" type="submit" value="Register Ball" onclick ="updateBallList(${game.balls})">
					Add Ball</button>
					
							
					
				</div>
				<div id="remove-ball-form">
					<input type="text" name="removeBallName" placeholder="Ball Name to Remove">
					<button name="removeBall" type="submit" value="Remove Ball" onclick="updateBallList(${game.balls})">
					Remove Ball</button>
				</div>
				<div id="ballsList"> &nbsp				
				 <% 
            		// Retrieve ArrayList from request attribute
            		ArrayList<Ball> balls = (ArrayList<Ball>) request.getAttribute("balls");
   					if (balls != null) {
       				for (Ball ball : balls) {
				%>
        			<div class="ball-section">
            		<p>Name: <%= ball.getName() %></p>
        		</div>
				<% 
       			 } } else {	%>
   					 <p>No balls available.</p>
				<% } %>
				
				</div>
				
			</div>
		
		</form>
	</body>
</html>