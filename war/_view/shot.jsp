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
    //HttpSession session = request.getSession();
    BallArsenal model = (BallArsenal) session.getAttribute("ballArsenalKey");
    ArrayList<Ball> balls = (model != null) ? model.getBalls() : null;
%>

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

            .firstShot.selected
            .secondShot.selected{
                background-color: orange;
                color: black;
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
             <a href="${pageContext.servletContext.contextPath}/ballArsenal">Ball Arsenal</a>
             <a href="https://github.com/emmetl913/RevMetrixUI-Database">GitHub</a>
             <a class="dropbtn" href="#" onclick="toggleDropdown(), nextStep(1)">Start Bowling!</a>
             <div class="dropdown-content" id="myDropdown">
 	        <a href="${pageContext.servletContext.contextPath}/event">&nbsp&nbsp >Event</a>
	        <a href="${pageContext.servletContext.contextPath}/session">&nbsp&nbsp >Session</a>
	        <a href="${pageContext.servletContext.contextPath}/game">&nbsp&nbsp >Game</a>
	        <a href="${pageContext.servletContext.contextPath}/shot">&nbsp&nbsp >Shot</a>
               </div>
       </div>
        

        <input type="hidden" id="selected-score-box">

        <div class="container">
            <div id="game-info">
                <!-- no info, so it shows blank -->
                <div class="game-box" id="gameNumber">
                    <span>Game:   <%= session.getAttribute("gameNumber") %></span>
                </div>
                <div class="frame-box" id="frameNumber">
                    <span>Frame: <%= session.getAttribute("frameNumber") %></span>
                </div>
                <!-- <div class="score-box">
                    <span>Total Score: ${sessionScope.totalScore}</span>
                </div> -->
            </div>
        </div>
	
		<form id="ballForm" method="post">
            <div class="container">
        
                <!-- drop down menu - selecting a ball -->
                <div class="dropdown">
                    <select name="ballArsenalDropdown" id="ballArsenalDropdown">
                        <option value="">Select a ball...</option>
                        <%
                            if (balls != null && !balls.isEmpty()) {
                                int i = 0;
                                for (Ball ball : balls) { %>
                                    <option value=<%= ball.getBallId() %> > <%= ball.getName() %></option>
                                <% System.out.println(ball.getName());
                                }
                            }else{%>
                                <p>There are no balls</p>
                            <%}
                        %>
                         <!-- <option value="add">Add Ball... </option>-->
                    </select>
                </div>
            
                <input type="hidden" id="ballIdInput" name="ballId" value="">
                <input type="hidden" id="shotNumberInput" name="shotNumber" value="">
            </div>
        </form>

        <div class="container">
            <div class="triangle">
                <div class="row">
                    <div class="pin" onclick="togglePin(this)"><input type="hidden" name ="pin7" id="7" value="up"><span>7</span></div>
                    <div class="pin" onclick="togglePin(this)"><input type="hidden" name ="pin8" id="8" value="up"><span>8</span></div>
                    <div class="pin" onclick="togglePin(this)"><input type="hidden" name ="pin9" id="9" value="up"><span>9</span></div>
                    <div class="pin" onclick="togglePin(this)"><input type="hidden" name ="pin0" id="0" value="up"><span>10</span></div>
                </div>
                <div class="row">
                    <div class="pin" onclick="togglePin(this)"><input type="hidden" name ="pin4" id="4" value="up"><span>4</span></div>
                    <div class="pin" onclick="togglePin(this)"><input type="hidden" name ="pin5" id="5" value="up"><span>5</span></div>
                    <div class="pin" onclick="togglePin(this)"><input type="hidden" name ="pin6" id="6" value="up"><span>6</span></div>
                </div>
                <div class="row">
                    <div class="pin" onclick="togglePin(this)"><input type="hidden" name ="pin2" id="2" value="up"><span>2</span></div>
                    <div class="pin" onclick="togglePin(this)"><input type="hidden" name ="pin3" id="3" value="up"><span>3</span></div>
                </div>
                <div class="row">
                    <div class="pin" onclick="togglePin(this)"><input type="hidden" name ="pin1" id="1" value="up"><span>1</span></div>
                </div>
            </div>
            <div class="row">
                <div id="shot-count"></div>
                    <div class="score-box-form" data-shot="1">
                        <div class="firstShot" id="score-box1" style="background-color: lightslategray;">
                            <input type="hidden" id="shotNumber1" name="shotNumber" value="1">
                        </div>
                    </div>
                    <div class="score-box-form" data-shot="2">
                        <div class="secondShot" id="score-box2" style="background-color: lightslategray;">
                            <input type="hidden" id="shotNumber2" name="shotNumber" value="2">
                        </div>
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

        <script>
            function togglePin(pin){
                // var pinID = pin.id;
                var hiddenVal = pin.querySelector('input[type="hidden"]');
                // var pinValue = pin.getAttribute('value');
                var pinValue = hiddenVal.value;


                if(pinValue === 'up'){
                    hiddenVal.setAttribute('value', 'down');
                    console.log(hiddenVal, " pin changed to down");
                }else if(pinValue === 'down'){
                    hiddenVal.setAttribute('value', 'up');
                    console.log(hiddenVal, " pin changed to up");
                }


                console.log("togglePin function called");
                // const isLeave = pin.classList.toggle('selected');
                const selectedScoreBox = getSelectedScoreBoxId();
                // console.log("isLeave: ", isLeave);
                // console.log("Second Shot initialize: ", secondShotScore);


                if(!selectedScoreBox){
                    console.log("No selected shot box");
                    return;
                }


                if(selectedScoreBox === 'score-box1'){
                    document.getElementsByName("shotNumber")[0].value = 1;
                }else if(selectedScoreBox === 'score-box2'){
                    document.getElementsByName("shotNumber")[0].value = 2;
                }


                const isLeave = pin.classList.toggle('selected');
                console.log("isLeave: ", isLeave);


                if(isLeave){
                    pin.style.backgroundColor = 'white'
                    pin.style.color = 'black';
                }else{
                    pin.style.backgroundColor = 'black'
                    pin.style.color = 'white';
                }


                // const pinsLeftStanding = calculateTotalPinsLeftStanding();
                // updateSelectedScoreBox(selectedScoreBox, null, calculateTotalPinsLeftStanding());
                // highlightSelectedScoreBox(selectedScoreBox);


                // if(selectedScoreBox === "score-box2" && isLeave){
                //     //const pinsLeftAfterFirstShot = calculatePinsLeftStandingAfterFirstShot(firstShotCount);
                //     secondShotScore++;
                //     updateSecondShotDisplay();
                // }


                // updateSelectedPins();
                console.log("Current value of the pin: ", hiddenVal.getAttribute('value', 'up'));
            }

            //selects first and second shot
            document.addEventListener("DOMContentLoaded", function(){
                var scoreBoxes = document.querySelectorAll(".firstShot, .secondShot");

                scoreBoxes.forEach(function(scoreBox){
                    scoreBox.addEventListener("click", function(){
                        scoreBoxes.forEach(function(box){
                            box.classList.remove("selected");
                        });

                        scoreBox.classList.add("selected");

                        var shotNumberInput = document.querySelector('input[name="shotNumber"]');

                        if(shotNumberInput){
                            shotNumberInput.value = scoreBox.dataset.shot;
                        }else{
                            console.error("Could not find 'shotNumber' input element");
                        }
                    });
                });
            });

            //allows the user to click the first and second score box
            document.querySelectorAll('.score-box-form').forEach(function(scoreBoxForm){
                scoreBoxForm.addEventListener('click', function(){
                    var shotNumber = this.dataset.shot;
                    sendShotNumberToServlet(shotNumber);
                });
            });

            //allows the user to change their selected ball
            document.addEventListener("DOMContentLoaded", function(){
                var ballDropdown = document.querySelector('#ballArsenalDropdown');
                ballDropdown.addEventListener('change', function(){
                    var selectedBallId = this.value;
                    if(selectedBallId){
                        console.log("Selected Ball ID: ", selectedBallId);
                        // sendBallIdToServlet(selectedBallId);
                        document.querySelector('#ballForm input[name="selectedBallId"]');
                        document.getElementById("ballForm").submit();
                    }
                });
            });

            //submits the ball information without refreshing the page
            document.addEventListener("DOMContentLoaded", function(){
                var ballForm = document.getElementById("ballForm");
                ballForm.addEventListener("submit", function(event){
                    event.preventDefault();

                    var selectedBallId = document.querySelector('#ballArsenalDropdown');
                    var shotNumber = getSelectedScoreBoxId();

                    if(selectedBallId && shotNumber){
                        document.querySelector('#ballIdInput').value = selectedBallId;
                        document.querySelector('input[name="shotNumber"]').value = shotNumber;

                        ballForm.submit();
                    }else{
                        console.log("Selected ball ID or shot number is missing");
                    }
                })
            });

            //submits the next frame form so it resets the page and changes the frame number
            function submitForm(event) {
                event.preventDefault(); // Prevent the default form submission


                var pinElements = document.querySelectorAll('.pin'); // Select all elements with class 'pin'
                var pinValues = {}; // Object to store pin values


                // Iterate over each pin element and get its value
                pinElements.forEach(function(pinElement) {
                    var pinId = pinElement.id;
                    var pinValue = pinElement.getAttribute('value');
                    pinValues[pinId] = pinValue;
                });


                // Print pin values to the console
                console.log("Pin Values:");
                console.log(pinValues);

                // You can now proceed with form submission if needed
                // event.target.submit();
                // document.getElementById("shotForm").submit();
            }

            function getSelectedScoreBoxId(){
                const selectedScoreBox = document.querySelector('.firstShot.selected, .secondShot.selected');
                console.log("Selected Score Box: ", selectedScoreBox);
                if(selectedScoreBox){
                    console.log("ID: ", selectedScoreBox.id);
                    return selectedScoreBox.id;
                }
            }

            function sendShotNumberToServlet(scoreBoxId) {
                var xhr = new XMLHttpRequest();
                var url = "${pageContext.servletContext.contextPath}/shot";
                var params = "scoreBoxId=" +scoreBoxId;

                xhr.open("POST", url, true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 200) {
                            console.log("Shot number sent successfully.");
                        } else {
                            console.error("Failed to send shot number. Status:", xhr.status);
                        }
                    }
                };
                // xhr.send("shotNumber=" + encodeURIComponent(shotNumber));
                xhr.send(params);
            }

            // function sendBallIdToServlet(ballId){

            //     var xhr = new XMLHttpRequest();
            //     var url = "${pageContext.servletContext.contextPath}/shot";

            //     xhr.open("POST", url, true);
            //     xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            //     xhr.onreadystatechange = function(){
            //         if(xhr.readyState === XMLHttpRequest.DONE){
            //             if(xhr.status === 200){
            //                 console.lof("Ball ID sent successfully");
            //             }else{
            //                 console.error("Failed to send ball ID. Status: ", xhr.status);
            //             }
            //         }
            //     };
            //     xhr.send();
            // }
        </script>
    </body>
</html>