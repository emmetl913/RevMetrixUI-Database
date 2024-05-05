<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import= "edu.ycp.cs320.RevMetrix.model.Event" %>
<%@ page import= "edu.ycp.cs320.RevMetrix.model.Establishment" %>
<%@ page import = "java.io.*,java.util.*"%>

<%
  ArrayList<Event> events = (ArrayList<Event>)request.getAttribute("event");
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Establishment Registration</title>
    <style>

        body {
            font-family: Arial, sans-serif;
            background-color: darkgray;
            margin: 0;
            padding: 0;
        }
		.eventsList
		{
			width: 590px;
    		height: 400px;
			overflow: scroll;
		}
        .event-section 
        {
	        border: 1px solid black; /* Add border around each ball section */
	        margin-bottom: 10px; /* Add some space between ball sections */
	        padding: 10px; /* Add padding inside each ball section */
    		
    	}
		.event-section:hover {
       		background-color: #33B5FF;
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
  background: linear-gradient(to bottom, rgba( 243, 0, 178, 1 ), rgba( 28, 144, 243, 1 ) 95%, rgba( 255, 255, 0, 1 ));
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
          <a href="${pageContext.servletContext.contextPath}/stats">Stats</a>
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

      <h1>Event Page</h1>
      
    <div class="container">
      <h2>Pick Existing Event</h2>
        <form action="${pageContext.servletContext.contextPath}/event" method="post">
          <div id="eventsList" class="eventsList"> &nbsp				
            <% 
                   
                if (events != null) {
                  for (Event event : events) {
           %>
                 <div class="event-section" onclick= "selectEvent ('<%= event.getEventID() %>')">
                  <p>Name: <%= event.getEventName() %></p>
                   <p>Establishment Name: <%= event.getEstbID()%></p>
               </div>
           <% 
                 } } else {	%>
                 <p>No events available.</p>
           <% } %>
           
           </div>
           <input type="hidden" id="selectedEvent" name="selectedEvent">
          <tr>
            <td><a href="${pageContext.servletContext.contextPath}/session" method="post"><input type="Submit" id="SubmitCurrentEvent" name="SubmitCurrentEvent" value="Submit"></a></td>
          </tr>
         </form>
	 </div>

   <div class="container">
    <h2>Create Event</h2>
    <form action="${pageContext.servletContext.contextPath}/event" method="post">

      <label for="eventName">Event Name:</label>
      <input type="text" name="eventName" size="12" value="">

      <label for="eventdate">Event Date:</label>
      <!-- <input type="text" name="eventdate" size="12" value=""> -->
  	  <input type="date" id="inputTime" name="eventdate" value="">

      <input type="hidden" id="type" name="newType" value="">

      <label for="eventType">Event Type:</label>
      <button text="Practice" name="practice" type="button" value="Practice" onclick="setToPractive()">Practice</button>
      <button text="Tournament" name="tournament" type="button" value="Tournament"onclick="setToTournament()">Tournament</button>
      <button text="Leauge" name="leauge" type="button" value="Leauge"onclick="setToLeauge()">League</button>

      <label for="establishment">Establishment Name/Location:</label>
      <select name="establishment" id="establishment">
      <%
      ArrayList<Establishment> estabs = (ArrayList<Establishment>) request.getAttribute("esta");
        if (estabs != null) {
          for (Establishment establishment : estabs) {
       %>
      <option value="<%= establishment.getEstablishmentName()%>"><%= establishment.getEstablishmentName()%></option>
       <% 
         } } else {	%>
           <option>No Establishments</option>
       <% } %>
      </select>

            <label for="standing">Standing:</label>
            <input type="text" name="standing" size="12" value="${game.standing}">

      <tr>
        <td><a href="${pageContext.servletContext.contextPath}/session"><input type="Submit" id="sessionType" name="Submit" value="Submit"></a></td>
      </tr>
     </form>
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

        function setToPractive() {
          document.getElementById("type").value = "Practice";
        }
        function setToTournament() {
          document.getElementById("type").value = "Tournament";
        }
        function setToLeauge() {
          document.getElementById("type").value = "Leauge";
        }
        
        function selectEvent(event) {
    		document.getElementById('selectedEvent').value = event;
		}
      </script>
</body>
</html>