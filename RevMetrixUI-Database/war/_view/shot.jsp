<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
            background-color: gray;
            border: 2px solid black;
            cursor: pointer;
            margin: 20px;
        }

        .selected{
            background-color: orange;
        }
		</style>
	</head>

	<body>
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>
	
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
                    <div class="color-box" id="score-box1" onclick="highlightScoreBox(1)"></div>
                    <div class="color-box" id="score-box2" onclick="highlightScoreBox(2)"></div>
                </div>

                <div class="shot-buttons">
                    <button class="shot-button" onclick="selectShotType('F')">F</button>
                    <button class="shot-button" onclick="selectShotType('-')">-</button>
                    <button class="shot-button" onclick="selectShotType('X')">X</button>
                    <button class="shot-button" onclick="selectShotType('/')">/</button>
                </div>

                <div class="frame-buttons">
                    <button onclick="previousFrame()">Previous Frame</button>
                    <button onclick="nextFrame()">Next Frame</button>
                </div>
            </div>

            <script>
                var selectedPins = [];
                var gameNumber = 1;
                var frameNumber = 1;

                function togglePin(pinNumber){
                    var pin = document.getElementById("pin" + pinNumber);
                    pin.classList.toggle("selected");

                    if(selectedPins.includes(pinNumber)){
                        //remove pines from selectedPins array
                        selectedPins.splice(selectedPins.indexOf(pinNumber), 1);
                    }else{
                        //add pin to the array
                        selectedPins.push(pinNumber);
                    }
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

                function toggleColer(){
                    var box = document.querySelector('.color-box');
                    box.classList.toggle('selected');
                }

                function highlightScoreBox(boxNumber){
                    //reset all score boxes
                    var scoreBoxes = document.querySelectorAll('.color-box');
                    scoreBoxes.forEach(function(box){
                        box.classList.remove('selected');
                    });

                    //highlight the clicked score box
                    var selectedBox = document.getElementById('score-box' + boxNumber);
                    selectedBox.classList.add('selected');
                }

                function updateScoreBoxes(){
                    //calculates the score based on selected pins
                    var pinsRemaining = 10 - selectedPins.length;

                    //update the content of each score box
                    var scoreBoxes = document.querySelectorAll('.color-box');
                    scoreBoxes.forEach(function(box){
                        box.textContent = pinsRemaining;
                    });
                }
            </script>
		</form>
	</body>
</html>