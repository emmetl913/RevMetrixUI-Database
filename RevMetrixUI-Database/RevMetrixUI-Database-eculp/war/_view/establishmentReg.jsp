<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Establishment Registration</title>
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
    </style>
</head>
<body>
    <c:if test="${! empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>
    
    <div class="container">
        <h2>Establishment Registration</h2>
        <form action="${pageContext.servletContext.contextPath}/establishmentReg" method="post">
            <label>Establishment Name:</label>
            <input type="text" name="establishmentName" size="12" value="${game.establishmentName}">

            <label for="email">Email:</label>
            <input type="text" name="email" size="12" value="${game.email}">

            <label for="address">Address:</label>
            <input type="text" name="address" size="12" value="${game.address}">

            <button type="submit">Register</button>
        </form>
    </div>

</body>
</html>
