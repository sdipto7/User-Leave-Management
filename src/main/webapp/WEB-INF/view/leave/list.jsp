<%--
* @author rumi.dipto
* @since 12/01/21
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="url" value="${pageContext.request.getAttribute('javax.servlet.forward.request_uri')}"/>
<html>
<head>
    <title><fmt:message key="label.leave.list.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/view/components/navbar.jsp"/>

<div class="container">
    <table class="table table-bordered table-hover">
        <thead class="bg-success">
        <tr>
            <th scope="col"><fmt:message key="label.leaveList.body.columnHeader.user.firstName"/></th>
            <th scope="col"><fmt:message key="label.leaveList.body.columnHeader.user.lastName"/></th>
            <th scope="col"><fmt:message key="label.leaveList.body.columnHeader.user.designation"/></th>
            <th scope="col"><fmt:message key="label.leaveList.body.columnHeader.leave.type"/></th>
            <th scope="col"><fmt:message key="label.leaveList.body.columnHeader.leave.status"/></th>
            <th scope="col"><fmt:message key="label.actions"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="leave" items="${leaveList}">

            <c:url var="showDetailsLink" value="/leave/details">
                <c:param name="id" value="${leave.id}"/>
            </c:url>

            <tr>
                <td><c:out value="${leave.user.firstName}"/></td>
                <td><c:out value="${leave.user.lastName}"/></td>
                <td><c:out value="${leave.user.designation.naturalName}"/></td>
                <td><c:out value="${leave.leaveType.naturalName}"/></td>
                <td><c:out value="${leave.leaveStatus.naturalName}"/></td>
                <td>
                    <a href="${showDetailsLink}"><fmt:message key="label.link.details"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
        <c:forEach begin="1" end="${pageNumber}" varStatus="page">
            <c:url var="pageLink" value="${requestScope['javax.servlet.forward.request_uri']}">
                <c:param name="page" value="${page.index}"/>
            </c:url>
            <li class="page-item"><a class="page-link" href="${pageLink}">${page.index}</a></li>
        </c:forEach>
    </ul>
</nav>
</body>
</html>