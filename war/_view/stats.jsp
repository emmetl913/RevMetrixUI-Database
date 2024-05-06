<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="edu.ycp.cs320.RevMetrix.model.stat" %>
<%@ page import="edu.ycp.cs320.RevMetrix.model.Event" %>
<%@ page import="edu.ycp.cs320.RevMetrix.model.Session" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.io.*,java.util.*" %>

<%
	ArrayList<Event> events = (ArrayList<Event>) request.getAttribute("events");
%>

<html>
	<head>
		<style>
			body {
				font-family: Arial, sans-serif;
				background-color: darkgray;
				margin: 0;
				padding: 0;
			}

			button {
				background-color: #4caf50;
				color: #fff;
				padding: 10px 20px;
				border: none;
				border-radius: 4px;
				cursor: pointer;
			}

				button:hover {
					background-color: #45a049;
				}

			.container {
				max-width: 600px;
				margin: 50px auto;
				background-color: #fff;
				padding: 20px;
				border-radius: 8px;
				box-shadow: 10px 10px 5px black;
			}

				label {
					display: block;
					margin-bottom: 8px;
				}

				input {
					width: 100%;
					padding: 8px;
					margin-bottom: 16px;
					box-sizing: border-box;
					border: 1px solid #ccc;
					border-radius: 4px;
				}

				input[type=submit] {
					background-color: #4caf50;
					color: #fff;
					padding: 10px 20px;
					border: none;
					border-radius: 4px;
					cursor: pointer;
				}


			input[type=submit]:hover {
				background-color: #45a049;
			}

			.error {
				color: red;
			}

			/* Style for the black sidebar */
			.sidebar {
				height: 100%;
				width: 250px;
				position: fixed;
				top: 0;
				left: 0;
				background: linear-gradient(to bottom, rgba(243, 0, 178, 1), rgba(28, 144, 243, 1) 95%, rgba(255, 255, 0, 1));
				padding-top: 20px;
				box-shadow: 10px 5px 5px rgba(0, 0, 0, 0.1);
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
				margin-left: 250px;
				/* Same width as the sidebar */
				padding: 20px;
			}

			/* Responsive layout - when the screen is less than 600px wide, make the sidebar and the main content stack on top of each other */
			@media screen and (max-width: 600px) {
				.sidebar {
					width: 100%;
					height: auto;
					position: relative;
				}

				.sidebar a {
					float: left;
				}

				div.content {
					margin-left: 0;
				}
			}

			.bottom-link {
				position: absolute;
				bottom: 20px;
				/* Adjust this value to raise or lower the link */
				left: 0;
				width: 84%;
				text-align: left;
			}

			.bottom-link2 {
				position: absolute;
				bottom: 60px;
				/* Adjust this value to raise or lower the link */
				left: 0;
				width: 84%;
				text-align: left;
			}

			table {
				border-collapse: collapse;
				width: 100%;
			}

			th,
			td {
				border: 1px solid #ddd;
				padding: 8px;
				text-align: center;
			}

			th {
				background-color: #f2f2f2;
			}

			.total-score {
				font-weight: bold;
				background-color: #ffe6cc;
			}

			.container {
				max-width: 650px;
				margin: 50px auto;
				background-color: #fff;
				padding: 20px;
				border-radius: 8px;
				box-shadow: 10px 10px 5px black;
			}

			#hiddenContent{
				max-width: 650px;
			}

			table{
				width: 100%;
			}

			.hidden {
				display: none;
			}

			h2{
				font-size: 20px;
				text-decoration: underline;
			}
		</style>
	</head>

	<body>

		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>
		<input type="hidden" name="step" id="step" value="1">
		<!-- Side bar, duh -->
		<div class="sidebar">
			<a href="${pageContext.servletContext.contextPath}/index">
				<img src="${pageContext.request.contextPath}/_view/BowlingBall.png" width="100" height="100">
			</a>
			<a href="${pageContext.servletContext.contextPath}/establishmentReg">Establishment
				Registration</a>
			<a href="${pageContext.servletContext.contextPath}/ballArsenal">Ball Arsenal</a>
			<a href="${pageContext.servletContext.contextPath}/stats">Stats</a>
			<a href="${pageContext.servletContext.contextPath}/event">Start Bowling</a>

			<a href="https://github.com/emmetl913/RevMetrixUI-Database" class="bottom-link2">GitHub</a>
			<a href="${pageContext.servletContext.contextPath}/logIn" class="bottom-link">Sign Out</a>
		</div>
		</div>
		<form id="statsForm" action="${pageContext.servletContext.contextPath}/stats" method="post">
			<div class="container">
				<h1>Stats Page!</h1>
				<h2>League Stats:</h2>
				<select name="selectedEvent" id="selectedEvent">
					<% 	if (events != null) {
							for (Event event : events) {
							%>
							<option value="<%= event.getEventName()%>">
								<%= event.getEventName()%>
							</option>
							<% } } else { %>
								<option>No Events</option>
								<% } %>
				</select>
				<button name="submitButtonLeagueChart" type="submit" id="submitButtonLeagueChart">Get League Chart</button>

				<h2>Shot Stats:</h2>
				<form action="${pageContext.servletContext.contextPath}/stats" method="post">
					<select name="eventSelected" id="eventSelected">
						<%if (events != null) {
								for (Event event : events) {
							%>
							<option value="<%= event.getEventID()%>">
								<%= event.getEventName()%>
							</option>
							<% } } else { %>
								<option>No Events</option>
						<% } %>
					</select>
					
					<!-- submits the event ID -->
					<input type="hidden" name="selectedEventID" id="selectedEventID" value="">
					<button name="submitEvent" type="submit" id="submitEvent">Submit Event</button>
	
				</form>
				</br>

				<form action="${pageContext.servletContext.contextPath}/stats" method="post">
					<select name="sessionID" id="sessionID">
						<option value="">Select Sessions</option>
						<c:forEach var="session" items="${eventSessions}">
							<option value="${session.sessionID}">${session.name}</option>
						</c:forEach>
					</select>
	
					<input type="hidden" name="selectedSessionID" id="selectedSessionID" value="">
					<button name="submitSession" type="submit" id="submitSession">Submit Session</button>
	
				</form>
				
				<!-- 
				<script>
					function updateSelectedSession(){
						var selectedSession = document.getElementById("sessionSelected").value;
						document.getElementById("selectedSessionID").value = selectedSession;
					}
				</script> -->
				</br>

				<form action="${pageContext.servletContext.contextPath}/stats" method="post">
					<select name="gameID" id="gameID">
						<option value="">Select Game</option>
						<c:forEach var="game" items="${sessionGames}">
							<option value="${game.gameID}">${game.gameName}</option>
						</c:forEach>
					</select>
					<button name="submitShotData" type="submit" id="submitShotData">Get Shot Data</button>	
				</form>

			</div>
		</form>
		<c:if test="${requestScope.formSubmitted}">
			<div class="container" id="hiddenContent">
				<table>
					<thead>
						<tr>
							<th>DATE</th>
							<th>LGE (League)</th>
							<th>LANES</th>
							<th>1</th>
							<th>2</th>
							<th>3</th>
							<th>SERIES</th>
							<th>TOTAL GAMES</th>
							<th>AVERAGE</th>
						</tr>
					</thead>
					<tbody>
						<% ArrayList<stat> sessionTable = (ArrayList<stat>
							)request.getAttribute("sessionTable");
							for (stat s : sessionTable) { %>
							<tr>
								<td>
									<%= s.getDate() %>
								</td>
								<td>
									<%= s.getLeague() %>
								</td>
								<td>
									<%= s.getLanes() %>
								</td>
								<td>
									<%= s.getGames1() %>
								</td>
								<td>
									<%= s.getGames2() %>
								</td>
								<td>
									<%= s.getGames3() %>
								</td>
								<td>
									<%= s.getSeries() %>
								</td>
								<td>
									<%= s.getTotalGame() %>
								</td>
								<td>
									<%= s.getAvg() %>
								</td>
							</tr>
						<% } %>
					</tbody>
				</table>
			</div>
		</c:if>
		
		<!-- Add my stuff down here... -->

		<c:if test="${requestScope.shotFormSubmitted}">
			<div class="container" id="hiddenContent">
				<h3>Stats Data:</h3>
				<h4>Lifetime pins knocked down: </h4>
				<h4>Pin Strike: </h4>
				<h4>Strike Percentage: <%= request.getAttribute("strike") %> %</h4>
				<h4>Spare Percentage: <%= request.getAttribute("spare") %> %</h4>
				<h4>Leave Percentage: <%= request.getAttribute("leave") %> %</h4>
			</div>
		</c:if>
	</body>
</html>