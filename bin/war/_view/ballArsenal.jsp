<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Ball Arsenal</title>
		<link rel="stylesheet" href="style.css">
	</head>

	<body>
		<form action="${pageContext.servletContext.contextPath}/ballArsenal" method="post">
			<header>
				<h1>Ball Arsenal</h1>
			</header>
		</form>
	</body>
</html>