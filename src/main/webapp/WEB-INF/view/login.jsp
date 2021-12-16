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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title><fmt:message key="label.login.title"/></title>
</head>
<body>
<div class="form">
    <form:form action="login" method="post" modelAttribute="loginCommand">
        <div class="form-group">
            <label for="username"><fmt:message key="label.login.body.username"/></label>
            <form:input path="username" cssClass="form-control" placeholder="Enter username"/>
            <form:errors path="username" cssClass="errorBlock" element="div"/>
        </div>
        <div class="form-group">
            <label for="password"><fmt:message key="label.login.body.password"/></label>
            <form:password path="password" cssClass="form-control" placeholder="Enter password"/>
            <form:errors path="password" cssClass="errorBlock" element="div"/>
        </div>
        <input type="submit" value="Log in" class="button">
    </form:form>
</div>

<c:if test="${!empty error}">
    <div id="error">
        <c:out value="${error}"/>
    </div>
</c:if>

<c:if test="${!empty logoutMessage}">
    <div id="successBlock">
        <c:out value="${logoutMessage}"/>
    </div>
</c:if>

</body>
</html>
