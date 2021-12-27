<%--
* @author rumi.dipto
* @since 11/22/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link href="<c:url value="/styles/styles-1.0.0.css" />" rel="stylesheet">
    <title><fmt:message key="label.index.title"/></title>
</head>
<body>
<br><br>

<h6><c:out value="${pageContext.request.contextPath}"/></h6>

<div id="panel">
    <h6><fmt:message key="label.index.body.welcome"/></h6>
    <h2><fmt:message key="label.index.body.leaveManagementSystem"/></h2>
    <input type="button" value="<fmt:message key="label.button.login"/>"
           onclick="window.location.href='/login'; return false;"
           class="button">
</div>
</body>
</html>
