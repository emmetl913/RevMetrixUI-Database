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
            justify-content: center;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s;
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

				<td><input type="text" name="eventName" value="${model.Estab}"/> <input type="Submit" name="eventName" value="Submit" onclick="nextStep(2)"></td>

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
