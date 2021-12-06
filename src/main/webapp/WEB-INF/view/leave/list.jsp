<%--
* @author rumi.dipto
* @since 12/01/21
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><fmt:message key="label.leave.list.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/view/components/navbar.jsp"/>

<div class="row">

    <%--    <c:if test="${(isSessionUserHr) && (leaveListType.naturalName != 'Pending')}">--%>
    <%--        <fmt:message key="label.leaveList.body.employeeCount.Current"/> <c:out value="${totalEmployeeOnLeaveCurrent}"/>--%>
    <%--        <br>--%>
    <%--        <fmt:message key="label.leaveList.body.employeeCount.Previous"/> <c:out value="${totalEmployeeOnLeavePrevious}"/>--%>
    <%--        <br><br>--%>
    <%--    </c:if>--%>

    <table id="table">
        <tr>
            <th><fmt:message key="label.leaveList.body.columnHeader.user.firstName"/></th>
            <th><fmt:message key="label.leaveList.body.columnHeader.user.lastName"/></th>
            <th><fmt:message key="label.leaveList.body.columnHeader.user.designation"/></th>
            <th><fmt:message key="label.leaveList.body.columnHeader.leave.type"/></th>
            <th><fmt:message key="label.leaveList.body.columnHeader.leave.status"/></th>
            <th><fmt:message key="label.actions"/></th>
        </tr>

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
    </table>
</div>
</div>
</body>
</html>