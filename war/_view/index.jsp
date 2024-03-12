<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html>
	<head>
		<title>Index page:lab02a_zcox</title>
		<style type="text/css">
		.error {
			color: red;
		}
		
		td.label {
			text-align: right;
		}
		</style>
	</head>

	<body>
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>

		<table>
				<form action="${pageContext.servletContext.contextPath}/addNumbers" method="get">
					<tr><input type="Submit" name="submit" value="Add Numbers!">
					</tr>
					&nbsp
				</form>
				<form action="${pageContext.servletContext.contextPath}/multiplyNumbers" method="get">
					<tr><input type="Submit" name="submit" value="Multiply Numbers!">
					</tr>
					&nbsp
				</form>
				<form action="${pageContext.servletContext.contextPath}/guessingGame" method="get">
					<tr><input type="Submit" name="submit" value="Play Guessing Game!">
					</tr>
					&nbsp
				</form>
				<form action="${pageContext.servletContext.contextPath}/logIn" method="get">
					<tr><input type="submit" name="action" value="Log In Page!">
					</tr>
				</form>
			</table>


			
	</body>
</html>
