<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import= "edu.ycp.cs320.lab02.model.Event" %>
<%@ page import="edu.ycp.cs320.lab02.model.EventArray" %>

<%@ page import= "edu.ycp.cs320.lab02.model.Game" %>
<%@ page import= "edu.ycp.cs320.lab02.model.Establishment" %>
<%@ page import="edu.ycp.cs320.lab02.model.EstablishmentArray" %>

<%@ page import = "java.io.*,java.util.*"%>


<html>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@500&display=swap" rel="stylesheet">

<head>

<script 
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"
	type="text/javascript">
</script>
    <style>

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
        .error {
			color: red;
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
            border: 0px;
        }

        td, th {
            border: none;
            padding: 8px;
            text-align: center;
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
	        <a href="#" onclick="showContent('event')">>Event</a>
	        <a href="#" onclick="showContent('session')">>Session</a>
	        <a href="#" onclick="showContent('game')">>Game</a>
			<a href="#" onclick="showContent('frame')">>Frame</a>
	        <a href="#" onclick="showContent('shot')">>Shot</a>
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
			
			<p class="label">Establishment Name:</p>
			
			<table>
		        <tr>
		            <th>Time</th>
		        </tr>
		        <tr>
	            <td><button type="Submit" id="currentTime" value="currentTime">Use Current Time</button></td>
	            <td><button type="Submit" id="otherTime" value="anotherTime">Use Another Time</button></td>
	        </tr>
	        <tr id="timeInputRow">
	            <td colspan="3"><input type="text" id="otherTime" placeholder="Enter Time of Session"></td>
	        	<td><button type="Submit" id="submitSession">Submit</button></td>
	        </tr>
    		</table>
			<table>
		        <tr>
		            <th>Opponent</th>
		        </tr>
		        <tr>
	            <td><button type="Submit" id="teamBtn" value="Team Opponent">Team Opponent</button></td>
	            <td><button type="Submit" id="indvBtn" value="Individual Opponent">Individual Opponent</button></td>
	            <td><button type="Submit" id="soloBtn" value="Solo">Solo</button></td>
	        </tr>
	        <tr id="opponentInputRow">
	            <td colspan="3"><input type="text" id="opponentName" placeholder="Enter opponent name"></td>
	            <td><button type="Submit" id="submitSession">Submit</button></td>
	        </tr>
    		</table>
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
			<option><%= game.getGameNumber() %>| Lane: <%=game.getLane()%> | # of Frames: <%=game.getFrameCount()%> | Score: <%=game.getScore()%></option>
		   <% 
				 } } else {	%>
					 <option value="${game.establishment}">No Establishments</option>
		   <% } %>
			</select>
			<p></p>
			<tr>
				<button type="submit" id="gameStatus" name="select"  onclick="nextStep(4)" value="selectCurrentGame"> Select Current Game
			  	<button type="submit" id="gameStatus" name="new" onclick="nextStep(4)" value="startNewGame"> Start New Game
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
			
		</div>
	</div>
	<script>
	const timeElement = document.querySelector(".time");
	const dateElement = document.querySelector(".date");

	/**
	 * @param {Date} date
	 */
	function formatTime(date) {
	  const hours12 = date.getHours() % 12 || 12;
	  const minutes = date.getMinutes();
	  const isAm = date.getHours() < 12;

	  return `${hours12.toString().padStart(2, "0")}:${minutes
	    .toString()
	    .padStart(2, "0")} ${isAm ? "AM" : "PM"}`;
	}

	/**
	 * @param {Date} date
	 */
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
    </script>
    <script>
        document.getElementById("otherTime").addEventListener("click", function() {
            document.getElementById("timeInputRow").style.display = "table-row";
        });
        document.getElementById("currentTime").addEventListener("click", function() {
            document.getElementById("timeInputRow").style.display = "none";
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
    	
		// Clock
    	let time = document.getElementById("datetime");
		setInterval(() => {
			let d = new Date();
			time.innerHTML = d.toLocaleTimeString();
		},1000);
    </script>
    
</body>
</html>