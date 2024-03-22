<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.ycp.cs320.lab02.model.BallArsenal" %>
<%@ page import="edu.ycp.cs320.lab02.model.Ball" %>
<%@ page import="java.util.ArrayList" %>

<html>
	<head>
		<title>Shot</title>
		<style type="text/css">
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
            background-color: rgb(187, 185, 185);
            border: 2px solid black;
            cursor: pointer;
            margin: 20px;

            display: flex;
            justify-content: center;
            align-items: center;

            text-align: center;
            font-size: 16px;
            font-weight: 900;
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

        <div class="sidebar">
			<a href="${pageContext.servletContext.contextPath}/index">
				<img src="${pageContext.request.contextPath}/_view/BowlingBall.png"width="100" height="100">
			  </a>
			<a href="${pageContext.servletContext.contextPath}/startBowling">Start Bowling</a>
			<a href="${pageContext.servletContext.contextPath}/establishmentReg">Establishment Registration</a>
		    <a href="${pageContext.servletContext.contextPath}/logIn">Sign Out</a>
        <a href="${pageContext.servletContext.contextPath}/shot">Shot</a>
        <a href="${pageContext.servletContext.contextPath}/ballArsenal">Ball Arsenal</a>
        <a href="https://github.com/emmetl913/RevMetrixUI-Database">GitHub</a>
		  </div>
	
		<form action="${pageContext.servletContext.contextPath}/shot" method="post">
            <div class="container">
                <!-- The Game and Frame -->
                <div id="game-info">
                    <span>Game:  </span><span class="game-number">1</span>
                    <span>Frame:  </span><span class="frame-number">1</span>
                </div>
                <!-- Invereted equilateral triangle -->
                <div class="row">
                    <div class="circle" id="pin7" onclick="togglePin(7)">7</div>
                    <div class="circle" id="pin8" onclick="togglePin(8)">8</div>
                    <div class="circle" id="pin9" onclick="togglePin(9)">9</div>
                    <div class="circle" id="pin10" onclick="togglePin(10)">10</div>
                </div>
                <div class="row">
                    <div class="circle" id="pin4" onclick="togglePin(4)">4</div>
                    <div class="circle" id="pin5" onclick="togglePin(5)">5</div>
                    <div class="circle" id="pin6" onclick="togglePin(6)">6</div>
                </div>
                <div class="row">
                    <div class="circle" id="pin2" onclick="togglePin(2)">2</div>
                    <div class="circle" id="pin3" onclick="togglePin(3)">3</div>
                </div>
                <div class="row">
                    <div class="circle" id="pin1" onclick="togglePin(1)">1</div>
                </div>

                <!-- Score Boxes -->
                <div class="row">
                    <div class="color-box selected" id="score-box1" onclick="highlightScoreBox(1); updateScoreBoxes()"></div>
                    <div class="color-box" id="score-box2" onclick="highlightScoreBox(2); updateScoreBoxes()"></div>
                </div>

                <div class="shot-buttons" onclick="handleButtonClick(event)">
                    <button class="shot-button" data-score="foul">F</button>
                    <button class="shot-button" data-score="no-pins">-</button>
                    <button class="shot-button" data-score="strike">X</button>
                    <button class="shot-button" data-score="spare">/</button>
                </div>

                <div class="frame-buttons">
                    <button onclick="previousFrame()">Previous Frame</button>
                    <button onclick="nextFrame()">Next Frame</button>
                </div>

                <!-- dropdown menu -->
                <div class="dropdown">
                    <select id="dropdownMenu" name="dropdownOption">
                        <option value="" disabled selected>Select an Option</option>
                        <% 
                            BallArsenal ballArsenal = (BallArsenal) request.getAttribute("ballArsenal");

                            if(ballArsenal != null){
                                for(Ball ball : ballArsenal.getBalls()){
                                    %>
                                    <option value="<%= ball.getName() %>"><%= ball.getName() %></option>
                                    <%
                                }
                            }
                        %>
                    </select>
                </div>
            </div>

            <script>
                var selectedPins = [];
                var gameNumber = 1;
                var frames = [];
                var currentFrameIndex = 0;
                var currentFrame = [];
                var score = 0;

                function initializeFrame(){
                    for(let i = 0; i<10; i++){
                        frames.push([]);
                    }
                }

                function handleButtonClick(event){
                    var target = event.target;
                    if(target.classList.contains('shot-button')){
                        var scoreType = target.getAttribute('data-score');
                        updateScoreAndDisplay(scoreType);
                        event.preventDefault();
                    }
                }


                function togglePin(pinNumber){
                    var pin = document.getElementById("pin" + pinNumber);
                    pin.classList.toggle("selected");

                    if(selectedPins.includes(pinNumber)){
                        //remove pines from selectedPins array
                        selectedPins.splice(selectedPins.indexOf(pinNumber), 1);
                        score--;
                    }else{
                        //add pin to the array
                        selectedPins.push(pinNumber);
                        score++;
                    }
                    updateScoreBoxes();
                }

                function nextFrame(){
                    frameNumber++;
                    updateFrameNumber();
                }

                function previousFrame(){
                    if(frameNumber>1){
                        frameNumber--;
                        updateFrameNumber();
                    }
                }

                function updateFrameNumber(){
                    var frameNumberElement = document.getElementById("frame-number");
                    frameNumberElement.textContent = frameNumber
                }

                function selectShotType(shotType){
                    //set the selected shot type
                    document.getElementById("shot-type").value = shotType;
                }

                function resetPinSelection(){
                    var pins = document.querySelectorAll(".circle");
                    pins.forEach(function(pin){
                        pin.classList.remove("selected");
                    });
                }

                //highlights the first box when page first booted up
                //switches color when the other box is clicked

                function highlightScoreBox(frameNumber){
                    var currentBox = document.getElementById('score-box' + frameNumber);
                    var otherBox = frameNumber === 1 ? document.getElementById('score-box2') : document.getElementById('score-box1');

                    currentBox.classList.add('selected');
                    otherBox.classList.remove('selected');

                    currentFrameIndex = frameNumber - 1;
                    currentFrame = frames[currentFrameIndex];

                    updateScoreBoxes();
                }

                function updateScoreBoxes(){
                    //calculates the score based on selected pins
                    var highlightedBox = document.querySelector('.color-box.selected');
                    //var score = calculateTotalScore();

                    //update the content of each score box
                    if(highlightedBox){ 
                        //score = calculateTotalScore();
                        highlightedBox.textContent = score;
                    }
                }

                function calculateTotalScore(){
                    var totalScore = 0;
                    
                    for(let i = 0; i < frames.length; i++){
                        var frame = frames[i];

                        if(frame.length === 0) continue;    //frame not played

                        var frameScore = frame.reduce((a,b) => a+b, 0);
                        totalScore += frameScore;
                    }
                    return totalScore;
                }

                function updateScoreAndDisplay(scoreType){
                    updateScore(scoreType); //updates score based on type
                    updateScoreBoxes(); //updates via pins
                }

                // function isSpareFrame(frame){
                //     return frame.length === 2 && frame[0] + frame[1] === 10;
                // }

                function updateScore(scoreType, event){
                    event.preventDefault();

                    var currentFrame = frames[currentFrameIndex];

                    //update score based on score type
                    switch(scoreType){
                        case 'foul' : 
                            currentFrame.push('F');
                            break;
                        case 'no-pins' : 
                            currentFrame.push('-');
                            break;
                        case "strike" : 
                            currentFrame.push('X');
                            break;
                        case 'spare' : 
                            // if(currentFrame.length === 1){
                            //     currentFrame.push(10 - currentFrame[0]);
                            // }else{
                            //     currentFrame.push(10);
                            // }
                            currentFrame.push('/');
                            break;
                        default : 
                            break;
                    }
                }

                initializeFrame();
            </script>
		</form>
	</body>
</html>