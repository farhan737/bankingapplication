<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>Transaction history</title>
	    <style>
	        body {
	            background-color: grey;
	            display: flex;
	            height: 100vh;
	            justify-content: center;
	            font-family: Arial, Helvetica, sans-serif;
	        }
	
	        body p {
	            margin: 0px;
	        }
	
	        #main-div {
	            width: 40%;
	            height: fit-content;
	            background-color: white;
	        }
	
	        #info-div {
	            margin: 20px 10px;
	            display: flex;
	            align-items: center;
	            justify-content: space-between;
	            font-size: 20px;
	        }
	
	        #info {
	            display: flex;
	            flex-direction: column;
	            align-items: end;
	        }
	
	        #name {
	            display: flex;
	        }
	
	        #main-div button {
	            margin: 10px;
	            font-size: 20px;
	            padding: 5px 20px;
	        }
	
	        #history-cards {
	            display: flex;
	            flex-direction: column;
	            box-sizing: border-box;
	            padding: 10px 10px 0px;
	        }
	
	        .history-card-in {
	            padding: 20px;
	            display: flex;
	            align-items: center;
	            box-sizing: border-box;
	            width: 100%;
	            height: 100px;
	            border: 3px green dashed;
	            margin: 0px 0px 10px;
	            justify-content: space-between;
	            background-color: rgba(0, 128, 0, 0.164);
	        }
	
	        .history-card-out {
	            padding: 20px;
	            display: flex;
	            align-items: center;
	            box-sizing: border-box;
	            width: 100%;
	            height: 100px;
	            border: 3px grey dashed;
	            margin: 0px 0px 10px;
	            justify-content: space-between;
	            background-color: rgba(128, 128, 128, 0.13);
	        }
	
	        .transaction-type {
	            font-size: 20px;
	            display: flex;
	            flex-direction: column;
	            justify-content: end;
	            align-items: start;
	        }
	
	        .amount-date {
	            display: flex;
	            flex-direction: column;
	            align-items: end;
	            justify-content: space-around;
	        }
	
	        .account-id {
	            font-weight: bold;
	        }
	
	        .transaction-id {
	            display: flex;
	            flex-direction: row;
	        }
	
	        .transaction-info {
	            display: flex;
	            flex-direction: row;
	        }
	
	        .card-amount {
	            display: flex;
	            align-items: center;
	            font-size: 24px;
	        }
	
	    </style>
	</head>
	
	<body>
	    <div id="main-div">
	        <div id="info-div">
	            <div id="screen-name" style="font-size: 25px;font-weight: bold;">
	                <p>Transaction history</p>
	            </div>
	            <div id="info">
	                <div id="name">
	                    <p>
	                        ${sessionScope.userDetails.firstName}
	                    </p>
	                    <p style="margin-left: 10px;">
	                        ${sessionScope.userDetails.lastName}
	                    </p>
	                </div>
	                <div style="width: 20px;"></div>
	                <div id="email">
	                    ${sessionScope.user.email}
	                </div>
	            </div>
	        </div>
	        <div style="height: 1px; background-color: grey;"></div>
	       	<div style="display: flex;justify-content: space-between;">
	       		<button type="button" onclick="location.href='dashboard.jsp'">go back</button>
		        <form method="post" action="reloadtransactions">
		        	<button type="submit">reload</button>
		        </form>
	       	</div>
	        <div style="height: 1px; background-color: grey;"></div>
	        <div id="history-cards">

    <c:forEach var="t" items="${sessionScope.transactions}">

        <!-- Incoming money (deposit, transfer_in, initial_deposit) -->
        <c:if test="${t.transactionType == 'deposit' 
                     || t.transactionType == 'transfer_in'
                     || t.transactionType == 'initial_deposit'}">

            <div class="history-card-in">
                <div class="transaction-type">
                    <div class="transaction-id">
                        <p>transaction id:</p>
                        <p>${t.transactionId}</p>
                    </div>

                    <!-- Show source account if applicable -->
                    <c:if test="${t.transactionType == 'transfer_in'}">
                        <div class="transaction-info">
                            <p>from accountId:</p>
                            <p class="account-id">${t.accountId}</p>
                        </div>
                    </c:if>

                    <c:if test="${t.transactionType != 'transfer_in'}">
                        <div class="transaction-info">
                            <p>${t.transactionType}</p>
                        </div>
                    </c:if>
                </div>

                <div class="amount-date">
                    <div class="card-amount">
                        <p>+&#8377;</p>
                        <p>${t.amount}</p>
                    </div>
                    <div class="time">
                        <p>${t.transactionTime}</p>
                    </div>
                </div>
            </div>

        </c:if>



        <!-- Outgoing money (withdraw, transfer_out) -->
        <c:if test="${t.transactionType == 'withdraw' 
                     || t.transactionType == 'transfer_out'}">

            <div class="history-card-out">
                <div class="transaction-type">
                    <div class="transaction-id">
                        <p>transaction id:</p>
                        <p>${t.transactionId}</p>
                    </div>

                    <!-- Show destination account if transfer out -->
                    <c:if test="${t.transactionType == 'transfer_out'}">
                        <div class="transaction-info">
                            <p>to accountId:</p>
                            <p class="account-id">${t.toAccountId}</p>
                        </div>
                    </c:if>

                    <c:if test="${t.transactionType == 'withdraw'}">
                        <div class="transaction-info">
                            <p>withdrawal</p>
                        </div>
                    </c:if>
                </div>

                <div class="amount-date">
                    <div class="card-amount">
                        <p>-&#8377;</p>
                        <p>${t.amount}</p>
                    </div>
                    <div class="time">
                        <p>${t.transactionTime}</p>
                    </div>
                </div>
            </div>

        </c:if>

    </c:forEach>

</div>

	    </div>
	</body>
</html>