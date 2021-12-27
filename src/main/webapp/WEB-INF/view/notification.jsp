<%--
* @author rumi.dipto
* @since 12/12/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="url" value="${pageContext.request.getAttribute('javax.servlet.forward.request_uri')}"/>
<html>
<head>
    <title><fmt:message key="label.notification.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/view/components/navbar.jsp"/>

<div class="container">
    <table class="table">
        <thead class="bg-success">
        <tr>
            <th><fmt:message key="label.notification.columnHeader"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="notification" items="${notificationList}">
            <tr>
                <td><c:out value="${notification.message}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
