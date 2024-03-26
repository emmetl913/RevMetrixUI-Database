<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import= "edu.ycp.cs320.RevMetrix.model.Event" %>
<%@ page import="edu.ycp.cs320.RevMetrix.model.EventArray" %>

<%@ page import= "edu.ycp.cs320.RevMetrix.model.Game" %>
<%@ page import= "edu.ycp.cs320.RevMetrix.model.Establishment" %>
<%@ page import="edu.ycp.cs320.RevMetrix.model.EstablishmentArray" %>

<%@ page import = "java.io.*,java.util.*"%>

<html>

<head>

<script 
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"
	type="text/javascript">
</script>
    <style>
	.datetime {
	  font-size: 16px;
	  padding: 24px;
	  color: #00000;
	  box-shadow: 0 0 10px rgba(0, 0, 0, 0.25);
	  border-radius: 4px;
	  width: 400px;
	  font-weight: 500;
	  font-family: "Inter", sans-serif;
	}
	
	.time {
	  font-size: 3em;
	  color: #00000;
	}
	
	.date {
	  margin-top: 12px;
	  font-size: 1.75em;
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

        .sidebar {
            height: 100%;
            width: 250px;
            position: fixed;
            top: 0;
            left: 0;
            background-color: #000;
            padding-top: 20px;
        }

        .sidebar a {
            padding: 10px 20px;
            text-decoration: none;
            /*font-size: 18px;*/
            color: white;
            display: block;
        }

        .sidebar a:hover {
            background-color: #555;
        }

        .content {
            margin-left: 250px;
            padding: 20px;
        }
        
        .dropdown {
            position: relative;
            display: inline-block;
        }

        
        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #00000;
            min-width: 210px;
            z-index: 1;
        }

        
        .dropdown-content a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
        }
        
        
        .nested-dropdown {
            display: none;
            position: absolute;
            left: 100%;
            top: 0;
            min-width: 160px;
            z-index: 1;
        }

        /* Show the nested dropdown on hover */
        .dropdown-content:hover .nested-dropdown {
            display: block;
        }
        .content{
        	margin-left: 250px;
        	padding: 20px;
        }
        .content div {
        	display: none;
        }
        .content div.active {
        	display: block;
        }
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
		}
        /* Styling for the shot level */
        .shotHeader{
        	margin-right: 20px;
        	display: flex;
        	align-items: center;
        	justify-content: center;
        	position: relative;
        	font-size: 28px;
        	font-weight: bold;
        }
        .shotHeader label{
        	margin-right: 10px;
        	text-align: center;
        }
        .shotHeader input {
        	border: 2px #000000;
        	text-align: center;
        	font-weight: bold;
        }
        .row {
        	display: flex;
        	justify-content: center;
        }
        .pin {
            width: 30px;
            height: 30px;
            border: 2px #000000;
            border-radius: 50%;
            margin: 5px;
            display: flex;
            align-items: center;
            margin-top: 50px;
        }

        .row{
            display: flex;
            justify-content: center;
        }

        .circle{
            width: 45px;
            height: 45px;
            border-radius: 50%;
            border: 1px solid black;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 5px;
            cursor: pointer;
            font-weight: 900;
        }

        .circle.selected{
            background-color: black;
            color: white;
        }

        .shot-buttons{
            margin-top: 20px;
        }

        .shot-button{
            margin: 5px;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            font-weight: 900;
        }

        #game-info{
            display: inline-block;
            justify-content: center;
            align-items: center;
            margin-bottom: 20px;
            margin-top: 10px;
            margin-right: 15px;
            font-size: 24px;
            font-weight: 900;
        }

        .frame-buttons{
            margin-top: 5px;
        }

        .frame-buttons button{
            margin: 25px;
        }

        .game-number{
            margin-right: 30px;
            border: 1px solid black;
            padding: 5px;
            padding-left: 25px;
            padding-right: 25px;
        }

        .frame-number{
            border: 1px solid black;
            padding: 5px;
            padding-left: 25px;
            padding-right: 25px;
        }

        .color-box{
            width: 40px;
            height: 40px;
            background-color: gray;
            border: 2px solid black;
            cursor: pointer;
            margin: 20px;
        }

        .selected{
            background-color: orange;
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
	table {
            border-collapse: collapse;
        }

        td {
            border: none;
            padding: 8px;
            text-align: center;
        }
		th {
			border: none;
			padding: 8px;
			text-align: left;
		}
        #opponentInputRow {
            display: none;
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
	        <a href="#" onclick="showContent('event')"> >Event</a>
	        <a href="${pageContext.servletContext.contextPath}/session"> >Session</a>
	        <a href="${pageContext.servletContext.contextPath}/game">Game</a>
					<a href="#" onclick="showContent('frame')">Frame</a>
	        <a href="#" onclick="showContent('shot')">Shot</a>
	   	 </div>
	</div>
	<div class="content">
	
		<!-- Home Page -->
		<div id="step1" class="active">
			<h1>Nothing to see here yet!</h1>
			<p>Nothing to see here yet!</p>
		</div>
		<!-- Event Page -->
		
		<div class = "container" id="step2">
			<h2>Event Page</h2>

			<label for="eventName">Event Name:</label>
			<input type="text" name="eventName" size="12" value="${game.eventName}">

			<label for="eventType">Event Type:</label>
            <button text="Practice" name="practice" type="submit" value="Practice">Practice</button>
			<button text="Tournament" name="tournament" type="submit" value="Tournament">Tournament</button>
			<button text="Leauge" name="leauge" type="submit" value="Leauge">Leauge</button>

			<label for="establishment">Establishment Name/Location:</label>
			<select name="establishment" id="establishment">
			<%
			ArrayList<Event> events = (ArrayList<Event>) request.getAttribute("event");
				if (events != null) {
				  for (Event event : events) {
		    %>
			<option value="${game.establishment}"><%= event.getEstablishment().getEstablishmentName()%></option>
		    <% 
				 } } else {	%>
					 <option value="${game.establishment}">No Establishments</option>
		    <% } %>
			</select>

            <label for="standing">Standing:</label>
            <input type="text" name="standing" size="12" value="${game.standing}">

            <label for="stats">Stats?:</label>
            <input type="text" name="stats" size="12" value="${game.stats}">

			<tr>
				<td><input type="Submit" id="sessionType" name="Submit" value="Submit" onclick="nextStep(2)"></td>
			</tr>
		   	
		</div>
		<!-- Session Page -->
		<div id="step3" class="container">
			<th>Location: </th>
			<th>Total Score: </th>
			<table>
		        <tr>
		        <th>Time</th>
		        </tr>
		        <tr>
		            <td><button type="button" id="otherTime" value="otherTime">Enter a Session Time</button></td>
		            <td><button type="button" id="currentTime" value="currentTime">Use Current Time</button></td>
		        </tr>
		        <tr id="inputTime">
		            <td colspan="3"><input type="text" id="inputTime" placeholder="Enter time of Session"></td>
		        </tr>
    		</table>
			<table>
		        <tr>
		        <th>Opponent</th>
		        </tr>
		        <tr>
		            <td><button type="button" id="teamBtn" value="Team Opponent">Team Opponent</button></td>
		            <td><button type="button" id="indvBtn" value="Individual Opponent">Individual Opponent</button></td>
		            <td><button type="button" id="soloBtn" value="Solo">Solo Bowl</button></td>
		        </tr>
		        <tr id="opponentInputRow">
		            <td colspan="3"><input type="text" id="opponentName" placeholder="Enter opponent name"></td>
		        </tr>
    		</table>
    		<tr>
				<td><input type="Submit" id="sessionType" name="Submit" value="Submit" onclick="nextStep(3)"></td>
			</tr>
		</div>
		<!-- Game Page -->
		<div class = "container" id="step4">
			<h1>Game Page!</h1>
			<p> Select game by index: </p>

			<select name="establishment" id="establishment">
			<%
			ArrayList<Game> games = (ArrayList<Game>)request.getAttribute("gameObjArr");
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
				<button type="submit" id="gameStatus" name="select"  onclick="nextStep(4)" value="selectCurrentGame"> Select Current Game</button>
				&nbsp
			  	<button type="submit" id="gameStatus" name="new" onclick="nextStep(4)" value="startNewGame"> Start New Game</button>
			</tr> 
		</div>
		<!-- Frame Page -->
		<div id="step5">
			<h1>Nothing to frame here yet!</h1>
			<p>Nothing to frame here yet!</p>
			<p>i forgot how frames work so i didn't do anything yet :3</p>
			<input type="Submit" name="nextFrame" id="frameStatus" value="Continue" onclick="nextStep(5)">
		</div>
		<!-- Shot Input Level -->
		<div id="step6" style=display:flex;flex-direction:column;align-items:center;>
			<div class="row">
				<div class="pin"><span>7</span></div>
				<div class="pin"><span>8</span></div>
				<div class="pin"><span>9</span></div>
				<div class="pin"><span>10</span></div>
			</div>
			<div class="row">
				<div class="pin"><span>4</span></div>
				<div class="pin"><span>5</span></div>
				<div class="pin"><span>6</span></div>
			</div>
			<div class="row">
				<div class="pin"><span>2</span></div>
				<div class="pin"><span>3</span></div>
			</div>
			<div class="row">
				<div class="pin"><span>1</span></div>
			</div>
		</div>
	</div>
	<script>
	const timeElement = document.querySelector(".time");
	const dateElement = document.querySelector(".date");
	
	function formatTime(date) {
	  const hours12 = date.getHours() % 12 || 12;
	  const minutes = date.getMinutes();
	  const isAm = date.getHours() < 12;
	
	  return `${hours12.toString().padStart(2, "0")}:${minutes
	    .toString()
	    .padStart(2, "0")} ${isAm ? "AM" : "PM"}`;
	}
	
	function formatDate(date) {
	  const DAYS = [
	    "Sunday",
	    "Monday",
	    "Tuesday",
	    "Wednesday",
	    "Thursday",
	    "Friday",
	    "Saturday"
	  ];
	  const MONTHS = [
	    "January",
	    "February",
	    "March",
	    "April",
	    "May",
	    "June",
	    "July",
	    "August",
	    "September",
	    "October",
	    "November",
	    "December"
	  ];
	
	  return `${DAYS[date.getDay()]}, ${
	    MONTHS[date.getMonth()]
	  } ${date.getDate()} ${date.getFullYear()}`;
	}
	
	setInterval(() => {
	  const now = new Date();
	
	  timeElement.textContent = formatTime(now);
	  dateElement.textContent = formatDate(now);
	}, 200);

	</script>
	<script>
        document.getElementById("teamBtn").addEventListener("click", function() {
            document.getElementById("opponentInputRow").style.display = "table-row";
        });
        document.getElementById("indvBtn").addEventListener("click", function() {
            document.getElementById("opponentInputRow").style.display = "table-row";
        });
        document.getElementById("soloBtn").addEventListener("click", function() {
            document.getElementById("opponentInputRow").style.display = "none";
        });
        document.getElementById("otherTime").addEventListener("click", function() {
            document.getElementById("inputTime").style.display = "table-row";
        });
        document.getElementById("currentTime").addEventListener("click", function() {
            document.getElementById("inputTime").style.display = "none";
        });
    </script>
	<script>
		var currentStep = 1;
		// Display current step
		function showStep(step)
		{
			var contentDivs = document.querySelectorAll('.content div');
			contentDivs.forEach(function(div)
					{
						div.classList.remove('active')
					});
			var selectedDiv = document.getElementById('step'+ step);
			selectedDiv.classList.add('active');
			
			currentStep = step;
		}
		
		// Step Incrementer 
		function nextStep(step)
		{
			if (step === currentStep)
			{
				showStep(step+1);
			}
		}
		// Toggle Method for Dropdown button
	    function toggleDropdown() 
	    {
	        var dropdownContent = document.getElementById("myDropdown");
	        if (dropdownContent.style.display === "block") {
	            dropdownContent.style.display = "none";
	        } else {
	            dropdownContent.style.display = "block";
	        }
	    }
	    
    </script>
    <script type="text/javascript">
    //Initialize button fields for input 
		$(document).ready(function() {
			//Pull event name from the submit
			$("#eventName").click(function() {
				//call ajax and post information back to the servlet
				$.ajax({
					type: 'POST',
					url: 'lab02/servlet/startBowling',
					data: { name: $("eventName")},
				});
			});
			//Pull session type from the submit
			$("#sessionType").click(function() {
				$.ajax({
					type: 'POST',
					url: 'lab02/servlet/startBowling',
					data: { sType: $("sessionType")},
				});
			});
			//Pull game type from the submit
			$("#gameStatus").click(function(){
				$.ajax({
					type: 'POST',
					url: 'lab02/servlet/startBowling',
					data: { gStatus: $("gameStatus")},
				});
			});
			//Pull frame info from the submit
			$("#nextFrame").click(function(){
				$.ajax({
					type: 'POST',
					url: 'lab02/servlet/startBowling',
					data: { frame: $("nextFrame")},
				});
			});
			
		});
	</script>
</body>
</html>