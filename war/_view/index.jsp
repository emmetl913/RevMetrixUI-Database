<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import= "edu.ycp.cs320.RevMetrix.model.Account" %>
<%@ page import= "edu.ycp.cs320.RevMetrix.model.Event" %>
<%@ page import = "java.io.*,java.util.*"%>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
	Account acc = (Account)session.getAttribute("currAccount");
  ArrayList<Event> events = (ArrayList<Event>)request.getAttribute("event");
%>
<html>
	<head>
		<title>Index page</title>
		<style type="text/css">
		body {
            font-family: Arial, sans-serif;
            background-color: darkgray;
            margin: 0;
            padding: 0;
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
		
		td.label {
			text-align: right;
		}
		form {
            display: inline-block;
            margin-right: 10px; /* Add some margin between forms */
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

  .event-section {
        border: 1px solid black; /* Add border around each ball section */
        margin-bottom: 10px; /* Add some space between ball sections */
        padding: 10px; /* Add padding inside each ball section */
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
          <a href="${pageContext.servletContext.contextPath}/event">Start Bowling</a>

          <a href="https://github.com/emmetl913/RevMetrixUI-Database"class="bottom-link2">GitHub</a>
          <a href="${pageContext.servletContext.contextPath}/logIn"class="bottom-link">Sign Out</a>
		  </div>
		  
		  <div class="container">
			<h2>Account Page</h2>
			<form action="${pageContext.servletContext.contextPath}/index" method="post">

				<label>Username: <%= acc.getUsername()%> </label>
	
				<label>Email: <%= acc.getEmail()%> </label>
				
				<label>First Name: <%= acc.getFirstName()%> </label>
				
				<label>Last Name: <%= acc.getLastName()%> </label>
	
				<label>Events up soon:</label>
				<div id="eventList"> &nbsp				
          <% 
                 
              if (events != null) {
                for (Event event : events) {
         %>
               <div class="event-section">
                <p>Name: <%= event.getEventName() %></p>
                 <p>Establishment Name: <%= event.getTime()%></p>
             </div>
         <% 
               } } else {	%>
               <p>No events available.</p>
         <% } %>
         
         </div>
		</div>
	</body>
</html>
