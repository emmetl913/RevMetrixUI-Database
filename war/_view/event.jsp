<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import= "edu.ycp.cs320.lab02.model.Event" %>
<%@ page import="edu.ycp.cs320.lab02.model.EventArray" %>

<%@ page import= "edu.ycp.cs320.lab02.model.Establishment" %>
<%@ page import="edu.ycp.cs320.lab02.model.EstablishmentArray" %>

<%@ page import = "java.io.*,java.util.*"%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Establishment Registration</title>
    <style>

        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .ball-section {
        border: 1px solid black; /* Add border around each ball section */
        margin-bottom: 10px; /* Add some space between ball sections */
        padding: 10px; /* Add padding inside each ball section */
    }

        .container {
            max-width: 600px;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
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
	      <a href="${pageContext.servletContext.contextPath}/establishmentReg">Establishment Registration</a>
		    <a href="${pageContext.servletContext.contextPath}/logIn">Sign Out</a>
          <a href="${pageContext.servletContext.contextPath}/shot">Shot</a>
          <a href="${pageContext.servletContext.contextPath}/ballArsenal">Ball Arsenal</a>
          <a href="https://github.com/emmetl913/RevMetrixUI-Database">GitHub</a>
          <a href="${pageContext.servletContext.contextPath}/startBowling">Start Bowling</a>
          <a class="dropbtn" href="#" onclick="toggleDropdown(), nextStep(1)">Start Bowling!</a>
		      <div class="dropdown-content" id="myDropdown">
	        <a href="${pageContext.servletContext.contextPath}/event">Event</a>
	        <a href="#" onclick="showContent('session')">Session</a>
	        <a href="#" onclick="showContent('game')">Game</a>
	        <a href="#" onclick="showContent('shot')">Shot</a>
	   	 </div>
		  </div>
      
    <div class="container">
      <h2>Event Page</h2>
        <form action="${pageContext.servletContext.contextPath}/event" method="post">

          <label for="eventName">Event Name:</label>
          <input type="text" name="eventName" size="12" value="${game.eventName}">
    
          <label for="eventType">Event Type:</label>
                <button text="Practice" name="practice" type="submit" value="Practice">Practice</button>
          <button text="Tournament" name="tournament" type="submit" value="Tournament">Tournament</button>
          <button text="Leauge" name="leauge" type="submit" value="Leauge">Leauge</button>
    
          <label for="establishment">Establishment Name/Location:</label>
          <select name="establishment" id="establishment">
          <%
          ArrayList<Event> events = (ArrayList<Event>) request.getAttribute("event");
            if (events != null) {
              for (Event event : events) {
           %>
          <option value="${game.establishment}"><%= event.getEstablishment().getEstablishmentName()%></option>
           <% 
             } } else {	%>
               <option value="${game.establishment}">No Establishments</option>
           <% } %>
          </select>
    
                <label for="standing">Standing:</label>
                <input type="text" name="standing" size="12" value="${game.standing}">
    
                <label for="stats">Stats?:</label>
                <input type="text" name="stats" size="12" value="${game.stats}">
    
          <tr>
            <td><input type="Submit" id="sessionType" name="Submit" value="Submit"></td>
          </tr>
    </div>

    <script>
      var currentStep = 1;
      // Display current step
      function showStep(step)
      {
        var contentDivs = document.querySelectorAll('.content div');
        contentDivs.forEach(function(div)
            {
              div.classList.remove('active')
            });
        var selectedDiv = document.getElementById('step'+ step);
        selectedDiv.classList.add('active');
        
        currentStep = step;
      }
      
      // Step Incrementer 
      function nextStep(step)
      {
        if (step === currentStep)
        {
          showStep(step+1);
        }
      }
      // Toggle Method for Dropdown button
        function toggleDropdown() 
        {
            var dropdownContent = document.getElementById("myDropdown");
            if (dropdownContent.style.display === "block") {
                dropdownContent.style.display = "none";
            } else {
                dropdownContent.style.display = "block";
            }
        }
        
      </script>
</body>
</html>
