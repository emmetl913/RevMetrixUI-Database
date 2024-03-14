<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Bowling Ball Arsenal</title>
		<style type="text/css">
			body{
				font-family: Arial, Helvetica, sans-serif;
			}

			header{
				text-align: center;
				display: flex;
				justify-content: center;
				position: relative;
				pad: 10px;
			}
			h1{
				font-size: 50px;
				color: black;
				text-align: center;
			}

			#ball-list{
				margin-top: 20px;
			}

			#add-ball-form, #remove-ball-form{
				margin-bottom: 10px;
			}

			#add-ball-form input, #remove-ball-form input{
				margin-right: 10px;
			}

			.ball-box{
				width: 500px;

				align-items: center;
				text-align: center;
				position: absolute;

				top: 25%;
				left: 50%;
				transform: translate(-50%, -50%);

				padding: 10px;
				border: 1px solid #ccc;
				border-radius: 5px;
				margin-bottom: 10px;
				background-color: white;
				box-shadow: 2px;
			}

			.bowling-ball-img{
				width: 150px;	/*Adjust the size of the image*/
				height: auto;
				margin-bottom: 10px;
			}
		</style>
	</head>

	<body>
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>
	
		<form action="${pageContext.servletContext.contextPath}/ballArsenal" method="post">
			<header><h1>Bowling Ball Arsenal</h1></header>	

			<div class="ball-box">
				<div id="add-ball-form">
					<input type="text" id="ball-name" placeholder="Ball Name">
					<input type="text" id="ball-color" placeholder="Ball Color">
					<button onclick="addBall()">Register Ball</button>
				</div>
				<div id="remove-ball-form">
					<input type="text" id="remove-ball-name" placeholder="Ball Name to Remove">
					<button onclick="removeBall()">Remove Ball</button>
				</div>
			</div>

			<img class="bowling-ball-img" id="bowling-ball" src="" alt="Bowling Ball">

			<script>
				//Function to add the ball
				function addBallToArsenal(){
					var name = document.getElementById("ball-name").value;
					var color = document.getElementById("ball-color").value;

					//call Java method to add ball
					addBall(name, color);

					//update the ball list
					updateBallList();
				}

				//FUnction to Remove a ball
				function removeBalFromArsenal(){
					var name = document.getElementById("remove-ball-name").value;

					//call java method to remove ball
					removeBall(name);

					//update the ball list
					updateBallList();
				}

				//Function to update ball list
				function updateBallList(){
					var name = document.getElementById("balls");
					ballsList.innerHTML = "";

					//call Java method to get balls
					var balls = getBalls();

					balls.forEach(function(ball){
						var listItem = document.createElement("li");
						listItem.textContent = ball.name = " - " + ball.color;
						ballsList.appendChild(listItem);
					});
				}

				function changeBallImage(){
					var color = document.getElementById("ball-color").value;
					var img = document.getElementById("ball-img");

					//update image soource based on color input
					img.src = "bowlingBall-" + color.toLowerCase() + ".jpg";

					//show the bowling ball image below the register ball box
					var bowlingBallImg = document.getElementById("bowling-ball");
					bowlingBallImg.src = img.src;
				}
			</script>
		</form>
	</body>
</html>