<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import= "edu.ycp.cs320.lab02.model.Game" %>
<%@ page import = "java.io.*,java.util.*"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
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
	<form id = "gameForm" action="${pageContext.servletContext.contextPath}/game" method="post">
	<div class="container">
	
		<!-- Game Page -->
			<h1>Game Page!</h1>
			<p> Select game by index: </p>

			<select name="establishment" id="establishment">
			<%
				if (games != null) {
				  for (int i =0; i < games.size(); i++) {
					  Game game = games.get(i);
					  
			%>
			<option><%= game.getGameNumber() %> &nbsp&nbsp&nbsp|&nbsp&nbsp&nbsp Lane: <%=game.getLane()%> &nbsp&nbsp&nbsp|&nbsp&nbsp&nbsp # of Frames: <%=game.getFrameCount()%>
			&nbsp&nbsp&nbsp|&nbsp&nbsp&nbsp Score: <%=game.getScore()%></option>
		   <% 
				 } } else {	%>
					 <option value="${game.establishment}">No Establishments</option>
		   <% } %>
			</select>
			<p></p>
			<tr>
				Enter Lane: <br> 
				<input type="text" id="laneInput" name="laneInput" class="number-input">
			</tr>
				<br> 
			<tr>
				<button id="submitButton" type="submit" id="gameStatus" name="select" value="selectCurrentGame"> Select Current Game</button>
				&nbsp
			  	<button id="submitButton1"type="submit" id="gameStatus" name="new" value="startNewGame"> Start New Game</button>
			</tr> 
		</div>
		</form>
		<script>
			document.getElementById("submitButton1").addEventListener("click", function(){
			document.getElementById("laneInput").value = ""
			}
		
		</script>
</body>
</html>