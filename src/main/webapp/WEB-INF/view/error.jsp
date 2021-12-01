<%--
* @author rumi.dipto
* @since 11/25/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link href="<c:url value="/styles/styles-1.0.0.css" />" rel="stylesheet">

    <title><fmt:message key="label.error.title"/></title>
</head>
<body>
<h1><c:out value="${message}"/></h1>
<br>
<h2><fmt:message key="label.error.body.dashboard"/></h2>
<a href="/dashboard"><fmt:message key="label.navbar.body.dashboard"/></a>
</body>
</html>
