<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import= "edu.ycp.cs320.RevMetrix.model.Event" %>
<%@ page import= "edu.ycp.cs320.RevMetrix.model.Game" %>
<%@ page import= "edu.ycp.cs320.RevMetrix.model.Establishment" %>
<%@ page import = "java.io.*,java.util.*"%>
<%@ page import="javax.servlet.http.HttpSession" %>


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
            background: linear-gradient(to bottom, rgba( 243, 0, 178, 1 ), rgba( 28, 144, 243, 1 ) 95%, rgba( 255, 255, 0, 1 ));
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
		  <a class="dropbtn" href="#" onclick="toggleDropdown(), nextStep(1)">Start Bowling!</a>
		  <div class="dropdown-content" id="myDropdown">
		<a href="${pageContext.servletContext.contextPath}/event">>Event</a>
		<a href="${pageContext.servletContext.contextPath}/session">>Session</a>
		<a href="${pageContext.servletContext.contextPath}/game">>Game</a>
		<a href="${pageContext.servletContext.contextPath}/shot">>Shot</a>
	   	 </div>
            <a href="https://github.com/emmetl913/RevMetrixUI-Database"class="bottom-link2">GitHub</a>
            <a href="${pageContext.servletContext.contextPath}/logIn"class="bottom-link">Sign Out</a>
	</div>
<form id="session" action="${pageContext.servletContext.contextPath}/session" method="post">
	<div class="container">
		<div class="active">
			<th>Location: </th>
			<th>Total Score: </th>
			<table>
		        <tr>
		        <th>Time: </th>
		        </tr>
		        <tr>
		            <td><button type="button" id="otherTime">Enter a Session Time</button></td>
		            <td>
		            	<button type="button" id="currentTime" value="Current Time">Use Current Time</button>
		            	
		            </td>
		        </tr>
		        <tr id="inputTime">
		            <td colspan="3"><input type="text" id="inputTime" name="inputTime" placeholder="Enter time of Session"></td>
		        </tr>
    		</table>
    		<input type="hidden" name="timeType" id="timeType" value="">
			<table>
		        <tr>
		        <th>Opponent</th>
		        </tr>
		        <tr>
		            <td><button type="button" id="teamBtn" value="Team Opponent">Team Opponent</button></td>
		            <td><button type="button" id="indvBtn" value="Individual Opponent">Individual Opponent</button></td>
		            <td>
		            	<button type="button" id="soloBtn" value="solo">Solo Bowl</button>
		            	
		            </td>
		        </tr>
		        <tr id="opponentInputRow">
		            <td colspan="3"><input type="text" name="opponentName" id="opponentName" placeholder="Enter opponent name"></td>
		        </tr>
    		</table>
    		<input type="hidden" name="bowlType" id="bowlType" value="">
    		<tr>
				<td><input type="Submit" name="submit" value="Submit Page"><a href="${pageContext.servletContext.contextPath}/game"></a></td>
			</tr>
		</div>
	</div>
</form>
	<script>
        document.getElementById("teamBtn").addEventListener("click", function() {
        	document.getElementById("bowlType").value = "Team Opponenet";
            document.getElementById("opponentInputRow").style.display = "table-row";
        });
        document.getElementById("indvBtn").addEventListener("click", function() {
            document.getElementById("bowlType").value = "Individual Opponent";
        	document.getElementById("opponentInputRow").style.display = "table-row";
        });
        document.getElementById("soloBtn").addEventListener("click", function() {
        	document.getElementById("bowlType").value = "Solo Bowl";
            document.getElementById("opponentInputRow").style.display = "none";
        });
        
        
        document.getElementById("otherTime").addEventListener("click", function() {
            document.getElementById("timeType").value = "Other Time";
        	document.getElementById("inputTime").style.display = "table-row";
        });
        document.getElementById("currentTime").addEventListener("click", function() {
        	document.getElementById("timeType").value = "Current Time";
        	document.getElementById("inputTime").style.display = "none";
        });
        
        
        document.getElementById("session").addEventListener("submit", function(e)
        		{
        			if (document.getElementById("bowlType").value !== "Solo Bowl")
        				{
        					var opponentName = document.getElementById("opponentName").value;
        					document.getElementById("opponentName").setAttribute("name", "opponentName");
        		            document.getElementById("opponentName").setAttribute("value", opponentName);
        				} 
        			if (document.getElementById("timeType").value !== "Current Time")
        				{
        					var otherTime = document.getElementById("inputTime").value;
        					document.getElementById("inputTime").setAttribute("name", "inputTime");
        					document.getElementById("inputTime").setAttribute("value", inputTime);
        				}
        		})
        
        
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