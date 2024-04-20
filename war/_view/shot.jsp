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
                /* margin-right: 15px; */
                margin-left: 25px;
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
                background-color: white;
                color: black;
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
                background-color: black;
                color: white;
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
                        <span>Frame: <%= request.getAttribute("frameNumber") %></span>
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
                        <div class="firstShot" id="score-box1" onclick="highlightSelectedScoreBox('score-box1')" style="background-color: lightslategray;">
                            <c:forEach var="shot" items="${formattedShots1}">
                                ${shot}
                            </c:forEach>
                        </div>
                        <div class="secondShot" id="score-box2" style="background-color: lightslategray;">
                            <c:forEach var="shot" items="${formattedShots2}">
                                ${shot}
                            </c:forEach>
                        </div>
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
            const firstShot = null;
            const secondShot = null;

            //const noPins = 0;
            const allPins = 10;

            const minFrame = 1;
            const maxFrame = 12;

            let pinCount = 0;
            let firstShotCount = 0;
            let secondShotCount = 0;
            let shot = firstShot;

            let secondShotScore = 0;

            let frameNumber = 1;
            let gameNumber = 1;

            let frameShots = [];

            initializeFrameShots();

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

                let firstScoreBox = document.getElementById("score-box1");
                let secondScoreBox = document.getElementById("score-box2");

                initializeUI();
                addEventListeners();

                function initializeUI(){
                    highlightSelectedScoreBox(firstScoreBox);
                    updateFrameNumber(frameNumber);
                    // updateGameNumber(gameNumber);
                    clearErrorMessage();
                }

                function addEventListeners() {
                    firstScoreBox.addEventListener("click", function() {
                        // selectScoreBox('score-box1', 'selected-score-box');
                        if(!firstScoreBox.classList.contains('selected')){
                            firstScoreBox.classList.add('selected');
                            secondScoreBox.classList.remove('selected');

                            setFirstShot();
                            // togglePin(pin);
                            updateFirstShotDisplay();
                        }
                    });

                    secondScoreBox.addEventListener("click", function() {
                        // selectScoreBox('score-box2', 'selected-score-box');
                        if(!secondScoreBox.classList.contains('selected')){
                            secondScoreBox.classList.add('selected');
                            firstScoreBox.classList.remove('selected');

                            secondShotScore = 0;

                            setSecondShot();
                            // togglePin(pin);
                            // calculateSecondShotScore();

                            // let pinsLeftAfterFirstShot = calculatePinsLeftStandingAfterFirstShot(firstShotCount);

                            // // const firstShotCount = parseInt(document.getElementById("score-box1").textContent);
                            // const secondShotScore = 1;

                            if(firstShotCount !== null){
                                const maxPinsSecondShot = 10 - firstShotCount;

                                if(secondShotScore > maxPinsSecondShot){
                                    setSpare();
                                }
                            }

                            // // updateSecondShotDisplay();
                            // secondShotScore = Math.min(secondScoreScore + 1, pinsLeftAfterFirstShot);

                            // secondScoreBox.textContent = secondShotScore;

                            // secondScoreBox.textContent = '1';
                            updateSecondShotDisplay();
                        }
                    });

                    const nextFrameBtn = document.getElementById("nextFrameBtn");
                    if (nextFrameBtn) {
                        console.log("Next frame button found");
                        nextFrameBtn.addEventListener("click", function() {
                            console.log("Next frame button clicked");
                            if (frameNumber < 10) {
                                // frameNumber++;
                                // console.log("Frame number updated: ", frameNumber);
                                // updateFrameNumber(frameNumber);
                                // clearFirstShot();
                                // clearSecondShot();
                                clearPins();
                                updateFrameShots();

                                getNextFrame();
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
                                // frameNumber--;
                                // console.log("Frame number updated: ", frameNumber);
                                // updateFrameNumber(frameNumber);
                                // restorePreviousFrame();
                                getPreviousFrame();
                                console.log("Loaded previous frame successfully");
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

            let secondScoreBoxClicked = false;

            function togglePin(pin){
                console.log("togglePin function called");
                // const isLeave = pin.classList.toggle('selected');
                const selectedScoreBox = getSelectedScoreBoxId();
                // console.log("isLeave: ", isLeave);
                console.log("Second Shot initialize: ", secondShotScore);

                if(!selectedScoreBox){
                    console.log("No selected shot box");
                    return;
                }

                const isLeave = pin.classList.toggle('selected');
                console.log("isLeave: ", isLeave);

                if(isLeave){
                    pin.style.backgroundColor = 'black'
                    pin.style.color = 'white';
                }else{
                    pin.style.backgroundColor = 'white'
                    pin.style.color = 'black';
                }

                // const pinsLeftStanding = calculateTotalPinsLeftStanding();
                updateSelectedScoreBox(getSelectedScoreBoxId(), null, calculateTotalPinsLeftStanding());
                highlightSelectedScoreBox(getSelectedScoreBoxId());

                const secondShotBox = document.getElementById("score-box2");

                if(selectedScoreBox === "score-box2" && secondShotBox.classList.contains('selected')){
                    secondScoreBoxClicked = true;
                }

                if(selectedScoreBox === "score-box2" && secondScoreBoxClicked){
                    const pinsLeftAfterFirstShot = calculatePinsLeftStandingAfterFirstShot(firstShotCount);
                    secondShotScore++;
                    updateSecondShotDisplay();
                }

                // if(selectedScoreBox === "score-box1"){
                //     firstShotCount = pinsLeftStanding;
                // }else if(selectedScoreBox === "score-box2"){
                //     if(isLeave){
                //         if(secondShotScore < (10-firstShotCount)){
                //             secondShotScore++;
                //         }
                //     }else{
                //         if(secondShotScore > 1){
                //             secondShotScore--;
                //         }
                //     }

                //     //stays within the range
                //     // if(secondShotScore < 1){
                //     //     secondShotScore = 1;
                //     // }else if(secondShotScore > (10-firstShotCount)){
                //     //     secondShotScore = 10 - firstShotCount;
                //     // }

                //     //update second shot display
                    // const secondScoreBox = document.getElementById("score-box2");
                    // secondScoreBox.textContent = secondShotScore;
                // }

                // const secondShotBox = document.getElementById("score-box2");
                // if(secondShotBox.classList.contains("selected")){
                //     secondShotBox.textContent = pinsLeftStanding;
                // }

                // updateSelectedScoreBox(getSelectedScoreBoxId(), null, calculateTotalPinsLeftStanding());
                // highlightSelectedScoreBox(getSelectedScoreBoxId());

                // if(secondShotBox.classList.contains("selected")){
                //     highlightSelectedScoreBox("score-box2");
                // }
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
                        leftmostBox.classList.add('selected');

                        const secondBox = document.querySelector('.secondShot');
                        if(secondBox){
                            secondBox.style.backgroundColor = 'lightslategray';
                            secondBox.classList.remove('selected');
                        }
                    });
                }

                const secondBox = document.querySelector('.secondShot');
                if(secondBox){
                    secondBox.addEventListener('click', function() {
                        secondBox.style.backgroundColor = 'orange';
                        secondBox.classList.add('selected');

                        const firstBox = document.querySelector('.firstShot');
                        if(firstBox){
                            firstBox.style.backgroundColor = 'lightslategray';
                            firstBox.classList.remove('selected');
                        }
                    });
                }
            }

            function getSelectedScoreBoxId(){
                const selectedScoreBox = document.querySelector('.firstShot.selected, .secondShot.selected');
                console.log("Selected Score Box: ", selectedScoreBox);
                if(selectedScoreBox){
                    console.log("ID: ", selectedScoreBox.id);
                    return selectedScoreBox.id;
                }
            }

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

                const selectedBoxId = getSelectedScoreBoxId();
                updateSelectedScoreBox(selectedBoxId, '-', null);
                selectScoreBox(selectedBoxId);
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
                secondShotScore = 0;
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
                updateSelectedScoreBox(selectedBoxId, 'F', null);
                selectScoreBox(selectedBoxId);

            }

            function setSpare(){
                clearPins();
                setSecondShot();

                const firstShotScore = document.querySelector('#score-box1').textContent;
                console.log("First shot score: ", firstShotScore);

                //if the first shot box had a score, update the first shot box with that score
                if(firstShotScore !== 'X' && firstShotScore !== ''){
                    document.querySelector('#score-box1').textContent = firstShotCount;
                }

                document.querySelector('#score-box2').textContent = '/';

                // //if the first shot box had a score, update the first shot box with that score
                // if(firstShotScore !== 'X' && firstShotScore !== ''){
                //     document.querySelector('#score-box1').textContent = firstShotCount;
                // }
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
                    restorePreviousFrame();
                    frameNumber--;
                    updateFrameNumber(frameNumber);
                }
            }

            function getNextFrame(){
                clearPins();
                resetPinCounts();

                if(frameNumber < 10){
                    frameNumber++;
                    updateFrameNumber(frameNumber);
                    clearFirstShot();
                    clearSecondShot();
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
                    if(frameNumber >= 1 && frameNumber <= maxFrame){
                        frameNumberElement.textContent = "Frame: " + frameNumber;
                        document.getElementById('error-message').textContent = "";
                    }else{
                        document.getElementById("error-message").textContent = "Frame number must be between 1 and 10";
                    }
                }
            }

            function calculateTotalPinsLeftStanding(){
                const totalPins = 10;
                const pins = document.querySelectorAll('.pin:not(.selected)').length;

                const pinsKnockedOver = totalPins - pins;
                //const pinsLeftStanding = pins.length;

                return pinsKnockedOver;
            }

            function updateSelectedScoreBox(scoreBoxId, shotType, pinsLeftStanding){
                // const pinsLeft = document.querySelectorAll('.pin:not(.selected)').length;

                console.log("ScoreBoxID: ", scoreBoxId);
                console.log("shotType: ", shotType);
                console.log("Pins knocked over: ", pinsLeftStanding);

                document.querySelectorAll('.firstShot, .secondShot').forEach(box => {
                    box.style.backgroundColor = 'lightslategray';
                });

                const selectedBox = document.getElementById(scoreBoxId);
                console.log("selectedBox: ", selectedBox);
                if(selectedBox){
                    selectedBox.style.backgroundColor = 'orange';
                    if(shotType !== null){
                        selectedBox.textContent = shotType;
                    }else{
                        selectedBox.textContent = pinsLeftStanding;
                    }
                }
                console.log("function complete");
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
                //ensure frameShots exist
                if(!frameShots){
                    initializeFrameShots();
                }

                frameShots[frameNumber-1] = {
                    firstShot: firstShotCount,
                    secondShot: secondShotCount
                };
            }

            //function to restore shot information for the previous frame
            function restorePreviousFrame(){
                console.log("Restoring...");
                // console.log("frameNumber: ", frameNumber);
                // console.log("frameShots: ", frameShots);
                
                if(frameNumber === 1){
                    console.log("Cannot restore previous frame because this is the first frame");
                    return;
                }

                const previousFrameIndex = frameNumber-2;

                if(frameShots[previousFrameIndex]){
                    const previousFrame = frameShots[previousFrameIndex];

                    firstShotCount = previousFrame.firstShot;
                    secondShotCount = previousFrame.secondShot;

                    console.log("First shot count: ", firstShotCount);
                    console.log("Second shot count: ", secondShotCount);


                    updateFirstShotDisplay();
                    updateSecondShotDisplay();

                    console.log("Previous frame restored successfully");
                    
                }else{
                    console.log("Shot information for previous frame is not available");
                }                
            }

            //function to update the first shot display
            function updateFirstShotDisplay(){
                const firstShotBox = document.getElementById("score-box1");
                firstShotBox.textContent = firstShotCount !== null ? firstShotCount : "";
            }

            //function to update the second shot display
            function updateSecondShotDisplay(){
                const secondShotBox = document.getElementById("score-box2");
                // secondShotBox.textContent = secondShotCount !== null ? secondShotCount : "";

                if(firstShotCount !== null){
                    const maxPinsSecondShot = 10 - firstShotCount;

                    if(secondShotScore <= maxPinsSecondShot){
                        secondShotBox.textContent = secondShotScore;
                    }else{
                        setSpare();
                    }
                }

                // const maxPinsSecondShot = 10 - firstShotCount;

                // if(!secondShotBox){
                //     return;
                // }

                // if(secondShotScore > maxPinsSecondShot){
                //     console.log("Too may pins");
                // }

                // const secondShotScore = Math.min(maxPinsSecondShot, 1);

                // const secondShotScore = calculateSecondShotScore();

                secondShotBox.textContent = secondShotScore;
            }

            // let currentSecondShotScore = 1;

            //function to calculate the second shot
            function calculateSecondShotScore(){
                // const pinsLeftStandingAfterFirstShot = 10 - firstShotCount;
                // console.log("pins left standing after first shot: ", pinsLeftStandingAfterFirstShot);

                // //strike
                // // if(pinsLeftStandingAfterFirstShot === 10){
                // //     setStrike();
                // //     return;
                // // }

                // //no pins left standing - strike
                // if(pinsLeftStandingAfterFirstShot === 0){
                //     setStrike();
                //     return;
                // }

                // return Math.min(0, 10-firstShotCount) + 1;

                let secondShotScore = 1;
                return secondShotScore;
            }

            function calculatePinsLeftStandingAfterFirstShot(firstShotCount){
                return 10 - firstShotCount;
            }

        </script>
    </body>
</html>