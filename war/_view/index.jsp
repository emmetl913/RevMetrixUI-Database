<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html>
	<head>
		<title>Index page:lab02a_eculp</title>
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

		<form action="${pageContext.servletContext.contextPath}/startBowling" method="get">
    		<input type="submit" name="startBowling" size="12" value="Start Bowling!" />


		<form action="${pageContext.servletContext.contextPath}/ballArsenal" method="post">
			<input type="submit" name="action" value="BallArsenal">

		</form>
	</body>
</html>
