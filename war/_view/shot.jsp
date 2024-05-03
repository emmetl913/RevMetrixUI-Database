<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.ycp.cs320.RevMetrix.model.BallArsenal" %>
<%@ page import="edu.ycp.cs320.RevMetrix.controller.ShotController" %>
<%@ page import="edu.ycp.cs320.RevMetrix.model.Ball" %>
<%@ page import="java.util.ArrayList" %>

<%
// Retrieve ArrayList from session attribute
    BallArsenal model = (BallArsenal) session.getAttribute("ballArsenalKey");
    ArrayList<Ball>
    balls = (model != null) ? model.getBalls() : null;
%>
<html lang="en">
	<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Bowling Ball Arsenal</title>
		<style type="text/css">
			.title {
				font-size: 2.5em;
				font-weight: bold;
				background: linear-gradient(-32deg, rgba(243, 0, 178, 1), rgba(28, 144, 243, 1) 50%);
				text-transform: uppercase;
				letter-spacing: 2px;
				text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
			}
	        body{
				font-family: Arial, Helvetica, sans-serif;
	        	display:flex;
				background-color: darkgray;
			}
			#shaderCanvas {
	            border-radius:50%;
	        }
			header {
				text-align: center;
				padding-left: 400px;
				max-width: 600px;
				margin: 50px auto;
				width: 100%;  /* Ensure the header spans the full width */
			}
			input {
	            width: 70%;
	            padding: 8px;
	            margin-bottom: 16px;
	            box-sizing: border-box;
	            border: 1px solid #ccc;
	            border-radius: 4px;
	        }
			.sidebar {
				  height: 100%;
				  width: 250px;
				  position: fixed;
				  top: 0;
				  left: 0;
				  background: linear-gradient(to bottom, rgba( 243, 0, 178, 1 ), rgba( 28, 144, 243, 1 ) 95%, rgba( 255, 255, 0, 1 ));
				  padding-top: 20px;
				  box-shadow: 10px 5px 5px rgba(0, 0, 0, 0.1);
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
			.error{
				color: red; 
	        	font-weight: bold;
			}
			.container {
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
				box-shadow: 10px 10px 5px black;
			}	           
			#bowling-frame-display {
				width: 100%;
				max-width: 800px; /* Adjust as needed */
				margin: 0 auto;
				text-align: center;
			}

			.bowling-game {
				display: flex;
			}

			.frame {
				width: calc(100% / 10);
				height: 70px;
				border: 1px solid black;
				box-sizing: border-box;
				padding: 10px;
				background-color: #e0e0e0;
				position: relative;
				display: flex;
				justify-content: center;
				align-items: center;
			}
			.score-box-right {
				position: relative;
				top: -24px;
			    right: -23px;
				float: right;	
				width: 20px; 
				height: 20px; 
				border: 1px solid black;
				border-left: none;
				background-color: #b9b9b9;
				border-right:none;
				
			}
			.score-box-left {
				position: relative;
				top: -24px;
			    right: -23px;
				float: right;	
				width: 20px; 
				height: 20px; 
				border: 1px solid black;
				background-color: white;
			}
			.score-box-10right {
				position: relative;
				top: -24px;
			    right: -20px;
				float: right;	
				width: 20px; 
				height: 20px; 
				border: 1px solid black;
				border-left: none;
				border-right: none;
				background-color: white;

			}
			.score-box-10middle {
				position: relative;
				top: -24px;
			    right: -20px;
				float: right;	
				width: 20px; 
				height: 20px; 
				border: 1px solid black;
				background-color:#b9b9b9;

			}
			.score-box-10left {
				position: relative;
				top: -24px;
			    right: -20px;
				float: right;	
				width: 20px; 
				height: 20px; 
				border: 1px solid black;
				border-right: none;
				background-color: white;

			}
			.row {
				display: flex;
				justify-content: center;
			}
			

			.pin {
				width: 30px;
				height: 30px;
				border: 2px solid black;
				border-radius: 50%;
				margin: 5px;
				display: flex;
				align-items: center;
				justify-content: center;
				font-weight: bold;
				cursor: pointer;
				background-color: black; 
				color: white; 
				transition: background-color 0.3s;
			}
			.pin.down {
				background-color: white;
				color: black;
			}
			.pin.locked{
				border: none;
				color: white;
				background-color: white;
			}
			.shot {
				width: 30px;
				height: 30px;
				border: 2px solid black;
				margin: 5px;
				display: flex;
				align-items: center;
				justify-content: center;
				position: relative;
				font-size: 14px;
				font-weight: bold;
				background-color: green;
        }
		 .miss {
            width: 30px;
            height: 30px;
            border: 2px solid black;
            margin: 5px;
            display: flex;
            align-items: center;
            justify-content: center;
            position: relative;
            font-size: 28px;
            font-weight: bold;
        }
        .foul {
            width: 30px;
            height: 30px;
            border: 2px solid black;
            margin: 5px;
            display: flex;
            align-items: center;
            justify-content: center;
            position: relative;
            font-size: 28px;
            font-weight: bold;
        }
        .strike {
            width: 30px;
            height: 30px;
            border: 2px solid black;
            margin: 5px;
            display: flex;
            align-items: center;
            justify-content: center;
            position: relative;
            font-size: 28px;
            font-weight: bold;
        }
        .spare {
            width: 30px;
            height: 30px;
            border: 2px solid black;
            margin: 5px;
            display: flex;
            align-items: center;
            justify-content: center;
            position: relative;
            font-size: 28px;
            font-weight: bold;
        }

			
		</style>
	</head>

	<body>
	  <div class="sidebar">
			<a href="${pageContext.servletContext.contextPath}/index">
			<img src="${pageContext.request.contextPath}/_view/BowlingBall.png"width="100" height="100">
		  </a>
	      <a href="${pageContext.servletContext.contextPath}/establishmentReg">Establishment Registration</a>
          <a href="${pageContext.servletContext.contextPath}/ballArsenal">Ball Arsenal</a>
          <a href="${pageContext.servletContext.contextPath}/event">Start Bowling</a>

          <a href="https://github.com/emmetl913/RevMetrixUI-Database"class="bottom-link2">GitHub</a>
          <a href="${pageContext.servletContext.contextPath}/logIn"class="bottom-link">Sign Out</a>
	  </div>



		  <form id="shotForm" action="${pageContext.servletContext.contextPath}/shot" method="post">
			
			<div class="container">
			<div class="title">BOWLING!</div>
				<c:if test="${! empty errorMessage}">
					<div class="error">${errorMessage}</div>
				</c:if>
				<div>
					Establishment Name &emsp;&emsp; Game# &emsp;&emsp; Frame# <!-- &emsp is a spacer-->
				</div>
				<div id="bowling-frame-display">
					<div class="bowling-game">
						<% for (int frameNum = 1; frameNum <= 10; frameNum++) { %>
							<div class="frame">

								<%if(frameNum == 10){  %>
									<div class="score-box score-box-10left"><%= frameNum%></div>
									<div class="score-box score-box-10middle"><%= frameNum%></div>
									<div class="score-box score-box-10right"><%= frameNum%></div>

								<% } 
								else { %>
									<div class="score-box-left"><%= frameNum + 1%></div>
									<div class="score-box-right"><%= frameNum% + 2%></div>
								<% } %>
								 
								<!-- framescore goes below-->
								<%= frameNum %>
							</div>

						<% } %>
					</div>
				</div>
					<br><br>
					<div class="pin-section">
						<div class="row">
							<div class="pin" onclick="togglePin(this)"><input type="hidden" name="pin7" value="up"><span>7</span></div>
							<div class="pin" onclick="togglePin(this)"><input type="hidden" name="pin8" value="up"><span>8</span></div>
							<div class="pin" onclick="togglePin(this)"><input type="hidden" name="pin9" value="up"><span>9</span></div>
							<div class="pin" onclick="togglePin(this)"><input type="hidden" name="pin10" value="up"><span>10</span></div>
							<!--FIxes impossible bug --><input type="hidden" name="pin11" value="up">

						</div>
						<div class="row">
							<div class="pin" onclick="togglePin(this)"><input type="hidden" name="pin4" value="up"><span>4</span></div>
							<div class="pin" onclick="togglePin(this)"><input type="hidden" name="pin5" value="up"><span>5</span></div>
							<div class="pin" onclick="togglePin(this)"><input type="hidden" name="pin6" value="up"><span>6</span></div>
						</div>
						<div class="row">
							<div class="pin" onclick="togglePin(this)"><input type="hidden" name="pin2" value="up"><span>2</span></div>
							<div class="pin" onclick="togglePin(this)"><input type="hidden" name="pin3" value="up"><span>3</span></div>
						</div>
						<div class="row">
							<div class="pin" onclick="togglePin(this)"><input type="hidden" name="pin1" value="up"><span>1</span></div>
						</div>
						<div class="row">
							<div class="shot" name"shotBox" value="0" onClick=""><span>Shot!</span></div>
						</div>
						<div class="row">
							<div class="foul" onclick="setFoul()"><span>F</span></div>
							<div class="miss" onclick="setGutter()"><span>-</span></div>
							<div class="strike" onclick="setStrike()"><span>X</span></div>
							<div class="spare" onclick="setSpare()"><span>/</span></div>
						</div>

						<br>
					</div>
				<button text="Submit Shot" name="submitShot" type="submit">Submit Shot</button>
		  </form>

		  <script>
			const NO_PINS  = 0;
			const ALL_PINS = 10; 

			let pinCount = NO_PINS;

			var shot = document.querySelector('.shot');

			function togglePin(pin){
				pin.classList.toggle("down");
				var hiddenVal = pin.querySelector('input[type="hidden"]');

				if (pin.classList.contains("down")) {
					// If it has the "down" class, set the pin value to "down"
					hiddenVal.value = "down";
					pinCount++;
				} 
				else {
					// If it doesn't have the "down" class, set the pin value to "up"
					hiddenVal.value = "up";
					pinCount--;
				}
				shot.textContent = pinCount;
				shot.value = pinCount

			}
		  </script>
	</body>


