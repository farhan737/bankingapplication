<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Registration</title>
    <style>
        body {
            margin: 0px;
            background-color: gray;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
        }

        .main-form {
            margin-top: 10%;
            width: 700px;
            background-color: white;
            padding: 30px;
            box-sizing: border-box;
            height: fit-content;
        }

        .input-div {
            display: flex;
            flex-direction: column;
            width: 100%;
        }

        .input-div input {
            height: 50px;
            border: 2px grey solid;
            font-size: 20px;
            padding-left: 5px;
        }

        .input-div input:hover {
            border: 3px grey solid;
            border-radius: 0px;
        }

        .input-div input:focus {
            border: 3px grey solid;
            border-radius: 0px;
        }

        .input-div label {
            margin-left: 10px;
            margin-top: 10px;
            margin-bottom: 5px;
        }

        #warning-red {
            display: flex;
            justify-content: center;
            margin-top: 30px;
            background-color: rgba(255, 0, 0, 0.164);
            border: 2px red solid;
            color: red;
        }
    </style>
</head>

<body>
    <form class="main-form" method="post" action="register">
        <div style="display: flex;justify-content: center;">
            <p style="font-size: 40px;margin: 0px;">Registration</p>
        </div>
        <c:if test="${not empty status}">
    		<div id="warning-red">
        		<p>${status}</p>
    		</div>
		</c:if>

        <div style="display: flex;">
            <div class="input-div">
                <label>first name:</label>
                <input type="text" class="input-box" name="firstName">
            </div>
            <div style="width: 30px;"></div>
            <div class="input-div">
                <label>last name:</label>
                <input type="text" class="input-box" name="lastName">
            </div>
        </div>
        <div class="input-div">
            <label>email:</label>
            <input type="email" class="input-box" name="email">
        </div>
        <div class="input-div">
            <label>contact number:</label>
            <input type="number" class="input-box" name="contactNumber">
        </div>
        <div class="input-div">
            <label>permanent address:</label>
            <input type="text" class="input-box" name="permanentAddress">
        </div>
        <div class="input-div">
            <label>state:</label>
            <input type="text" class="input-box" name="state">
        </div>
        <div class="input-div">
            <label>district:</label>
            <input type="text" class="input-box" name="district">
        </div>
        <div class="input-div">
            <label>city:</label>
            <input type="text" class="input-box" name="city">
        </div>
        <div style="display: flex;">
            <div class="input-div">
                <label>pincode:</label>
                <input type="number" class="input-box" name="pincode">
            </div>
            <div style="width: 30px;"></div>
            <div class="input-div">
                <label>date of birth:</label>
                <input type="date" class="input-box" name="dateOfBirth">
            </div>
        </div>
        <div style="display: flex;">
            <div class="input-div">
                <label>password:</label>
                <input type="password" class="input-box" name="password">
            </div>
            <div style="width: 30px;"></div>
            <div class="input-div">
                <label>confirm-password:</label>
                <input type="password" class="input-box">
            </div>
        </div>
        <button
            style="color:white;font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; width: 100%; height: 50px;border: 0px; background-color: grey; margin-top: 20px;font-size: 20px;">register</button>
    </form>
</body>

</html>