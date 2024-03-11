<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html>
	<head>
		<title>Index page</title>
		<style type="text/css">
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
		</style>
	</head>

	<body>
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>

		<form action="${pageContext.servletContext.contextPath}/addNumbers" method="get">
    		<input type="submit" name="addNumbers" size="12" value="Add Numbers" />
		</form>
		<form action="${pageContext.servletContext.contextPath}/multiplyNumbers" method="get">
    		<input type="submit" name="multiplyNumbers" size="12" value="Multiply Numbers" />
		</form> 
		<form action="${pageContext.servletContext.contextPath}/guessingGame" method="get">
    		<input type="submit" name="guessingGame" size="12" value="Guessing Game" />
		</form>
		<form action="${pageContext.servletContext.contextPath}/startBowling" method="get">
    		<input type="submit" name="startBowling" size="12" value="Start Bowling!" />
		</form>
	</body>
</html>
