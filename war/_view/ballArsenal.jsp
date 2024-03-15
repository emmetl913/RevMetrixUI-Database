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
	
		<form action="${pageContext.servletContext.contextPath}/ballArsenal" method="post">
			<header><h1>Bowling Ball Arsenal</h1></header>	

			<div class="ball-box">
				<div id="add-ball-form">
					<input type="text" id="ball-name" placeholder="Ball Name">
					<button onclick="addBall()">Register Ball</button>
				</div>
				<div id="remove-ball-form">
					<input type="text" id="remove-ball-name" placeholder="Ball Name to Remove">
					<button onclick="removeBall()">Remove Ball</button>
				</div>
			</div>

			<script>
				//Function to add the ball
				function addBallToArsenal(){
					var name = document.getElementById("ball-name").value;

					//call Java method to add ball
					addBall(name);

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
			</script>
		</form>
	</body>
</html>