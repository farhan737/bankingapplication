<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="bankingapplication.dto.Users" %>
<%@ page import="bankingapplication.dto.Accounts" %>
<%@ page import="bankingapplication.dao.AccountsDao" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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

        .main-div {
            width: 800px;
            height: fit-content;
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

        .actions {
            width: 100%;
            flex: 1;
            background-color: white;
            display: flex;
            flex-direction: column;
        }

        #main-actions {
            margin: 20px;
            box-sizing: border-box;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        #quick-actions {
            margin: 20px;
            box-sizing: border-box;
            border: 2px grey dashed;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .main-actions-buttons {
            height: 80px;
            margin: 10px 10px;
            padding: 0px 50px;
            font-size: 20px;
            border: 0px;
        }

        .quick-actions-buttons {
            height: 80px;
            margin: 10px 10px;
            padding: 0px 50px;
            font-size: 20px;
            border: 0px;
        }
    </style>
    <title>Dashboard</title>
</head>

<body>

	<%
    	if (session == null || session.getAttribute("user") == null) {
        	response.sendRedirect("index.jsp");
        	return;
	    }
		if (session.getAttribute("account") == null) {
			response.sendRedirect("create-account.jsp");
			return;
		} else {
			 AccountsDao aDao_dashboard = new AccountsDao();
			 Accounts refreshedAccount = aDao_dashboard.getAccountByUserId(((Users)session.getAttribute("user")).getUserId());
			 session.setAttribute("account", refreshedAccount);
		}
	%>


    <div class="main-div">
        <div class="info-div">
            <p>hi, ${sessionScope.userDetails.firstName} ${sessionScope.userDetails.lastName}</p>
			<p>${sessionScope.user.email}</p>
			<form method="post" action="logout">
				<button>log out</button>
			</form>
        </div>
        <div style="height: 10px;"></div>
        <div class="actions">
            <div id="main-actions">
                <button onclick="location.href='withdraw.jsp'" class="main-actions-buttons">withdraw</button>
                <button onclick="location.href='deposit.jsp'" class="main-actions-buttons">deposit</button>
                <button onclick="location.href='accounts.jsp'" class="main-actions-buttons">account</button>
            </div>
            <div id="quick-actions">
                <p>quick actions</p>
                <div>
                    <button class="quick-actions-buttons">transaction history</button>
                    <button onclick="location.href='transfer.jsp'" class="quick-actions-buttons">transfer to account</button>
                </div>
            </div>
        </div>
    </div>
</body>

</html>