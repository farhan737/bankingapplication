<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ page import="java.util.List" %>
<%@ page import="bankingapplication.dto.Users" %>
<%@ page import="bankingapplication.dto.Accounts" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accounts</title>
    <style>
        body {
            margin: 0px;
            background-color: gray;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
        }

        .main-div {
            width: 800px;
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

        .accounts {
            box-sizing: border-box;
            background-color: white;
            padding: 10px;
            display: flex;
            width: 100%;
            flex-direction: column;
        }

        .account-cards {
            display: flex;
            justify-content: space-between;
            margin: 10px;
            border: 2px grey dashed;
            padding: 10px;
        }

        .action-buttons {
            display: flex;
            justify-content: space-between;
        }

        .action-buttons button {
            margin: 10px;
            height: 50px;
            width: fit-content;
            padding: 0 20px;
            font-size: 20px;
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
	
    <div class="main-div">
        <div class="info-div">
            <p>hi, ${sessionScope.userDetails.firstName} ${sessionScope.userDetails.lastName}</p>
			<p>${sessionScope.user.email}</p>
        </div>
        <div style="height: 10px;"></div>
        <div class="accounts">
        	
            <div class="action-buttons">
                <button onclick="location.href='dashboard.jsp'">go back</button>
                <button onclick="location.href='create-account.jsp'">create a new account</button>
            </div>
            
            <%
			    Users loggedIn = (Users) session.getAttribute("user");
			    if (loggedIn == null) {
			        response.sendRedirect("index.jsp");
			        return;
			    }
				
			    if (session.getAttribute("account") == null) {
					response.sendRedirect("create-account.jsp");
					return;
				}
			%>
			            
            <c:if test="${empty sessionScope.account}">
			    <div class="account-cards">
			        <p>No account associated with this user</p>
			    </div>
			</c:if>

			<c:if test="${not empty sessionScope.account}">
			    <div class="account-cards">
			        <p>${sessionScope.account.accountNumber}</p>
			        <p>${sessionScope.account.accountType}</p>
			        <p>${sessionScope.account.balance}</p>
			    </div>
			</c:if>

        </div>
    </div>
</body>

</html>