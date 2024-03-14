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
    		<input type="submit" name="addNumbers" size="12" value="AddNumbers" />
		</form>
		<form action="${pageContext.servletContext.contextPath}/multiplyNumbers" method="get">
    		<input type="submit" name="multiplyNumbers" size="12" value="MultiplyNumbers" />
		</form> 
		<form action="${pageContext.servletContext.contextPath}/guessingGame" method="get">
    		<input type="submit" name="guessingGame" size="12" value="GuessingGame" />
		</form>
		<form action="${pageContext.servletContext.contextPath}/startBowling" method="get">
    		<input type="submit" name="startBowling" size="12" value="StartBowling!" />
		</form>
		<form action="${pageContext.servletContext.contextPath}/establishmentReg" method="get">
    		<input type="submit" name="establishmentReg" size="12" value="EstablishmentReg" />
		</form>
		<form action="${pageContext.servletContext.contextPath}/logIn" method="get">
					<input type="submit" name="action" value="Log In Page!">
		</form>
		<form action="${pageContext.servletContext.contextPath}/ballArsenal" method="get">
    		<input type="submit" name="BallArsenal" size="12" value="BallArsenal" />
		</form>
		<form action="${pageContext.servletContext.contextPath}/shot" method="get">
    		<input type="submit" name="Shot" size="12" value="Shot" />
		</form>
	</body>
</html>
