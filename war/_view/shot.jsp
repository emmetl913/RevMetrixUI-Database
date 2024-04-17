<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.ycp.cs320.RevMetrix.model.BallArsenal" %>
<%@ page import="edu.ycp.cs320.RevMetrix.controller.ShotController" %>
<%@ page import="edu.ycp.cs320.RevMetrix.model.Ball" %>
<%@ page import="java.util.ArrayList" %>

<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
        <title>Shot</title>
        <style type="text/css">
            .container{
                text-align: center;
                align-items: center;
                margin-top: 50px;
                position: relative;
            }

            #game-info{
                display: flex;
                justify-content: center;
                align-items: center;
                position: relative;
                margin-right: 20px;
                font-size: 24px;
                font-weight: 900;
            }

            .game-box{
                border: 1px solid black;
                padding: 5px;
                padding-left: 25px;
                padding-right: 50px;
            }

            .frame-box{
                margin-left: 20px;
                border: 1px solid black;
                padding: 5px;
                padding-left: 25px;
                padding-right: 50px;
            }

            .score-box{
                margin-left: 20px;
                border: 1px solid black;
                padding: 5px;
                padding-left: 25px;
                padding-right: 75px;
            }

            .dropdown{
                margin-top: 20px;
                margin-bottom: 20px;
                font-size: 16px;
            }

            .row{
                display: flex;
                justify-content: center;
            }

            .pin{
                width: 45px;
                height: 45px;
                border-radius: 50%;
                border: 1px solid black;
                background-color: black;
                color: white;
                display: flex;
                justify-content: center;
                align-items: center;
                margin: 5px;
                cursor: pointer;
                font-weight: 900;
            }

            .pin-inner {
                font-size: 18px;
            }

            .pin.selected{
                background-color: white;
                color: black;
            }

            .triangle{
                margin-top: 50px;
            }

            .firstShot{
                width: 40px;
                height: 40px;
                background-color: lightslategray;
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

        		/* Style for the black sidebar */
            .sidebar {
                height: 100%;
                width: 250px;
                position: fixed;
                top: 0;
                left: 0;
                background: linear-gradient(to bottom, rgba( 243, 0, 178, 1 ), rgba( 28, 144, 243, 1 ) 95%, rgba( 255, 255, 0, 1 ));
                padding-top: 20px;
            }

            .secondShot{
                width: 40px;
                height: 40px;
                background-color: lightslategray;
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

            .foul{
                width: 40px;
                height: 40px;
                border: 2px solid black;
                margin: 10px;
                display: flex;
                align-items: center;;
                justify-content: center;
                position: relative;
                font-size: 32px;
                font-weight: 900;
            }

            .miss{
                width: 40px;
                height: 40px;
                border: 2px solid black;
                margin: 10px;
                display: flex;
                align-items: center;;
                justify-content: center;
                position: relative;
                font-size: 32px;
                font-weight: 900;
            }

            .strike{
                width: 40px;
                height: 40px;
                border: 2px solid black;
                margin: 10px;
                display: flex;
                align-items: center;;
                justify-content: center;
                position: relative;
                font-size: 32px;
                font-weight: 900;
            }

            .spare{
                width: 40px;
                height: 40px;
                border: 2px solid black;
                margin: 10px;
                display: flex;
                align-items: center;;
                justify-content: center;
                position: relative;
                font-size: 32px;
                font-weight: 900;
            }

            .frame-buttons{
                margin-top: 5px;
            }

            .frame-buttons button{
                margin: 25px;
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
            @media screen and (max-width: 800px) {
                .sidebar {
                    width: 100%;
                    height: auto;
                    position: relative;
                }
                .sidebar a {float: left;}
                div.content {margin-left: 0;}
                .container{
                    flex-direction: column;
                    margin-top: 100px;
                    margin-right: 50px;
                    float: left;
                }
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
             <a href="${pageContext.servletContext.contextPath}/establishmentReg">Establishment Registration</a>
             <a href="${pageContext.servletContext.contextPath}/logIn">Sign Out</a>
             <a href="${pageContext.servletContext.contextPath}/shot">Shot</a>
             <a href="${pageContext.servletContext.contextPath}/ballArsenal">Ball Arsenal</a>
             <a href="https://github.com/emmetl913/RevMetrixUI-Database">GitHub</a>
             <a class="dropbtn" href="#" onclick="toggleDropdown(), nextStep(1)">Start Bowling!</a>
             <div class="dropdown-content" id="myDropdown">
           <a href="${pageContext.servletContext.contextPath}/event">>Event</a>
           <a href="${pageContext.servletContext.contextPath}/session">>Session</a>
           <a href="${pageContext.servletContext.contextPath}/game">>Game</a>
           <a href="${pageContext.servletContext.contextPath}/shot">>Shot</a>
               </div>
       </div>

        <!-- gets variables from the servlet -->
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            BallArsenal ballArsenal = (BallArsenal) request.getAttribute("ballArsenal");
            String shotType = (String) request.getAttribute("shotType");

            Object shot = request.getAttribute("shot");
        %>

        <input type="hidden" id="selected-score-box">
	
		<form action="${pageContext.servletContext.contextPath}/shot" method="post">
            <div class="container">
                <div id="game-info">
                    <!-- no info, so it shows blank -->
                    <div class="game-box" id="gameNumber">
                        <span>Game:   ${sessionScope.gameNumber}</span>
                    </div>
                    <div class="frame-box" id="frameNumber">
                        <span>Frame: </span>
                    </div>
                    <!-- <div class="score-box">
                        <span>Total Score: ${sessionScope.totalScore}</span>
                    </div> -->
                </div>
        
                <!-- drop down menu - selecting a ball -->
                <form action="ShotServlet" method="post" id="ball-form">
                    <div class="dropdown">
                        <select name="ball">
                            <option value="">Select a ball...</option>
                            <c:forEach var="ball" items="${ballArsenal.getBalls()}">
                                <option value="${ball.getName()}">${ball.getName()}</option>
                            </c:forEach>
                            <option value="add">Add Ball... </option>
                        </select>
                    </div>
                </form>

                <div class="triangle">
                    <div class="row">
                        <div class="pin" onclick="togglePin(this)"><span>7</span></div>
                        <div class="pin" onclick="togglePin(this)"><span>8</span></div>
                        <div class="pin" onclick="togglePin(this)"><span>9</span></div>
                        <div class="pin" onclick="togglePin(this)"><span>10</span></div>
                    </div>
                    <div class="row">
                        <div class="pin" onclick="togglePin(this)"><span>4</span></div>
                        <div class="pin" onclick="togglePin(this)"><span>5</span></div>
                        <div class="pin" onclick="togglePin(this)"><span>6</span></div>
                    </div>
                    <div class="row">
                        <div class="pin" onclick="togglePin(this)"><span>2</span></div>
                        <div class="pin" onclick="togglePin(this)"><span>3</span></div>
                    </div>
                    <div class="row">
                        <div class="pin" onclick="togglePin(this)"><span>1</span></div>
                    </div>
    
                    <div class="row">
                        <div id="shot-count"></div>
                        <div class="firstShot" id="score-box1" onclick="highlightSelectedScoreBox('score-box1')" style="background-color: lightslategray;"></div>
                        <div class="secondShot" id="score-box2" style="background-color: lightslategray;"></div>
                    </div>
                </div>
    
                <div class="row">
                    <div class="foul" onclick="setFoul()"><span>F</span></div>
                    <div class="miss" onclick="setGutter()"><span>-</span></div>
                    <div class="strike" onclick="setStrike()"><span>X</span></div>
                    <div class="spare" onclick="setSpare()"><span>/</span></div>
                </div>
    
                <div class="frame-buttons">
                    <button id="previousFrameBtn" type="button">Previous Frame</button>
                    <button id="nextFrameBtn" type="button">Next Frame</button>

                    <!-- <c:if test="${outOfRange}">
                        <div class="error-message">
                            Frame number is out of range. Please select a frame between 1 and 10.
                        </div>
                    </c:if> -->
                </div>

                <div id="error-message" style="color: red;"></div>
            </div>
        </form>

        <script>
            const firstShot = 0;
            const secondShot = 0;

            //const noPins = 0;
            const allPins = 10;

            const minFrame = 1;
            const maxFrame = 12;

            let pinCount = 0;
            let firstShotCount = 0;
            let secondShotCount = 0;
            let shot = firstShot;

            let frameNumber = 1;
            let gameNumber = 1;

             // Highlight the leftmost box when the page loads
            // window.onload = function() {
            //     document.getElementById('box1').classList.add('highlighted');
            // };

            function selectScoreBox(scoreBoxId){
                console.log("selectedScoreBox function called");
                console.log("scoreBoxId: ", scoreBoxId);

                //remove 'selected' class from all score boxes
                document.querySelectorAll('.score-box.selected').forEach(function(scoreBox){
                    scoreBox.classList.remove('selected');
                });

                //add 'selected' to the clicked score box
                let clickedScoreBox = document.getElementById(scoreBoxId);
               
                if(clickedScoreBox){
                    // clickedScoreBox.setAttribute('selected', 'true');

                    console.log("Adding 'selected' class to clicked score box: ", clickedScoreBox);
                    clickedScoreBox.classList.add('selected');
                    highlightSelectedScoreBox(clickedScoreBox);
                }
            }

            //makes the page work - user interface
            document.addEventListener("DOMContentLoaded", function(){

                // highlightSelectedScoreBox('score-box1');

                // const scoreBox2 = document.getElementById('score-box2');
                // if(scoreBox2){
                //     scoreBox2.addEventListener("click", function(){
                //         highlightSelectedScoreBox('score-box2');
                //     });
                // }


                let firstScoreBox = document.getElementById("score-box1");
                let secondScoreBox = document.getElementById("score-box2");

                let frameShots = [];

                initializeUI();
                addEventListeners();

                function initializeUI(){
                    highlightSelectedScoreBox('score-box1');
                    updateFrameNumber(frameNumber);
                    // updateGameNumber(gameNumber);
                    clearErrorMessage();
                }

                function addEventListeners() {
                    firstScoreBox.addEventListener("click", function() {
                        selectScoreBox('score-box1', 'selected-score-box');
                    });

                    secondScoreBox.addEventListener("click", function() {
                        selectScoreBox('score-box2', 'selected-score-box');
                    });

                    const nextFrameBtn = document.getElementById("nextFrameBtn");
                    if (nextFrameBtn) {
                        console.log("Next frame button found");
                        nextFrameBtn.addEventListener("click", function() {
                            console.log("Next frame button clicked");
                            if (frameNumber < 10) {
                                frameNumber++;
                                console.log("Frame number updated: ", frameNumber);
                                updateFrameNumber(frameNumber);
                            } else {
                                console.log("Frame number cannot exceed 10");
                                displayErrorMessage("Frame number cannot exceed 10");
                            }
                        });
                    }else{
                        console.log("Next frame button not found");
                    }

                    const previousFrameBtn = document.getElementById("previousFrameBtn");
                    if (previousFrameBtn) {
                        console.log("Previous frame button found");
                        previousFrameBtn.addEventListener("click", function() {
                            console.log("Previous frame button clicked");
                            if (frameNumber > 1) {
                                frameNumber--;
                                console.log("Frame number updated: ", frameNumber);
                                updateFrameNumber(frameNumber);
                            } else {
                                console.log("Frame number cannot be less than 1");
                                displayErrorMessage("Frame number cannot be less than 1");
                            }
                        });
                    }else{
                        console.log("Previous frame button not found");
                    }
                }

                function displayErrorMessage(message) {
                    const errorMessageElement = document.getElementById("error-message");
                    if (errorMessageElement) {
                        errorMessageElement.textContent = message;
                    }
                }

                function clearErrorMessage() {
                    displayErrorMessage("");
                }
            });

            document.querySelectorAll('.pin').forEach(function(pin) {
                pin.addEventListener("click", function() {
                    console.log("pin clicked");
                    togglePin(pin);
                    updateSelectedScoreBox(calculateTotalPinsLeftStanding());
                    highlightSelectedScoreBox(getSelectedScoreBoxId());
                });
            });

            function togglePin(pin){
                console.log("togglePin function called");
                const isLeave = pin.classList.toggle('selected');

                if(isLeave){
                    pin.style.backgroundColor = 'white'
                    pin.style.color = 'black';
                }else{
                    pin.style.backgroundColor = 'black'
                    pin.style.color = 'white';
                }

                // const pinsLeftStanding = calculateTotalPinsLeftStanding();

            //     updateSelectedScoreBox(calculateTotalPinsLeftStanding());
            //     selectScoreBox(getSelectedScoreBoxId(), 'selected-score-box');
            }

            //function to highlight the selected score box
            function highlightSelectedScoreBox(scoreBox){
                console.log("highlightSelectedScoreBox function called");
                console.log("Selected Score Box: ", scoreBox);

                //removes highlight from all score boxes
                document.querySelectorAll('.firstShot, .secondShot').forEach(box =>{
                    box.style.backgroundColor = 'lightslategray';
                });

                const leftmostBox = document.querySelector('.firstShot');
                if(leftmostBox){
                    leftmostBox.style.backgroundColor = 'orange';
                    leftmostBox.addEventListener('click', function () {
                        leftmostBox.style.backgroundColor = 'orange';
                        const secondBox = document.querySelector('.secondShot');
                        if(secondBox){
                            secondBox.style.backgroundColor = 'lightslategray';
                        }
                    });
                }

                const secondBox = document.querySelector('.secondShot');
                if(secondBox){
                    secondBox.addEventListener('click', function() {
                        secondBox.style.backgroundColor = 'orange';
                        const firstBox = document.querySelector('.firstShot');
                        if(firstBox){
                            firstBox.style.backgroundColor = 'lightslategray';
                        }
                    });
                }
            }

            function getSelectedScoreBoxId(){
                const selectedScoreBox = document.querySelector('.firstShot.selected, .secondShot.selected');
                console.log("Selected Score Box: ", selectedScoreBox);
                if(selectedScoreBox){
                    return selectedScoreBox.id;
                }
            }

            // function calculateScore(){
            //     const pins = document.querySelectorAll('.pin.leave');
            //     const pinsKnockedDown = pins.length;
            //     const pinsLeft = 10-pinsKnockedDown;
            //     return pinsLeft;
            // }

            function clearPins(){
                var pins = document.querySelectorAll(".pin");
                pins.forEach(function(pin){
                    pin.classList.remove('leave');
                });
            }

            function setAllPinsStanding(){
                var pins = document.querySelectorAll('.pin');
                pins.forEach(function(pin){
                    pin.classList.add('leave');
                });

                resetPinCounts();
                clearSecondShot();
            }

            function setGutter(){
                console.log("Gutter button clicked");

                // setAllPinsStanding();
                clearPins();
                // if(shot === firstShot){
                //     clearSecondShot();
                //     document.querySelector('#score-box1').textContent = '-';
                // }else{
                //     document.querySelector('#score-box2').textContent = '-';
                // }

                //check if it's the first shot and clear the the second shot if needed
                // if(shot == firstShot){
                //     setAllPinsStanding();
                //     clearSecondShot();
                // }

                const selectedBoxId = getSelectedScoreBoxId();
                updateSelectedScoreBox(selectedBoxId, '-');
                selectScoreBox(selectedBoxId, 'selected-score-box');
            }

            function setFirstShot(){
                shot = firstShot;
                document.querySelector('#score-box1').style.backgroundColor = 'orange';
                document.querySelector('#score-box2').style.backgroundColor = 'lightslategray';
            }

            function setSecondShot(){
                shot = secondShot;
                document.querySelector('#score-box1').style.backgroundColor = 'lightslategray';
                document.querySelector('#score-box2').style.backgroundColor = 'orange';
            }

            function clearFirstShot(){
                const firstShotElement = document.querySelector('#score-box1');

                if(firstShotElement !== null) {
                    firstShotElement.textContent = ' ';
                }
            }

            function clearSecondShot(){
                const secondShotElement = document.querySelector('#score-box2');

                if(secondShotElement !== null){
                    secondShotElement.textContent = ' ';
                }
            }

            function resetPinCounts(){
                noPins = 0;
                pinCount = noPins;
                firstShotCount = noPins;
                secondShotCount = noPins;
            }

            function setFoul(){
                console.log("foul button clicked")
                if(shot == firstShot){
                    setAllPinsStanding();
                    clearSecondShot();
                }

                const selectedBoxId = getSelectedScoreBoxId();
                updateSelectedScoreBox(selectedBoxId, 'F');
                selectScoreBox(selectedBoxId, 'selected-score-box');

            }

            function setSpare(){
                clearPins();
                setSecondShot();
                if(document.querySelector('#score-box1').textContent !== '-'){
                    if(document.querySelector('#score-box1').textContent !== 'F'){
                        document.querySelector('#score-box1').textContent = firstShotCount; 
                    }
                }
                document.querySelector('#score-box2').textContent = '/';
            }

            function setStrike(){
                clearPins();
                setFirstShot();
                resetPinCounts();
                clearFirstShot();
                document.querySelector('#score-box1').textContent = 'X';
            }

            function getPreviousFrame(){
                clearPins();
                resetPinCounts();

                if(frameNumber > 1){
                    frameNumber--;
                    updateFrameNumber();
                    restorePreviousFrame();
                }
            }

            function getNextFrame(){
                clearPins();
                resetPinCounts();

                if(frameNumber < 10){
                    frameNumber++;
                    updateFrameNumber(frameNumber);
                }
            }

            function getFrameNumber(){
                clearPins();
                resetPinCounts();
                clearFirstShot();
                clearSecondShot();

                if(frameNumber < 10){
                    frameNumber++;
                    updateFrameNumber();
                }
            }

            function updateFrameNumber(frameNumber){
                const frameNumberElement = document.getElementById('frameNumber');
                if(frameNumberElement){
                    if(frameNumber >= 1 && frameNumber <= 10){
                        frameNumberElement.textContent = "Frame: " + frameNumber;
                        document.getElementById('error-message').textContent = "";
                    }else{
                        document.getElementById("error-message").textContent = "Frame number must be between 1 and 10";
                    }
                }
            }

            // function handleFrameChange(){
            //     //update the UI when the user moves to the next frame
            //     clearPins();
            //     const selectedBoxId = getSelectedScoreBoxId();
            //     const pinsLeftStanding = calculateScore();
            //     updateSelectedScoreBox(pinsLeftStanding, selectScoreBoxId);
            // }

            function calculateTotalPinsLeftStanding(){
                const pins = document.querySelectorAll('.pin:not(.leave)');

                let totalPins = 0;
                pins.forEach(function(pin){
                    totalPins += parseInt(pin.textContent);
                });

                let pinsLeftStanding = 10-totalPins;
                return pinsLeftStanding;
            }

            function updateSelectedScoreBox(scoreBoxId, shotType){
                document.querySelectorAll('.firstShot, .secondShot').forEach(box => {
                    box.style.backgroundColor = 'lightslategray';
                });

                const selectedBox = document.getElementById(scoreBoxId);
                if(selectedBox){
                    selectedBox.style.backgroundColor = 'orange';
                    selectedBox.textContent = shotType;
                }
            }

            function initializeFrameShots(){
                frameShots = new Array(maxFrame);
                for(let i = 0; i<maxFrame; i++){
                    frameShots[i] = {
                        firstShot: null,
                        secondShot: null
                    };
                }
            }

            //function to update shot information for the current frame
            function updateFrameShots(){
                frameShots[frameNumber-1].firstShot = firstShotCount;
                frameShots[frameNumber-1].secondShot = secondShotCount;
            }

            //function to restore shot information for the previous frame
            function restorePreviousFrame(){
                if(frameNumber > 1){
                    //Restore shot information from the array for the previous frame
                    firstShotCount = frameShots[frameNumber-2].firstShot;
                    secondShotCount = frameShots[frameNumber-2].secondShot;

                    //update the display with the restored shot information
                    updateFirstShotDisplay();
                    updateSecondShotDisplay();
                }
            }

        </script>
    </body>
</html>