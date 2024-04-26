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

           <a href="https://github.com/emmetl913/RevMetrixUI-Database"class="bottom-link2">GitHub</a>
          <a href="${pageContext.servletContext.contextPath}/logIn"class="bottom-link">Sign Out</a>
               </div>
       </div>

        <!-- gets variables from the servlet -->
        

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
                            <c:forEach var="ball" items="${ballArsenal}">
                                <option value="${ball.getId()}">${ball.getName()}</option>
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
                        <div class="firstShot" id="score-box1" style="background-color: lightslategray;">
                            ${formattedShots1}
                        </div>
                        <div class="secondShot" id="score-box2" style="background-color: lightslategray;">
                            ${formattedShots2}
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
                    <button id="previousFrameBtn" name="action" type="button">Previous Frame</button>
                    <button id="nextFrameBtn" name="action" type="button">Next Frame</button>
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

            let clickCount1 = 0;
            let clickCount2 = 0;
            let selectedPinsFirstShot = [];
            let selectedPinsSecondShot = [];

            initializeFrameShots();

            let selectedScoreBoxId = null;

            function selectScoreBox(scoreBoxId){
                console.log("selectedScoreBox function called");
                console.log("scoreBoxId: ", scoreBoxId);

                //remove 'selected' class from all score boxes
                // document.querySelectorAll('.score-box.selected').forEach(function(scoreBox){
                //     scoreBox.classList.remove('selected');
                // });

                if(selectedScoreBoxId !== null){
                    const previousSelectedScoreBox = document.getElementById(selectedScoreBoxId);
                    if(previousSelectedScoreBox){
                        previousSelectedScoreBox.classList.remove('selected');
                    }
                }

                //add 'selected' to the clicked score box
                let clickedScoreBox = document.getElementById(scoreBoxId);
               
                if(clickedScoreBox){
                    // clickedScoreBox.setAttribute('selected', 'true');

                    console.log("Adding 'selected' class to clicked score box: ", clickedScoreBox);
                    clickedScoreBox.classList.add('selected');
                    highlightSelectedScoreBox(clickedScoreBox);
                    selectedScoreBoxId = scoreBoxId;
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
                        console.log("firstScoreBox clicked");
                        // selectScoreBox('score-box1', 'selected-score-box');
                        console.log("Class list before condition: ", firstScoreBox.classList);
                        console.log("first click count: ", clickCount1);
                        // if(firstScoreBox.classList.contains('selected')){
                            // console.log("firstScoreBox selected...");
                            // firstScoreBox.classList.add('selected');
                            // secondScoreBox.classList.remove('selected');

                            // highlightSelectedScoreBox("score-box1");

                            // setFirstShot();
                            // // togglePin(pin);
                            // updateFirstShotDisplay();

                            clickCount1++;
                            if(clickCount1 === 1){
                                console.log("clickCount1 = 1");
                                resetSelectedScoreBox(firstScoreBox);
                                console.log("reset score completed");
                                firstShotScore = 0;
                                updateFirstShotDisplay();
                                console.log("update first shot display completed");
                                clickCount1=0;
                                // resetPinCounts();
                                revertSelectedPinsColor(firstScoreBox);
                                console.log("funtions completed");
                                selectedPinsFirstShot = [];
                            }
                            // return;
                        // }

                        console.log("firstScoreBox is not selected");

                        firstScoreBox.classList.add('selected');
                        secondScoreBox.classList.remove('selected');

                        highlightSelectedScoreBox("score-box1");

                        setFirstShot();
                        updateFirstShotDisplay();

                        selectedPinsFirstShot = document.querySelectorAll('.selected-pin');
                        console.log("selected pins first shot: ", selectedPinsFirstShot);
                    });

                    secondScoreBox.addEventListener("click", function() {
                        // selectScoreBox('score-box2', 'selected-score-box');
                        if(secondScoreBox.classList.contains('selected')){
                            console.log("second score box selected");
                            // secondScoreBox.classList.add('selected');
                            // firstScoreBox.classList.remove('selected');

                            // highlightSelectedScoreBox("score-box2");

                            // secondShotScore = 0;

                            // setSecondShot();

                            // if(firstShotCount !== null){
                            //     const maxPinsSecondShot = 10 - firstShotCount;

                            //     if(secondShotScore > maxPinsSecondShot){
                            //         setSpare();
                            //     }
                            // }

                            // updateSecondShotDisplay();
                            clickCount2++;
                            if(clickCount2 === 2){
                                resetSelectedScoreBox(secondScoreBox);
                                secondShotScore = 0;
                                updateSecondShotDisplay();
                                clickCount2=0;
                                // resetPinCounts();
                                revertSelectedPinsColor(secondScoreBox);
                                console.log("second shot funtions completed");
                                selectedPinsSecondShot = [];
                            }
                            return;
                        }

                        secondScoreBox.classList.add('selected');
                        firstScoreBox.classList.remove('selected');
                        highlightSelectedScoreBox("score-box2");
                        secondShotScore = 0;
                        setSecondShot();
                        if (firstShotCount !== null) {
                            const maxPinsSecondShot = 10 - firstShotCount;
                            if (secondShotScore > maxPinsSecondShot) {
                                setSpare();
                            }
                        }
                        updateSecondShotDisplay();
                        selectedPinsSecondShot = document.querySelectorAll('.selected-pin');
                        console.log("selected pins for second shot: ", selectedPinsSecondShot);
                    });

                    // firstScoreBox.addEventListener("click", function() {
                    //     if(firstScoreBox.classList.contains('selected')){
                    //         initializeShots();
                    //     }else{
                    //         firstScoreBox.classList.add('selected');
                    //         secondScoreBox.classList.remove('selected');
                    //         highlightSelectedScoreBox("score-box1");
                    //         initializeShots();
                    //         selectedPinsFirstShot = document.querySelectorAll('.selected-pin');
                    //         console.log("Selected pins for the first shot: ", selectedPinsFirstShot);
                    //     }
                    // });

                    // secondScoreBox.addEventListener("click", function() {
                    //     if(secondScoreBox.classList.contains('selected')){
                    //         initializeShots();
                    //     }else{
                    //         secondScoreBox.classList.add('selected');
                    //         firstScoreBox.classList.remove('selected');
                    //         highlightSelectedScoreBox("score-box2");
                    //         initializeShots();
                    //         selectedPinsSecondShot = document.querySelectorAll('.selected-pin');
                    //         console.log("Selected pins for the second shot: ", selectedPinsSecondShot);
                    //     }
                    // })

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
                updateSelectedScoreBox(selectedScoreBox, null, calculateTotalPinsLeftStanding());
                highlightSelectedScoreBox(selectedScoreBox);

                if(selectedScoreBox === "score-box2" && isLeave){
                    //const pinsLeftAfterFirstShot = calculatePinsLeftStandingAfterFirstShot(firstShotCount);
                    secondShotScore++;
                    updateSecondShotDisplay();
                }

            }

            //function to highlight the selected score box
            function highlightSelectedScoreBox(scoreBox){
                console.log("highlightSelectedScoreBox function called");
                console.log("Selected Score Box: ", scoreBox);

                const firstScoreBox = document.querySelector('.firstShot');
                console.log("firstShot: ", firstScoreBox);
                const secondScoreBox = document.querySelector('.secondShot');
                console.log("secondShot: ", secondScoreBox);

                if(scoreBox === firstScoreBox){
                    console.log("score-box1 highlighted");
                    firstScoreBox.style.backgroundColor = 'orange';
                    firstScoreBox.classList.add('selected');

                    secondScoreBox.style.backgroundColor = 'lightslategray';
                    secondScoreBox.classList.remove('selected');
                }else if(scoreBox === secondScoreBox){
                    console.log("score-box2 highlighted");
                    secondScoreBox.style.backgroundColor = 'orange';
                    secondScoreBox.classList.add('selected');

                    firstScoreBox.style.backgroundColor = 'lightslategray';
                    firstScoreBox.classList.remove('selected');
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
                    pin.classList.add('selected');
                    pin.style.backgroundColor = 'black';
                    pin.style.color = 'white';
                });
            }

            function initializeShots(){
                firstShotScore = 0;
                updateFirstShotDisplay();
                revertSelectedPinsColor()
                selectedPinsFirstShot = [];
            }

            function invertPinColors(){
                var pins = document.querySelectorAll(".pin");
                pins.forEach(function(pin) {
                    var backgroundColor = pin.style.backgroundColor;
                    pin.style.backgroundColor = pin.style.color;
                    pin.style.color = backgroundColor;
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

                const selectedBoxId = getSelectedScoreBoxId();

                if(selectedBoxId){
                    updateSelectedScoreBox(selectedBoxId, '-', null);
                    selectScoreBox(selectedBoxId);
                }else{
                    console.log("no score box selected");
                }
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
                console.log("foul button clicked");

                const selectedBoxId = getSelectedScoreBoxId();

                if(selectedBoxId){
                    updateSelectedScoreBox(selectedBoxId, 'F', null);
                    selectScoreBox(selectedBoxId);
                }else{
                    console.log("no score box selected");
                }
            }

            function setSpare(){
                clearPins();
                setSecondShot();

                const secondShotBox = document.getElementById("score-box2");
                secondShotBox.textContent = '/';

                const firstShotScore = document.getElementById("score-box1").textContent;
                const firstShotValue = firstScoreBox.textContent;
                if(firstShotValue !== 'X'){
                    secondShotBox.textContent = '/';
                }

                updateSecondShotDisplay();
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
                //resetPinCounts();

                if(frameNumber > 1){
                    restorePreviousFrame();
                    frameNumber--;
                    updateFrameNumber(frameNumber);
                }
            }

            function getNextFrame(){
                // clearPins();
                invertPinColors();
                // resetPinCounts();

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

                    const restoredFirstShot = previousFrame.firstShot;
                    const restoredSecondShot = previousFrame.secondShot;

                    console.log("First shot count: ", firstShotCount);
                    console.log("Second shot count: ", secondShotCount);

                    if(restoredFirstShot !== null){
                        firstShotCount = restoredFirstShot;
                        updateFirstShotDisplay()
                    }

                    if(restoredSecondShot !== null){
                        secondShotScore = restoredSecondShot;
                        updateSecondShotDisplay();
                    }

                    // updateFirstShotDisplay();
                    // updateSecondShotDisplay();

                    console.log("Previous frame restored successfully");
                    
                }else{
                    console.log("Shot information for previous frame is not available");
                }                
            }

            //function to update the first shot display
            function updateFirstShotDisplay(){
                const firstShotBox = document.getElementById("score-box1");
                // firstShotBox.textContent = firstShotCount !== null ? firstShotCount : "";

                if(firstShotCount !== null){
                    console.log("First shot restored: ", firstShotCount);
                    firstShotBox.textContent = firstShotCount;
                }else{
                    firstShotBox.textContent = "N/A";
                }
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

                secondShotBox.textContent = secondShotScore;
            }

            function calculatePinsLeftStandingAfterFirstShot(firstShotCount){
                return 10 - firstShotCount;
            }

            function selectScoreBoxAndSubmit(scoreBoxId){
                selectScoreBox(scoreBoxId);

                var firstShotValue = null;
                var secondShotValue = null;

                if(scoreBoxId === 'score-box1'){
                    firstShotValue = document.getElementById('score-box1').textContent.trim();
                }else if(scoreBoxId === 'score-box2'){
                    secondShotValue = document.getElementById('score-box2').textContent.trim();
                }

                var url = '${pageContext.servletContext.contextPath}/shot?firstShotScore=' + encodeURIComponent(firstShotValue) + '&secondShotScore=' + encodeURIComponent(secondShotValue);

                window.location.href = url;
            }

            function resetSelectedScoreBox(scoreBox){
                //resets score box to 0
                scoreBox.classList.remove('selected');
            }

            function revertSelectedPinsColor(scoreBox){
                console.log("revert selected pins function called");
                const selectedPins = scoreBox.querySelectorAll('${scoreBoxId} .pin.selected');
                selectedPins.forEach(pin => {
                    pin.style.backgroundColor = 'white';
                    pin.style.color = 'black';
                });
                console.log("pins reverted");
            }

        </script>
    </body>
</html>