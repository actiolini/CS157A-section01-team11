<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>

<head>
    <link rel="stylesheet" href="./css/style.css">
    <title>Movie Buddy | Error</title>
    <style>
        .container {
            width: 60%;
            margin: auto;
            position: relative;
        }

        .textbox {
            position: absolute;
            right: 0;
            bottom: 0;
        }

        .errMsg {
            text-align: center;
            font-size: 40px;
        }

        .link {
            background-color: transparent;
            border: none;
            color: #007bff;
            cursor: pointer;
            font-size: 1rem;
            text-decoration: none;
        }

        .link:hover {
            color: #0056b3;
            text-decoration: none;
        }

        .link:focus {
            outline: none;
        }
    </style>
</head>

<body>
    <div class='container'>
        <img style='width: 50%;' src='./images/error.png' alt='saitama'>
        <div class='textbox'>
            <p class='errMsg'>Oops!!!</p>
            It seems like something just went wrong.<br>
            Please <a class="link" href="home.jsp">click here</a> to return to our home page.
        </div>
    </div>
</body>

</html>