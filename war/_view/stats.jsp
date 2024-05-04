<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import= "edu.ycp.cs320.RevMetrix.model.Game" %>
<%@ page import= "edu.ycp.cs320.RevMetrix.model.Event" %>

<%@ page import = "java.io.*,java.util.*"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
	ArrayList<Event> events = (ArrayList<Event>)session.getAttribute("eventsListKey");
	ArrayList<Game> games = (ArrayList<Game>)session.getAttribute("gamesListKey");
	//ArrayList<Ball> games = (model != null) ? model.getBalls() : null;

%>
<html>
<head>


    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
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

        .container {
            max-width: 600px;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 8px;
        }

        input {
            width: 100%;
            padding: 8px;
            margin-bottom: 16px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type=submit] {
            background-color: #4caf50;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type=submit]:hover {
            background-color: #45a049;
        }
        .error {
			color: red;
		}
		.sidebar {
			height: 100%;
			width: 250px;
			position: fixed;
			top: 0;
			left: 0;
			background-color: black;
			padding-top: 20px;
		}

		tr{
			padding: 5 px;
		}

		.button{
			margin-right: 15px;
			margin-left: 15px;
		}

		.number-input{
			width: 100px;
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
    </style>
</head>
<body>

<c:if test="${! empty errorMessage}">
	<div class="error">${errorMessage}</div>
</c:if>
	<input type="hidden" name="step" id="step" value="1">
	<!-- Side bar, duh -->
	<div class="sidebar">
		 <a href="${pageContext.servletContext.contextPath}/index">
			<img src="${pageContext.request.contextPath}/_view/BowlingBall.png"width="100" height="100">
		  </a>
	      <a href="${pageContext.servletContext.contextPath}/establishmentReg">Establishment Registration</a>
		  <a href="${pageContext.servletContext.contextPath}/logIn">Sign Out</a>
          <a href="${pageContext.servletContext.contextPath}/shot">Shot</a>
          <a href="${pageContext.servletContext.contextPath}/ballArsenal">Ball Arsenal</a>
          <a href="https://github.com/emmetl913/RevMetrixUI-Database">GitHub</a>
		 <a class="dropbtn" href="#" onclick="toggleDropdown(), nextStep(1)">Start Bowling!</a>
		 <div class="dropdown-content" id="myDropdown">
	        <a href="#" onclick="showContent('event')">Event</a>
	        <a href="#" onclick="showContent('session')">Session</a>
	        <a href="#" onclick="showContent('game')">Game</a>
			<a href="#" onclick="showContent('frame')">Frame</a>
	        <a href="#" onclick="showContent('shot')">Shot</a>
	   	 </div>
	</div>
	<form id = "gameForm" action="${pageContext.servletContext.contextPath}/stats" method="post">
	<div class="container">
	
		<!-- Game Page -->
			<h1>Stats Page!</h1> 
			<tr>
				
				<!-- All stats that need an event will be displayed below>
					First They must choose an event-->
				
				<select>
					<!-- User selects which event they want to see the stats from -->
				<%
					if(events!=null) {
						for(int i = 0; i < events.size();i++) {
							events.get(i);
							String is = ""+i; //for getting index of choice
						
							
						}
					}
				%>
				</select>
				<!-- Possibly the type is a button... -->
				<button class="button" id="strikeButton" type="submit" id="gameStatus" name="be" value="strikePercentage">Strike Percentage</button>
				<button class="button" id="spareButton" type="submit" id="gameStatus" name="happy" value="sparePercentage">Spare Percentage</button>
				<button class="button" id="leavePercentage" type="submit" id="gameStatus" name="leave" value="leavePercentage">Leave Percentage</button>
			</tr> 
		</div>
		</form>
	
		<!-- java script to make the information show -->
</body>
</html>