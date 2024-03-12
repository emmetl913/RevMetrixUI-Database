<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
  <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 600px;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
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
	<head>
		<title>RevMetrix Login</title>
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
	 <div class="container">
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
					<td>Success!</td>
					<td>Failure!</td>
				</tr>
			</table>
			<input name="logIn" type="submit" value="Log In!" />
			<input type="Submit" name="submit" value="Register Here!">
		</form>
		</div>
	</body>
	
</html>