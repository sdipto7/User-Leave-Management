<%--
* @author rumi.dipto
* @since 11/24/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link href="<c:url value="/styles/styles-1.0.0.css" />" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title><fmt:message key="label.navbar.title"/></title>
</head>
<body>

<c:url var="showProfileLink" value="/user/profile">
    <c:param name="id" value="${SESSION_USER.id}"/>
</c:url>

<c:url var="showNotificationLink" value="/notification">
    <c:param name="userId" value="${SESSION_USER.id}"/>
</c:url>

<nav class="navbar navbar-expand-lg navbar-dark bg-success">
    <div class="container-fluid">
        <div class="collapse navbar-collapse justify-content-center" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" style="color: white" href="${showProfileLink}"><fmt:message key="label.navbar.body.profile"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" style="color: white" href="/dashboard" aria-current="page"><fmt:message key="label.navbar.body.dashboard"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" style="color: white" href="${showNotificationLink}"><fmt:message key="label.navbar.body.notification"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" style="color: white" href="/logout"><fmt:message key="label.navbar.body.logoutButton"/></a>
                </li>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
