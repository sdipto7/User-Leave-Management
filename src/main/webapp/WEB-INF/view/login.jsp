<%--
* @author rumi.dipto
* @since 11/24/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link href="<c:url value="/styles/styles-1.0.0.css" />" rel="stylesheet">
    <title><fmt:message key="label.login.title"/></title>
</head>
<body>
<form:form action="login" method="post" modelAttribute="loginCommand">
    <label for="username"><fmt:message key="label.login.body.username"/></label>
    <form:input path="username"/>
    <form:errors path="username" cssClass="errorBlock" element="div"/>

    <br><br>

    <label for="password"><fmt:message key="label.login.body.password"/></label>
    <form:password path="password"/>
    <form:errors path="password" cssClass="errorBlock" element="div"/>

    <br><br>

    <input type="submit" value="Log in" class="button">
</form:form>

<br>

<div id="error">
    <c:out value="${error}"/>
</div>

<c:if test="${!empty logoutMessage}">
    <div id="successBlock">
        <c:out value="${logoutMessage}"/>
    </div>
</c:if>

</body>
</html>
