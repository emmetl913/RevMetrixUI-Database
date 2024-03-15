

<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Bowling Ball Arsenal</title>
<style>
    body {
        font-family: Arial, sans-serif;
    }
    .container {
        max-width: 600px;
        margin: 0 auto;
        padding: 20px;
    }
    h2 {
        text-align: center;
    }
    #arsenal-list {
        list-style-type: none;
        padding: 0;
    }
    .ball {
        padding: 10px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        overflow: hidden;
    }
    .ball h3 {
        margin-top: 0;
    }
    .ball p {
        margin-bottom: 5px;
    }
    .remove-btn {
        background-color: #dc3545;
        color: #fff;
        border: none;
        padding: 5px 10px;
        border-radius: 3px;
        cursor: pointer;
        float: right;
    }
</style>
</head>
<body>
<div class="container">
    <h2>Bowling Ball Arsenal</h2>
    <form id="add-ball-form">
        <label for="ball-type">Ball Type:</label>
        <select id="ball-type">
            <option value="joker">Joker</option>
            <option value="spare">Spare</option>
            <option value="performance">Performance</option>
        </select>
        <label for="ball-name">Ball Name:</label>
        <input type="text" id="ball-name" required>
        <button type="submit">Add Ball</button>
    </form>
    <ul id="arsenal-list"></ul>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const form = document.getElementById('add-ball-form');
        const arsenalList = document.getElementById('arsenal-list');
        
        form.addEventListener('submit', function (e) {
            e.preventDefault();
            const ballType = document.getElementById('ball-type').value;
            const ballName = document.getElementById('ball-name').value;

            if (ballName.trim() !== '') {
                addBallToList(ballType, ballName);
                form.reset();
            } else {
                alert('Please enter a ball name.');
            }
        });

        function addBallToList(type, name) {
            const list = document.createElement('li');
            list.classList.add('ball');
            list.innerHTML = `
                <h3>${name}</h3>
                <p>Type: ${type}</p>
                <button class="remove-btn">Remove</button>
            `;
            arsenalList.appendChild(list);
        }

        arsenalList.addEventListener('click', function (e) {
            if (e.target.classList.contains('remove-btn')) {
                e.target.parentElement.remove();
            }
        });
    });
</script>
</body>
</html>