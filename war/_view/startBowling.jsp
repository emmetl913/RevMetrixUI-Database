<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>

<script 
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"
	type="text/javascript">
</script>

    <style>
    body {
            margin: 0;
            font-family: Arial, sans-serif;
        }

        .sidebar {
            height: 100%;
            width: 250px;
            position: fixed;
            top: 0;
            left: 0;
            background-color: #111;
            padding-top: 20px;
        }

        .sidebar a {
            padding: 10px 15px;
            text-decoration: none;
            font-size: 18px;
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
            background-color: #f1f1f1;
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
        .container{
            text-align: center;
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
    </style>
</head>
<body>

<c:if test="${! empty errorMessage}">
	<div class="error">${errorMessage}</div>
</c:if>
	<input type="hidden" name="step" id="step" value="1">
	<!-- Side bar, duh -->
	<div class="sidebar">
		 <a class="dropbtn" href="#" onclick="toggleDropdown(), nextStep(1)">Start Bowling!</a>
		 <div class="dropdown-content" id="myDropdown">
	        <a href="#" onclick="showContent('event')">Event</a>
	        <a href="#" onclick="showContent('session')">Session</a>
	        <a href="#" onclick="showContent('game')">Game</a>
			<a href="#" onclick="showContent('frame')">Frame</a>
	        <a href="#" onclick="showContent('shot')">Shot</a>
	   	 </div>
		 <a href="${pageContext.servletContext.contextPath}/index">
				<img src="${pageContext.request.contextPath}/_view/BowlingBall.png"width="100" height="100">
			  </a>
			<a href="${pageContext.servletContext.contextPath}/establishmentReg">Establishment Registration</a>
			<a href="${pageContext.servletContext.contextPath}/logIn">Sign Out</a>
		<a href="${pageContext.servletContext.contextPath}/shot">Shot</a>
		<a href="${pageContext.servletContext.contextPath}/ballArsenal">Ball Arsenal</a>
		<a href="https://github.com/emmetl913/RevMetrixUI-Database">GitHub</a>
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
		<div id="step3">
			<h1>Nothing to session here yet!</h1>
			<p>Nothing to session here yet!</p>
			<p class="label">What kind of session?</p>
			<tr>
				<td><input type="Submit" id="sessionType" name="practice" value="Practice" onclick="nextStep(3)"></td>
				<td><input type="Submit" id="sessionType" name="tournament" value="Tournament" onclick="nextStep(3)"></td>
				<td><input type="Submit" id="sessionType" name="leauge" value="Leauge" onclick="nextStep(3)"></td>
			</tr>
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
			<h1>Nothing to Frame here yet!</h1>
			<p>Nothing to Frame here yet!</p>
			<input type="Submit" name="nextFrame" id="frameStatus" value="Continue" onclick="nextStep(5)">
		</div>
		<!-- Shot Input Level -->
		<div id="step6">
			<a href="${pageContext.servletContext.contextPath}/shot">Shot</a>
		</div>
	</div>

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