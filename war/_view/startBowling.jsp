<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@500&display=swap" rel="stylesheet">

<head>
    <style>
    #datetime {
        display: none;
    }
    body {
            margin: 0;
            font-family: Arial, sans-serif;
    }
	.datetime {
	  margin-left: 250px;
	  font-size: 16px;
	  padding: 24px;
	  color: #ffffff;
	  background: #444444;
	  box-shadow: 0 0 10px rgba(0, 0, 0, 0.25);
	  border-radius: 4px;
	  border-right: 10px #009578 solid;
	  width: 400px;
	  font-weight: 500;
	  font-family: "Inter", sans-serif;
	}
	
	.time {
	  margin-left: 250px;
	  font-size: 3em;
	  color: #00ffcc;
	}
	
	.date { 
	  margin-left: 250px;
	  margin-top: 12px;
	  font-size: 1.75em;
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
		<div id="step2">
			<h1>Nothing to event here yet!</h1>
			<p>Nothing to event here yet!</p>
			<tr>
				<td class="label">Enter Event Name:</td>
				<td><input id="eventName" type="text" name="eventName" value=""/><input type="Submit" name="eventName" value="Submit" onclick="nextStep(2)"> </td>
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
		<div id="step4">
			<h1>Nothing to game here yet!</h1>
			<p>Nothing to game here yet!</p>
			<tr>
				<td><input type="Submit" id="gameStatus" name="continue" value="Continue from last game" onclick="nextStep(4)"></td>
				<td><input type="Submit" id="gameStatus" name="select" value="Select a Game" onclick="nextStep(4)"></td>
				<td><input type="Submit" id="gameStatus" name="new" value="Start a New Game" onclick="nextStep(4)"></td>
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