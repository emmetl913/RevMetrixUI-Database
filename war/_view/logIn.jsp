<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Add Numbers</title>
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
	
		<form action="${pageContext.servletContext.contextPath}/logIn" method="post">
			<table>
				<tr>
					<td class="label">Username</td>
					<td><input type="text" name="username" size="12" value="${game.username}" /></td>
				</tr>
				<tr>
					<td class="label">Password</td>
					<td><input type="password" name="password" size="12" value="${game.password}" /></td>
				</tr>
				<tr>
					<td class="label">Result:</td>
					<c:if test="${game.validLogin}">
					<td>Success!</td>
					</c:if>
					<c:if test="${! game.validLogin}">
					<td>Failure!</td>
					</c:if>
				</tr>
			</table>
			<input type="Submit" name="submit" value="Sign In!">
			<input type="Submit" name="submit" value="Register Here!">
		</form>
	</body>
</html>