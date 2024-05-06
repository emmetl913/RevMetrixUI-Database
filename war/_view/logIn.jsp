<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style>
	body {
            background-image: url("${pageContext.request.contextPath}/_view/coolbowling.jpg");
    		background-repeat: no-repeat;
    		background-attachment: fixed;
    		background-size: cover; 
    		/* background-color: darkgrey; */
    		font-family: Arial, sans-serif;
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

	input[type=submit] {
            background-color: #4caf50;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
    }

	input[type=submit]:hover {
            background-color: #45a049;
	}

	.error {
			color: red;
	}
</style>
<html>
  	
	<head>
	
	
		<title>RevMetrix Login</title>
		
		<link rel="stylesheet" href="account.css">
	</head>

	<body>
	 <div class="container">
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>
	
		<form action="${pageContext.servletContext.contextPath}/logIn" method="post">
			<table>
				<tr>
					<td class="label">Username: </td>
					<td><input type="text" name="username" size="12" value="${username}" autocomplete="off" /></td>
				</tr>
				<tr>
					<td class="label">Password: </td>
					<td><input type="password" name="password" size="12" value="${password}" autocomplete="off" /></td>
				</tr>
				</table>
			<input name="logIn" type="submit" value="Log In!">
			<input type="submit" name="registerButton" value="Register Here!">
		</form>
		</div>
	</body>
	
</html>
<script src="https://apis.google.com/js/platform.js" async defer></script>