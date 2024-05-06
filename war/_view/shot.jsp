<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.ycp.cs320.RevMetrix.model.BallArsenal" %>
<%@ page import="edu.ycp.cs320.RevMetrix.controller.ShotController" %>
<%@ page import="edu.ycp.cs320.RevMetrix.model.Ball" %>
<%@ page import="edu.ycp.cs320.RevMetrix.model.Frame" %>
<%@ page import="edu.ycp.cs320.RevMetrix.model.Shot" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>


<%
// Retrieve ArrayList from session attribute

    BallArsenal model = (BallArsenal) session.getAttribute("ballArsenalKey");
    ArrayList<Ball> balls = (model != null) ? model.getBalls() : null;
	List<Frame> frames = (List<Frame>)session.getAttribute("frameList");
	String establishmentName = (String)session.getAttribute("shotEstablishmentName");
    int selectedBallID = (int) request.getAttribute("selectedBallID");
	int shotNum = (int)request.getAttribute("currentShotNumber");
	int frameNum = (int)request.getAttribute("currentFrameNumber");
	String prevShot1Pins =(String)request.getAttribute("shot1PinsLeft");
%>
<html lang="en">
	<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Bowling Page</title>
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
			.container	 {
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
		.pin-section{
		}
		.dropdown {
            margin-top: 20px;
            margin-bottom: 20px;
            font-size: 16px;
			//float:right;
			
        }
		.dropdown select {
			padding: 8px;
			font-size: 14px;
			border: 1px solid #ccc; /* Add this line to set the border */
		//	float: left;
		}
		pinsandarsenal{
		//	display: flex;
			align-items: center; /* Align items to the top */

		}
		.gameScores {
			display: flex;
			justify-content: space-around;
			margin-top: 20px;
		}

		/* Style for individual game score boxes */
		.gameScoreBox {
			width: calc(25% - 20px); /* Adjust as needed */
			height: 20px;
			border: 1px solid black;
			box-sizing: border-box;
			padding: 10px;
			background-color: #e0e0e0;
			display: flex;
			justify-content: center;
			align-items: center;
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
          <a href="${pageContext.servletContext.contextPath}/stats">Stats</a>
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
					<!--<%=establishmentName%> its null for now-->Establishment Name &emsp;&emsp; Game# &emsp;&emsp; Frame#: <%=frameNum%> <!-- &emsp is a spacer-->
				</div>
				<div id="bowling-frame-display">
					<div class="bowling-game">
					<% if (frames != null) { %>

						<%for (Frame frame: frames) { 
							if(frame.getFrameNumber() != 11 && frame.getFrameNumber() != 12){ %>
								<div class="frame">

									<%if(frame.getFrameNumber() == 10){  
										if (frame.getShot1() != null){ 
											String symbol = frame.getShot1().getPinsLeft();
											if(symbol.equals("X") || symbol.equals("F")){ %>
											 	<div class="score-box-10left"><%=symbol%></div> 
										  <%}
											else{
												String symbol2 = "";
												if(frame.getShot1().getCount() == 0){
													symbol2 = "-";%>
											 		<div class="score-box-10left"><%=symbol2%></div> <%
												}
												else{%>
											 		<div class="score-box-10left"><%=frame.getShot1().getCount()%></div> <%
												}
											} 
										
										}
										else{
											%><div class="score-box-10left">&nbsp</div> <%
										}
										if(frames.get(9).getShot1()!=null){
											if(!frames.get(9).getShot1().getPinsLeft().equals("X")){
												if(frame.getShot2()!=null){ 
													String symbol = frame.getShot2().getPinsLeft();
													if(symbol.equals("/") || symbol.equals("F")){ %>
														<div class="score-box-10middle"><%=frame.getShot2().getPinsLeft()%></div>
												  <%}
													else{
														if(frame.getShot2().getCount() == 0){
															%> <div class="score-box-10middle">-</div> <%
														}
														else{%>
															<div class="score-box-10middle"><%=frame.getShot2().getCount()%></div>
													 <%	}
													}
												 }
											}
										}
										else{
											Shot frame11shot1 = frames.get(10).getShot1();
											if(frame11shot1 != null){
													String symbol = frame11shot1.getPinsLeft();
													if(symbol.equals("/") || symbol.equals("F")){ %>
															<div class="score-box-10middle"><%=frame11shot1.getPinsLeft()%></div>
													<%}
													else{
														if(frame11shot1.getCount() == 0){
															%> <div class="score-box-10middle">-</div> <%
														}
													else{%>
															<div class="score-box-10middle"><%=frame11shot1.getCount()%></div>
												  <%}
												}
												
											}
										
										else{
											%><div class="score-box-10middle">&nbsp</div> <%
										}
										
									 } 
									}
									else { 
										if (frame.getShot1()!= null){ 
											String symbol = frame.getShot1().getPinsLeft();
											if(symbol.equals("X") || symbol.equals("F")){ %>
											 	<div class="score-box-left"><%=symbol%></div> 
										  <%}
											else{
												String symbol2 = "";
												if(frame.getShot1().getCount() == 0){
													symbol2 = "-";%>
											 		<div class="score-box-left"><%=symbol2%></div> <%
												}
												else{%>
											 		<div class="score-box-left"><%=frame.getShot1().getCount()%></div> <%
												}
											} 
										}
										else{
											%><div class="score-box-left">&nbsp</div> <%
										}
										if(frame.getShot2()!=null){ 
											String symbol = frame.getShot2().getPinsLeft();
											if(symbol.equals("/") || symbol.equals("F")){ %>
												<div class="score-box-right"><%=frame.getShot2().getPinsLeft()%></div>
										  <%}
											else{
												if(frame.getShot2().getCount() == 0){
													%> <div class="score-box-right">-</div> <%
												}
												else{%>
													<div class="score-box-right"><%=frame.getShot2().getCount()%></div>
											 <%	}
											}
										}
										else{
											%><div class="score-box-right">&nbsp</div> <%
										}
										
								  } %>
								 
									<!-- framescore goes below-->
									<% 
									if(frame.getScore() != -1){
										%> <%= frame.getScore() %> <%
									}
									%>

								</div>

						<%  }
						}
					}	else{%>
					<p>frames are null <p>
					<%}%>
					</div>
				</div>
					<br><br>
					<div class ="pinsandarsenal">
						<div class="pin-section">
							<div class="row">
								<div class="pin" onclick="togglePin(this)"><input type="hidden" name="pin7" value="up"><span>7</span></div>
								<div class="pin" onclick="togglePin(this)"><input type="hidden" name="pin8" value="up"><span>8</span></div>
								<div class="pin" onclick="togglePin(this)"><input type="hidden" name="pin9" value="up"><span>9</span></div>
								<div class="pin" onclick="togglePin(this)"><input type="hidden" name="pin10" value="up"><span>10</span></div>
								<!--FIxes impossible bugs --><input type="hidden" name="pin11" value="up">
								<input type = "hidden" name="spareFixplus1" value="0">

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
								<div class="shot"><span>Shot!</span></div>
								<input type="hidden" value="0" name="shotBox">

							</div>
							<div class="row">
								<div class="strike" onclick="setStrike()"><span>X</span></div>
								<div class="spare" onclick="setSpare()"><span>/</span></div>
								<div class="foul" onclick="setFoul()"><span>F</span></div>
								<div class="miss" onclick="setGutter()"><span>-</span></div>
							</div>

							<br>
						</div>
						<div class="dropdown">
							<p style="font-size: 16px; margin-bottom: 5px;">Ball Arsenal</p>
							<select name="ballArsenalDropdown" style="padding: 8px; font-size: 14px;">
								<option value="" disabled selected>Select a ball</option> <!-- Placeholder option -->

								<%
								if (balls != null && !balls.isEmpty()) {
								for (Ball ball : balls) { 
									String selected = (ball.getBallId() == selectedBallID) ? "selected" : "";%>
									<option value="<%= ball.getBallId() %>" <%= selected %>><%= ball.getName() %></option>
								<% System.out.println(ball.getName());
								}
								}else{%>
								<p>You must add ball(s) to the Ball Arsenal</p>
								<%}
								%>
							</select>
						</div>
					</div> <!-- this closes pinsandarsenal container div-->
					<div class="gameScores">
						<div class="gameScoreBox">Game1 Total: </div>
						<div class="gameScoreBox">Game2 Total: </div>
						<div class="gameScoreBox">Game3 Total: </div>
						<div class="gameScoreBox">Series Total: </div>
					</div> 
					<br>
				<button text="Submit Shot" name="submitShot" type="submit" onclick="">Submit Shot</button>
			</div>
		  </form>

		  <script>
			const NO_PINS  = 0;
			const ALL_PINS = 10; 

			let pinCount = NO_PINS;
			shotNum = 0;

			var shot = document.querySelector('.shot');
			var shotBox = document.querySelector('input[name="shotBox"]'); // Select the hidden input field

			window.onload =function(){
				var shotNum = <%= shotNum %>; // Get the value of shotNum from your JSP variable

				var strikeDiv = document.querySelector(".strike");
				var spareDiv = document.querySelector(".spare");

				if (shotNum === 1) {
					strikeDiv.style.display = "block";
					spareDiv.style.display = "none";
				} else if (shotNum === 2) {
					strikeDiv.style.display = "none";
					spareDiv.style.display = "block";
				}

				updatePins();
			}
			
			function togglePin(pin){
				 if (!pin.classList.contains("locked")) {
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
					shotBox.value = pinCount;
				 }
			}
			function setAllPinsDown() {
				var pins = document.querySelectorAll('.pin');

				let lockedPins = 0;

				// Count the number of locked pins
				pins.forEach(function(pin) {
					if (pin.classList.contains('locked')) {
						lockedPins++;
					}
				});

				// Calculate the number of remaining pins
				const remainingPins = 10 - lockedPins;
				pins.forEach(function(pin) {
					pin.classList.add('down');
					var hiddenVal = pin.querySelector('input[type="hidden"]');
					hiddenVal.value = "down";
				});
			
				pinCount = remainingPins;
				shotBox.value = pinCount;
				shot.textContent = pinCount;
			}
			function resetUnlockedPinsToUp() {
				const pins = document.querySelectorAll('.pin');
    
				// Loop through each pin element
				pins.forEach(function(pin) {
					if (!pin.classList.contains('locked') && pin.classList.contains('down')) {
						const hiddenVal = pin.querySelector('input[type="hidden"]');
						hiddenVal.value = "up";
						pin.classList.remove('down');
					}
				});
			}
			function setStrike(){
				setAllPinsDown();
			}
			function setSpare(){
				setAllPinsDown();
			    document.querySelector('input[name="spareFixplus1"]').value = "1";
			}
			function setFoul() {
				var shotbox = document.getElementById("shotbox");
				shot.textContent = "F";
				shotBox.value = "F";
				resetUnlockedPinsToUp();
				pinCount = 0;
			}
			function setGutter(){
				shot.textContent = "-";
				const shotNum = <%= shotNum %>; 
				resetUnlockedPinsToUp();
				shotBox.value = "0";
				if(shotNum == 1)
				pinCount = 0;

			}


			function updatePins() {
				const prevShot1Pins = '<%= prevShot1Pins %>'; // Accessing the JSP variable
				const shotNum = <%= shotNum %>; // Accessing the JSP variable
				const pinCount = 0;
				// Only update pins if shotNum is 2
				if (shotNum === 2) {
					// Convert the string to an array for easier manipulation
					const pinsArray = prevShot1Pins.split('');

					// Map pin numbers to their corresponding elements
					const pinMap = {
						'1': document.querySelector('input[name="pin1"]').parentNode,
						'2': document.querySelector('input[name="pin2"]').parentNode,
						'3': document.querySelector('input[name="pin3"]').parentNode,
						'4': document.querySelector('input[name="pin4"]').parentNode,
						'5': document.querySelector('input[name="pin5"]').parentNode,
						'6': document.querySelector('input[name="pin6"]').parentNode,
						'7': document.querySelector('input[name="pin7"]').parentNode,
						'8': document.querySelector('input[name="pin8"]').parentNode,
						'9': document.querySelector('input[name="pin9"]').parentNode,
						'10': document.querySelector('input[name="pin10"]').parentNode
					};

					// Check if prevShot1Pins is "F", then load all pins as up
					if (prevShot1Pins === "F") {
						Object.keys(pinMap).forEach(function(pinNum) {
							const pinElement = pinMap[pinNum];
							pinElement.classList.remove('down');
							pinElement.classList.remove('locked');
							pinElement.querySelector('input[type="hidden"]').value = "up";
						});
						pinCount = 0;
					}else 
					{
						// Loop through each pin element
						Object.keys(pinMap).forEach(function(pinNum) {
							const pinElement = pinMap[pinNum];
							// Check if the pin number is included in the string
							if (pinNum === '10' && pinsArray.includes('0') || pinsArray.includes(pinNum)) {
								// Set the pin to up
								pinElement.classList.remove('down');
								pinElement.querySelector('input[type="hidden"]').value = "up";
							} else {
								// Set the pin to down
								pinElement.classList.add('down');
								pinElement.classList.add('locked');
								pinElement.querySelector('input[type="hidden"]').value = "down";
							}
						});
						// Update the pin count
						pinCount = 10 - pinsArray.length;
					}

					
					document.querySelector('.shot').textContent = pinCount;
					document.querySelector('input[name="shotBox"]').value = pinCount;
				}
			}


		  </script>
	</body>
</html>
