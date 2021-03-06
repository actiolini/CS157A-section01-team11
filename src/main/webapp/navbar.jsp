<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="moviebuddy.util.Passwords" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // Proxies

    session = request.getSession();
    if (session.getAttribute("sessionId") == null) {
        session.setAttribute("sessionId", Passwords.applySHA256(session.getId()));
    }
    if (session.getAttribute("count") == null) {
        session.setAttribute("count", 0);
    } else {
        int count = (int) session.getAttribute("count");
        session.setAttribute("count", count + 1);
    }

    request.setAttribute("signedOut", true);
    request.setAttribute("signedIn", false);
    request.setAttribute("isProvider", false);
    request.setAttribute("isAdmin", false);

    if(session.getAttribute("email") != null && session.getAttribute("currentSession").equals(Passwords.applySHA256(session.getId() + request.getRemoteAddr()))){
        request.setAttribute("signedOut", false);
        request.setAttribute("signedIn", true);
        request.setAttribute("userName", session.getAttribute("userName"));
        request.setAttribute("zip", session.getAttribute("zip"));
        if(session.getAttribute("staffId") != null){
            if(session.getAttribute("role").equals("admin")){
                request.setAttribute("isProvider", true);
                request.setAttribute("isAdmin", true);
            } else if(session.getAttribute("role").equals("manager")){
                request.setAttribute("isProvider", true);
            }
        }
    }
%>
<nav id="movieBuddyNavBar" class="navbar navbar-expand-lg navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggler"
        aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href="./home.jsp">Movie Buddy</a>
    <div class="collapse navbar-collapse" id="navbarToggler">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <c:if test="${isProvider}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">
                        Manage
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <c:if test="${isAdmin}">
                            <a class="dropdown-item" href="./managetheatre.jsp">Theatre</a>
                        </c:if>
                        <a class="dropdown-item" href="./managemovie.jsp">Movie</a>
                        <a class="dropdown-item" href="./managestaff.jsp">Staff</a>
                    </div>
                </li>
            </c:if>
            <li class="nav-item active">
                <form class="form-inline my-2 my-lg-0">
                    <label for="theatreName" class="mx-2 navbar-brand">Theatre Name</label>
                    <input class="form-control mr-sm-2" type="search" placeholder="Zip Code">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Enter</button>
                </form>
            </li>
        </ul>
        <c:if test="${signedOut}">
            <a class="nav-link" href="./signin.jsp">Sign In</a>
            <a class="nav-link" href="./signup.jsp">Sign Up</a>
        </c:if>
        <c:if test="${signedIn}">
            <form action="" method="POST" class="formAsLink">
                <input class="inputAsLink" type="submit" value="${userName}">
            </form>
            <form action="SignOut" method="POST" class="formAsLink">
                <input class="inputAsLink" type="submit" value="Sign Out">
            </form>
        </c:if>
    </div>
</nav>