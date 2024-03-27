<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="edu.ycp.cs320.lab02.model.BallArsenal" %>
<%@ page import="edu.ycp.cs320.lab02.controller.ShotController" %>
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

            .circle-inner {
                font-size: 18px;
            }

            .circle.selected{
                background-color: black;
                color: white;
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
			<a href="${pageContext.servletContext.contextPath}/startBowling">Start Bowling</a>
			<a href="${pageContext.servletContext.contextPath}/establishmentReg">Establishment Registration</a>
		    <a href="${pageContext.servletContext.contextPath}/logIn">Sign Out</a>
            <a href="${pageContext.servletContext.contextPath}/shot">Shot</a>
            <a href="${pageContext.servletContext.contextPath}/ballArsenal">Ball Arsenal</a>
            <a href="https://github.com/emmetl913/RevMetrixUI-Database">GitHub</a>
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
                        <span>Frame:   ${sessionScope.frameNumber}</span>
                    </div>
                    <div class="score-box">
                        <span>Total Score: ${sessionScope.totalScore}</span>
                    </div>
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

                <div class="row">
                    <div class="circle" onclick="togglePin(this)"><span>7</span></div>
                    <div class="circle" onclick="togglePin(this)"><span>8</span></div>
                    <div class="circle" onclick="togglePin(this)"><span>9</span></div>
                    <div class="circle" onclick="togglePin(this)"><span>10</span></div>
                </div>
                <div class="row">
                    <div class="circle" onclick="togglePin(this)"><span>4</span></div>
                    <div class="circle" onclick="togglePin(this)"><span>5</span></div>
                    <div class="circle" onclick="togglePin(this)"><span>6</span></div>
                </div>
                <div class="row">
                    <div class="circle" onclick="togglePin(this)"><span>2</span></div>
                    <div class="circle" onclick="togglePin(this)"><span>3</span></div>
                </div>
                <div class="row">
                    <div class="circle" onclick="togglePin(this)"><span>1</span></div>
                </div>

                <div class="row">
                    <div id="shot-count"></div>
                    <div class="firstShot" id="score-box1" onclick="selectScoreBox('score-box1')" style="background-color: lightslategray;"></div>
                    <div class="secondShot" id="score-box2" onclick="selectScoreBox('score-box2')" style="background-color: lightslategray;"></div>
                </div>
    
                <div class="row">
                    <div class="foul" onclick="setFoul()"><span>F</span></div>
                    <div class="miss" onclick="setGutter()"><span>-</span></div>
                    <div class="strike" onclick="setStrike()"><span>X</span></div>
                    <div class="spare" onclick="setSpare()"><span>/</span></div>
                </div>
    
                <div class="frame-buttons">
                    <button id="previousFrameBtn" onclick="getPreviousFrame()">Previous Frame</button>
                    <button type="submit" id="nextFrameBtn" onclick="incrementFrameNumber(event)">Next Frame</button>
                </div>
            </div>
        </form>

        <script>
            const firstShot = 0;
            const secondShot = 0;

            // function selectScoreBox(scoreBoxId, selectedBoxId){
            //     //remove 'selected' class from all score boxes
            //     document.querySelectorAll('.score-box').forEach(function(scoreBox){
            //         scoreBox.classList.remove('selected');
            //     });

            //     //add 'selected' to the clicked score box
            //     let clickedScoreBox = document.getElementById(scoreBoxId);
            //     // clickedScoreBox.classList.add('selected');

            //     // let totalPinsLeftStanding = calculateTotalPinsLeftStanding();

            //     // clickedScoreBox.textContent = totalPinsLeftStanding;
            //     if(clickedScoreBox){
            //         clickedScoreBox.classList.add('selected');
            //         // let totalPinsLeftStanding = calculateTotalPinsLeftStanding();
            //         // clickedScoreBox.textContent = totalPinsLeftStanding;

            //         // //update the selected box ID
            //         // // document.getElementById(selectedBoxId).value = scoreBoxId;

            //         // let selectedBox = document.getElementById(selectedBoxId);
            //         // if(selectedBox){
            //         //     selectedBox.value = scoreBoxId;
            //         // }
            //     }
            // }

            document.addEventListener("DOMContentLoaded", function(){

                const noPins = 0;
                const allPins = 10;

                const minFrame = 1;
                const maxFrame = 12;

                let pinCount = noPins;
                let firstShotCount = noPins;
                let secondShotCount = noPins;
                let shot = firstShot;

                let frameNumber = 1;
                let gameNumber = 1;

                let firstScoreBox = document.getElementById("score-box1");
                let secondScoreBox = document.getElementById("score-box2");

                let frameShots = [];

                const frameNumberElement = document.getElementById("frameNumber");
                if(frameNumberElement){
                    frameNumberElement.innerHTML = "Frame: " + frameNumber;
                }

                const gameNumberElement = document.getElementById("gameNumber");
                if(gameNumberElement){
                    gameNumberElement.innerHTML = "Game: " + gameNumber;
                }

                function incrementFrameNumber(event) {
                    event.preventDefault();
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "${pageContext.servletContext.contextPath}/ShotServlet", true);
                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                    xhr.onreadystatechange = function() {
                        if (xhr.readyState === XMLHttpRequest.DONE) {
                            if (xhr.status === 200) {
                                var newFrameNumber = xhr.responseText;
                                // Update the frame number display on the page
                                document.getElementById("frameNumber").textContent = "Frame: " + newFrameNumber;
                            } else {
                                // Handle error
                                console.error("Error: " + xhr.status);
                            }
                        }
                    };
                    xhr.send("action=incrementFrameNumber");
                }


                function handleSelectChange(event){
                    if(event.target.value == "add"){
                        location.replace = '${pageContext.servletContext.contextPath}/ballArsenal';
                    }
                }

                //function to toggle selection of score box
                function toggleScoreBoxSelection(scoreBox){
                    //remove selected class from both score boxes
                    firstScoreBox.classList.remove('selected');
                    secondScoreBox.classList.remove('selected');

                    scoreBox.classList.add('selected');
                }

                firstScoreBox.addEventListener("click", function(){
                    selectScoreBoxId = 'score-box1';
                    highlightSelectedScoreBox(firstScoreBox);
                });

                setFirstShot();

                secondScoreBox.addEventListener("click", function(){
                    selectScoreBoxId = 'score-box2';
                    highlightSelectedScoreBox(secondScoreBox);
                });

                setSecondShot();

                // Add event listeners to the pins to handle selection
                document.querySelectorAll('.pin').forEach(function(pin) {
                    pin.addEventListener("click", function() {
                        // Toggle the 'leave' class to select/unselect the pin
                        togglePin(pin);

                        // Calculate the total pins left standing
                        let pinsLeftStanding = calculateTotalPinsLeftStanding();

                        // Update the selected score box with the new score
                        updateSelectedScoreBox(pinsLeftStanding);
                    });
                });
            });

            function togglePin(pin){
                const isLeave = pin.classList.toggle('leave');

                const pins = document.querySelectorAll('.pin:not(.leave)');
                pinCount = pins.length;

                if(pin.classList.contains('leave')){
                    pin.style.backgroundColor = 'black'
                    pin.style.color = 'white';
                    pinCount++;
                }else{
                    pin.style.backgroundColor = 'white'
                    pin.style.color = 'black';
                    pinCount--;
                }

                updateSelectedScoreBox(pinCount, shotType);

                // Update the selected score box
                if (selectedScoreBox) {
                    selectScoreBox(selectedScoreBox.id);
                }

            }

            //function to highlight the selected score box
            function highlightSelectedScoreBox(scoreBox){
                //removes highlight from all score boxes
                document.querySelectorAll('#score-box').forEach(box =>{
                    box.style.backgroundColor = 'lightslategray';
                });

                //highlights selected box
                scoreBox.style.backgroundColor = 'orange';
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
                if(shot == firstShot){
                    setAllPinsStanding();
                    clearSecondShot();
                    document.querySelector('#score-box1').textContent = '-';
                }else{
                    document.querySelector('#score-box2').textContent = '-';
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
                console.log("Foul button clicked");

                if(shot == firstShot){
                    setAllPinsStanding();
                    clearSecondShot();
                    document.querySelector(`#score-box1`).textContent = "F";
                }else{
                    document.querySelector('#score-box2').textContent = "F";
                }
                // updateSelectedScoreBox(null, 'foul');
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
                document.querySelector('#score-box2').textContent = 'X';
            }

            function getPreviousFrame(){
                clearPins();
                resetPinCounts();

                if(frameNumber > minFrame){
                    frameNumber--;
                    updateFrameNumber();
                }
            }

            function getFrameNumber(){
                clearPins();
                resetPinCounts();
                clearFirstShot();
                clearSecondShot();

                if(frameNumber < maxFrame){
                    frameNumber++;
                    updateFrameNumber();
                }
            }

            function updateFrameNumber(){
                const frameNumberElement = document.getElementById('frameNumber');
                if(frameNumberElement){
                    frameNumberElement.innerHTML = "Frame: " + frameNumber;
                }
            }

            function calculateTotalPinsLeftStanding(){
                const pins = document.querySelectorAll('.pin:not(.leave)');

                let totalPins = 0;
                pins.forEach(function(pin){
                    totalPins += parseInt(pin.textContent);
                });

                let pinsLeftStanding = 10-totalPins;
                return pinsLeftStanding;
            }

            function updateSelectedScoreBox(score){
                const selectedScoreBox = document.getElementById(selectedScoreBoxId);
                if(selectedScoreBox){
                    selectScoreBox.textContent = score;
                }
            }

            // function updateSelectedScoreBox(pinCount, shotType) {
            //     const selectedScoreBox = document.querySelector('.score-box.selected');
            //     const secondScoreBox = document.getElementById('score-box2');

            //     if (selectedScoreBox) {
            //         if (shotType === 'foul') {
            //             selectedScoreBox.textContent = 'F';
            //             if(setFirstShot){
            //                 setFirstShot("F");
            //             }else{
            //                 setSecondShot("F");
            //             }
            //         } else if (shotType === 'no-pins') {
            //             selectedScoreBox.textContent = '-';
            //         } else if (shotType === 'strike') {
            //             selectedScoreBox.textContent = 'X';
            //         } else if (shotType === 'spare') {
            //             secondScoreBox.textContent = '/';
            //         } else {
            //             selectedScoreBox.textContent = pinCount;
            //         }
            //     }
            // }


            // function displayShotType(shotType) {
            //     console.log("Shot type: " + shotType);
            //     var highlightedBox = document.querySelector(".score-box.selected");

            //     if (highlightedBox) {
            //         switch (shotType) {
            //             case 'foul':
            //                 highlightedBox.textContent = 'F';
            //                 break;
            //             case 'strike':
            //                 highlightedBox.textContent = 'X';
            //                 break;
            //             case 'spare':
            //                 highlightedBox.textContent = '/';
            //                 break;
            //             case 'gutter':
            //                 highlightedBox.textContent = '-';
            //                 break;
            //             default:
            //                 // If it's not a predefined shot type, assume it's the number of pins
            //                 highlightedBox.textContent = shotType;
            //                 break;
            //         }
            //     }else{
            //         console.error("No selected score box found");
            //     }
            // }


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