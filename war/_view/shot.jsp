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
                padding-right: 25px;
            }

            .frame-box{
                border: 1px solid black;
                padding: 5px;
                padding-left: 25px;
                padding-right: 25px;
            }

            .dropdown{
                margin-top: 10px;
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

            .circle.selected {
                background-color: gray;
            }

            .circle.selected{
                background-color: black;
                color: white;
            }

            .firstShot{
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

            .secondShot{
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

            .shot-buttons{
                margin-top: 20px;
            }

            .shot-button{
                margin: 5px;
                margin-right: 10px;
                padding: 10px 20px;
                font-size: 16px;
                cursor: pointer;
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

            Object pinsKnockedDown = request.getAttribute("pinsKnockedDown");
            int pinsKnockedOver = 0;
            if(pinsKnockedDown != null){
                pinsKnockedOver = (Integer) pinsKnockedDown;
            }
        %>
	
		<form action="${pageContext.servletContext.contextPath}/shot" method="post">
            <div class="container">
                <div id="game-info">
                    <!-- no info, so it shows blank -->
                    <div class="game-box">
                        <span>Game:   ${session.gameNumber}</span>
                    </div>
                    <div class="frame-box">
                        <span>Frame:   ${session.frameNumber}</span>
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
                    <div class="circle" data-number="7" name="pin-7"><span class="circle-inner">7</span></div>
                    <div class="circle" data-number="8" name="pin-8"><span class="circle-inner">8</span></div>
                    <div class="circle" data-number="9" name="pin-9"><span class="circle-inner">9</span></div>
                    <div class="circle" data-number="10" name="pin-10"><span class="circle-inner">10</span></div>
                </div>
                <div class="row">
                    <div class="circle" data-number="4" name="pin4"><span class="circle-inner">4</span></div>
                    <div class="circle" data-number="5" name="pin5"><span class="circle-inner">5</span></div>
                    <div class="circle" data-number="6" name="pin6"><span class="circle-inner">6</span></div>
                </div>
                <div class="row">
                    <div class="circle" data-number="2" name="pin2"><span class="circle-inner">2</span></div>
                    <div class="circle" data-number="3" name="pin3"><span class="circle-inner">3</span></div>
                </div>
                <div class="row">
                    <div class="circle" data-number="1" name="pin1"><span class="circle-inner">1</span></div>
                </div>

                <div class="row">
                    <div class="firstShot" id="score-box1" onclick="setFirstShot()" style="background-color: orange;"></div>
                    <div class="secondShot" id="score-box2" onclick="setSecondShot()" style="background-color: lightslategray;"></div>
                </div>
    
                <div class="shot-buttons">
                    <button class="shot-button" onclick="setFoul()">F</button>
                    <button class="shot-button" onclick="setGutter()">-</button>
                    <button class="shot-button" onclick="setStrike()">X</button>
                    <button class="shot-button" onclick="setSpare()">/</button>
                </div>
    
                <div class="frame-buttons">
                    <button onclick="previousFrame()">Previous Frame</button>
                    <button onclick="nextFrame()">Next Frame</button>
                </div>
            </div>
        </form>

        <script>
            function handleSelectChange(event){
                if(event.target.value == "add"){
                    window.location.href = '${pageContext.servletContext.contextPath}/ballArsenal';
                }
            }

            const firstShot = 0;
            const secondShot = 0;

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

            document.getElementById("frameNumber").innerHTML = frameNumber;
            document.getElementById("gameNumber").innerHTML =  gameNumber;

            setFirstShot();

            function togglePin(pin){
                pin.classList.toggle('leave');

                const pins = documnet.querySelectorAll('.pin:not(.leave)');
                pinCount = pins.length;

                if(shot == firstShot){
                    firstShotCount = pinCount;

                    if(firstShotCount == allPins){
                        setStrike();
                    }else{
                        document.querySelector('.firstShot span').textContent = firstShotCount;

                        clearSecondShot();
                    }
                }else{
                    secondShotCount = pinCount-firstShotCount;

                    if(pinCount == allPins){
                        document.querySelector('.secondShot span').textContent = '/';
                    }else if(secondShotCount == noPins){
                        document.querySelector('.secondShot span').textContent = '-';
                    }else{
                        document.querySelector('.secondShot span').textContent = secondShotCount;
                    }
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
                if(shot == firstShot){
                    setAllPinsStanding();
                    clearSecondShot();
                    document.querySelector('.firstShot span').textContent = '-';
                }else{
                    document.querySelector('.secondShot span').textContent = '-';
                }
            }

            function setFoul(){
                if(shot == firstShot){
                    setAllPinsStanding();
                    clearSecondShot();
                    document.querySelector('.firstShot span').textContent = 'F';
                }else{
                    document.querySelector('.firstShot span').textContent = 'F';
                }
            }

            function setFirstShot(){
                shot = firstShot;
                document.querySelector('.firstShot').style.backgroundColor = 'orange';
                document.querySelector('.secondShot').style.backgroundColor = 'lightslategray';
            }

            function clearFirstShot(){
                document.querySelector('.firstShot span').textContent = ' ';
            }

            function clearSecondShot(){
                document.querySelector('.secondShot span').textContent = ' ';
            }

            function resetPinCounts(){
                pinCount = noPins;
                firstShotCount = noPins;
                secondShotCount = noPins;
            }

            function setSpare(){
                clearPins();
                setSecondShot();
                if(document.querySelector('.firstShot span').textContent !== '-'){
                    if(document.querySelector('.firstShot span').textContent !== 'F'){
                        document.querySelector('.firstShot span').textContent = firstShotCount; 
                    }
                }
                document.querySelector('.secondShot span').textContent = '/';
            }

            function setStrike(){
                clearPins();
                setFirstShot();
                resetPinCounts();
                clearFirstShot();
                document.querySelector('.secondShot span').textContent = 'X';
            }

            function getPreviousFrame(){
                clearPins();
                resetPinCounts();
                document.querySelector('.firstShot span').textContent = ' ';
                document.querySelector('.secondShot span').textContent = ' ';
                setFirstShot();

                if(frameNumber > minFrame){
                    frameNumber--;
                    document.getElementById('current-frame').value = frameNumber;
                }
            }

            function getNextFrame(){
                clearPins();
                resetPinCounts();
                document.querySelector('.firstShot span').textContent = ' ';
                document.querySelector('.secondShot span').textContent = ' ';
                setSecondShot();

                if(frameNumber < maxFrame){
                    frameNumber++;
                    document.getElementById('frameNumber').value = frameNumber;
                }
            }
        </script>
    </body>
</html>