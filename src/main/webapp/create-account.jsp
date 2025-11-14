<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create acount</title>
    <style>
        body {
            margin: 0;
            background-color: gray;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 
                'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
        }

        #main-div {
            width: 500px;
            height: 800px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            box-sizing: border-box;
        }

        .info-div {
            width: 100%;
            height: 70px;
            background-color: white;
            padding: 10px;
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            box-sizing: border-box;
        }

        #actions {
            box-sizing: border-box;
            padding: 10px;
            width: 100%;
            background-color: white;
            display: flex;
            flex-direction: column;
        }

        #actions form {
            display: flex;
            box-sizing: border-box;
            flex-direction: column;
        }

        #actions input[type="number"],
        #actions input[type="password"] {
            height: 50px;
            margin-bottom: 10px;
            font-size: 16px;
        }

        .radios {
            display: flex;
            align-items: center;
            gap: 5px;
            margin-right: 20px;
        }

        #account-options {
            display: flex;
            align-items: center;
            margin: 10px 0;
        }

        #create-account, #go-back {
            height: 50px;
            font-size: 16px;
            cursor: pointer;
        }

        #go-back {
            width: fit-content;
        }
    </style>
</head>
<body>
	<%
    	if (session == null || session.getAttribute("user") == null) {
        	response.sendRedirect("index.jsp");
        	return;
	    }
	%>
    <div id="main-div">
        <div class="info-div">
            <p>hi, ${sessionScope.userDetails.firstName} ${sessionScope.userDetails.lastName}</p>
			<p>${sessionScope.user.email}</p>
        </div>
        <div style="height: 10px;"></div>
        <div id="actions">
		    <div style="display:flex; justify-content:space-between">
		        <button onclick="location.href='accounts.jsp'" id="go-back">go back</button>
		        <form method="post" action="logout">
		            <button style="width: fit-content;height: 100%;font-size: 16px;">log out</button>
		        </form>
		    </div>
    		<div style="height: 10px;"></div>
		    <form method="post" action="create-account">
		
		        <label>initial deposit</label>
		        <input name="amount" type="number" placeholder="Enter amount">
		
		        <div id="account-options">
		            <p>account type:</p>
		            <div class="radios">
		                <input id="savings" name="accountType" type="radio" value="savings">
		                <label for="savings">Savings</label>
		            </div>
		            <div class="radios">
		                <input id="current" name="accountType" type="radio" value="current">
		                <label for="current">Current</label>
		            </div>
		        </div>
		
		        <label for="pin">account pin</label>
		        <input id="pin" name="accountPin" type="password" placeholder="Enter PIN">
		
		        <button id="create-account">create account</button>
		    </form>
		</div>
    </div>
</body>
</html>
