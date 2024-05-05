<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import= "edu.ycp.cs320.RevMetrix.model.Game" %>
<%@ page import = "java.io.*,java.util.*"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import = "java.util.List" %>
<%
	List<Game> games = (List<Game>)request.getAttribute("games");
%>
<html>
<head>


    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: darkgray;
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
		.game-section{
			border: 1px solid black;
			margin-bottom: 10px;
			padding: 10px;
			overflow: auto; 
		}
		.game-section:hover{
			background-color: #33B5FF;
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
            box-shadow: 10px 10px 5px black;
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
	background: linear-gradient(to bottom, rgba( 243, 0, 178, 1 ), rgba( 28, 144, 243, 1 ) 95%, rgba( 255, 255, 0, 1 ));
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
	.bottom-link {
  position: absolute;
  bottom: 20px; /* Adjust this value to raise or lower the link */
  left: 0;
  width: 84%; 
  text-align: left;
  }

  .bottom-link2 {
  position: absolute;
  bottom: 60px; /* Adjust this value to raise or lower the link */
  left: 0;
  width: 84%; 
  text-align: left;
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
          <a href="${pageContext.servletContext.contextPath}/ballArsenal">Ball Arsenal</a>
		  <a href="${pageContext.servletContext.contextPath}/stats">Stats</a>
		  <a class="dropbtn" href="#" onclick="toggleDropdown(), nextStep(1)">Start Bowling!</a>
		  <div class="dropdown-content" id="myDropdown">
		        <a href="${pageContext.servletContext.contextPath}/event">&nbsp&nbsp >Event</a>
	        <a href="${pageContext.servletContext.contextPath}/session">&nbsp&nbsp >Session</a>
	        <a href="${pageContext.servletContext.contextPath}/game">&nbsp&nbsp >Game</a>
	        <a href="${pageContext.servletContext.contextPath}/shot">&nbsp&nbsp >Shot</a>
	   	 </div>
			<a href="https://github.com/emmetl913/RevMetrixUI-Database"class="bottom-link2">GitHub</a>
			<a href="${pageContext.servletContext.contextPath}/logIn"class="bottom-link">Sign Out</a>
	</div>
	<form id = "gameForm" action="${pageContext.servletContext.contextPath}/game" method="post">
	<div class="container">
		<h1>Pick an existing game</h1>
		<div id="gamesList"> &nbsp
			<% 
                   
                if(games != null) {
                  for (Game gameItem : games) {
           %>
		           <div class="game-section" onclick= "selectGame ('<%= gameItem.getGameID() %>')">
		                  <p>ID: <%= gameItem.getGameID() %></p>
		                  <p>Lane: <%= gameItem.getLane() %></p>
		                  <p>Game Number: <%= gameItem.getGameNumber()%></p>
		                  <p>Current Score: <%= gameItem.getScore()%></p>
		           </div>
           <% 
                 } } else {	%>
                 <p>No games available.</p>
           <% } %>
           
		</div>
		 <input type="hidden" id="selectedGame" name="selectedGame">
		 <tr>
            <td><a href="${pageContext.servletContext.contextPath}/shot"><input type="Submit" id="SubmitCurrentGame" name="SubmitCurrentGame" value="Submit"></a></td>
         </tr>
	</div>
	</form>
	<form href="${pageContext.servletContext.contextPath}/game" method="post"> 
	<div class="container">
	
		<h2>Start a new game</h2>
		<table>
			<tr>
		        <th>Enter the starting Lane for the new game: </th>
		        <tr id="inputLane">
		            <td colspan="3"><input type="text" id="inputLane" name="inputLane" placeholder="Enter the starting lane!"></td>
		        </tr>
		    </tr>
		</table>
		<input type="hidden" name="startingLane" id="startingLane" value="">
		<tr>
			<td><input type="Submit" name="submit" value="Submit Page"><a href="${pageContext.servletContext.contextPath}/shot"></a></td>
		</tr>
	</div>
</form>
<script>
	function selectGame(gameItem)
	{
		document.getElementById('selectedGame').value = gameItem;
	}
</script>	
</body>
</html>