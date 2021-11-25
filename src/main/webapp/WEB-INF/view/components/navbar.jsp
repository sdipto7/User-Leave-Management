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
  <title><fmt:message key="label.navbar.title"/></title>
</head>
<body>

<%--<c:url var="showProfileLink" value="/user/profile">--%>
<%--  <c:param name="id" value="${SESSION_USER.id}"/>--%>
<%--</c:url>--%>

<ul>
  <li><a href="${showProfileLink}"><fmt:message key="label.navbar.body.profile"/></a></li>

  <li><a href="/dashboard"><fmt:message key="label.navbar.body.dashboard"/></a></li>

  <li style="float:right"><a href="/logout"><fmt:message key="label.navbar.body.logoutButton"/></a></li>
</ul>

</body>
</html>
