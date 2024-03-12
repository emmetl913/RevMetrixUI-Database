<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html>
	<head>
		<title>Home</title>
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

		<form action="${pageContext.servletContext.contextPath}/index" method="post">
			<input type="submit" name="action" value="AddNumbers">
			<input type="submit" name="action" value="MultiplyNumbers">
			<input type="submit" name="action" value="GuessingGame">
		</form>
	</body>
</html>
