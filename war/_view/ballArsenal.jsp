<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import= "edu.ycp.cs320.RevMetrix.model.Ball" %>
<%@ page import="edu.ycp.cs320.RevMetrix.model.BallArsenal" %>
<%@ page import = "java.io.*,java.util.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
// Retrieve ArrayList from session attribute
	//HttpSession session = request.getSession();
	BallArsenal model = (BallArsenal) session.getAttribute("ballArsenalKey");
	ArrayList<Ball> balls = (model != null) ? model.getBalls() : null;
%>

<html>
	<head>
		<title>Bowling Ball Arsenal</title>
		<style type="text/css">
			body{
				font-family: Arial, Helvetica, sans-serif;
			}

			header {
    text-align: center;
    position: absolute;
    top: 10px; /* Adjust the top position as needed */
    width: 100%; /* Ensure the header spans the full width */
}
			input {
	            width: 70%;
	            padding: 8px;
	            margin-bottom: 16px;
	            box-sizing: border-box;
	            border: 1px solid #ccc;
	            border-radius: 4px;
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

	.ball-box {
            width: 800px;
            align-items: center;
            text-align: center;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 10px;
            background-color: white;
            box-shadow: 2px;
            /* Set fixed height for the container */
            height: 360px;
            /* Add scrollbar when content overflows */
            overflow: auto;            
        }
        .ball-box2 {
            width: 800px;
            align-items: center;
            text-align: center;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 10px;
            background-color: white;
            box-shadow: 2px;
            /* Set fixed height for the container */
            height: 350px;
            /* Add scrollbar when content overflows */
            overflow: auto;
			background-color: green;
            
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
button {
    background-color: #4caf50;
    color: #fff;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

button:hover {
    background-color: #45a049;
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
	      <a href="${pageContext.servletContext.contextPath}/establishmentReg">Establishment Registration</a>
		  <a href="${pageContext.servletContext.contextPath}/logIn">Sign Out</a>
          <a href="${pageContext.servletContext.contextPath}/shot">Shot</a>
          <a href="${pageContext.servletContext.contextPath}/ballArsenal">Ball Arsenal</a>
          <a href="https://github.com/emmetl913/RevMetrixUI-Database">GitHub</a>
          <a href="${pageContext.servletContext.contextPath}/startBowling">Start Bowling</a>
		  </div>
	
		<form action="${pageContext.servletContext.contextPath}/ballArsenal" method="post">
			<header><h1>Bowling Ball Arsenal</h1></header>	

			<div class="ball-box" id="ballBoxDiv">
				<div id="add-ball-form">
					<input type="text" name="ballName" placeholder="Ball Name">
					<button text="Add Ball" name="addBall" type="submit" value="Register Ball">
					Add Ball</button>
					
							
					
				</div>
				<div id="remove-ball-form">
					<input type="text" name="removeBallName" placeholder="Ball Name to Remove">
					<button name="removeBall" type="submit" value="Remove Ball">
					Remove Ball</button>
				</div>
				<div id="ballsList"> &nbsp		
				
			
						<% 
			            if (balls != null && !balls.isEmpty()) {
			                for (Ball ball : balls) {
			        %>
			        <div class="ball-section">
			            <p>Name: <%= ball.getName() %></p>
			        </div>
			        <% 
			                }
			            } else { 
			        %>
			        <p>No balls available.</p>
			        <% } 		session.setAttribute("ballArsenalKey", model); //update session model
%>
				
				</div>
				
			</div>
		
		</form>
	</body>
</html>