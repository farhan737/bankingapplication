<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Withdraw</title>
    <style>
        body {
        	height: 100vh;
            margin: 0px;
            background-color: gray;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
        }
		
		form {
			height: 800px;
			width: 40%;
		}
		
        .main-div {
            display: flex;
            flex-direction: column;
            height: 100%;
            width: 100%;
        }

        #actions {
            background-color: white;
            display: flex;
            flex-direction: column;
            padding: 10px;
            flex: 1;
        }

        .accounts {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border: 1px solid black;
            padding: 10px;
            margin: 10px 0;
            cursor: pointer;
        }

        .accounts:hover {
            background-color: #f3f3f3;
        }

        .accounts input[type="radio"] {
            accent-color: green;
            transform: scale(1.2);
        }


        #account-pin {
            height: 60px;
            width: 20%;
            margin-top: 10px;
        }

        #withdraw-button {
            height: 60px;
            width: 100%;
            margin-top: 10px;
            font-size: 20px;
        }

        .accounts {
            display: flex;
            justify-content: space-between;
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

	<form action="deposit" method="post">
		<div class="main-div">
	        <div
	            style="background-color: white; padding: 10px;box-sizing: border-box;height: 80px;display: flex; justify-content: space-between;">
	            <button type="button" style="width: 30%;" onclick="location.href='dashboard.jsp'">go back</button>
	            <input name="amount" type="number" id="amount-input" style="height: 100%; width: 70%;margin-left: 10px;box-sizing:border-box	">
	        </div>
	        <div style="height: 10px;"></div>
	        <div id="actions">
	            <div id="account-selector">
	            	<c:if test="${not empty sessionScope.account}">
					    <label class="accounts">
						    <input type="radio" name="account" style="margin-right:10px;" class="account-cards">
						        <p>${sessionScope.account.accountId}</p>
						        <p>${sessionScope.account.accountType}</p>
						        <p>${sessionScope.account.balance}</p>
						    </input>
						</label>
					</c:if>
	            </div>
	            <div style="display: flex;flex: 1;"></div>
	            <div style="display: flex; flex-direction: column; justify-content: center;align-items: center;">
	                <label for="">pin</label>
	                <input type="password" id="account-pin" name="accountPin">
	                <button type="submit" id="withdraw-button">deposit</button>
	            </div>
	        </div>
	    </div>
	</form>

</body>

</html>