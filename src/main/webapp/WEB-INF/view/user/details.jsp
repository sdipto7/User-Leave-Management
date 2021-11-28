<%--
* @author rumi.dipto
* @since 11/27/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><fmt:message key="label.user.details.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/view/components/navbar.jsp"/>

<fmt:message key="label.user.details.body.userName"/> <c:out value="${user.username}"/>
<br>
<fmt:message key="label.user.details.body.firstName"/> <c:out value="${user.firstName}"/>
<br>
<fmt:message key="label.user.details.body.lastName"/> <c:out value="${user.lastName}"/>
<br>
<fmt:message key="label.user.details.body.salary"/> <c:out value="${user.salary}"/>
<br>
<fmt:message key="label.user.details.body.sickLeaves"/> <c:out value="${leaveStat.sickLeaveCount}"/>
<br>
<fmt:message key="label.user.details.body.casualLeaves"/> <c:out value="${leaveStat.casualLeaveCount}"/>
<br>
<c:if test="${user.designation == 'DEVELOPER' || user.designation == 'TESTER'}">
    <fmt:message key="label.user.details.body.teamLead"/> <c:out value="${teamLead.firstName}"/>
</c:if>
<br><br>

<c:if test="${SESSION_USER.designation == 'HUMAN_RESOURCE'}">

    <%--    <c:url var="showTEUpdateFormLink" value="/teamLead/edit">--%>
    <%--        <c:param name="id" value="${user.id}"/>--%>
    <%--    </c:url>--%>

    <input type="button" value="Edit"
           onclick="window.location.href='${showTEUpdateFormLink}'; return false;"
           class="button">

    <br><br>

    <form action="/user/action" method="post">
        <input type="hidden" name="id" value="${userCommand.user.id}">
        <input type="submit" value="Delete" class="button" name="_action_delete">
    </form>

</c:if>

<br><br>

<c:if test="${user.designation == 'TEAM_LEAD'}">

    <h2><fmt:message key="label.user.details.body.developer"/></h2>

    <c:choose>
        <c:when test="${empty(developerList)}">
            <h5><fmt:message key="label.user.details.body.noAssignedDeveloper"/></h5>
        </c:when>

        <c:otherwise>
            <table id="table">
                <tr>
                    <th><fmt:message key="label.user.details.userList.table.columnHeader.username"/></th>
                    <th><fmt:message key="label.user.details.userList.table.columnHeader.firstName"/></th>
                    <th><fmt:message key="label.user.details.userList.table.columnHeader.lastName"/></th>
                </tr>

                <c:forEach var="developer" items="${developerList}">

                    <tr>
                        <td><c:out value="${developer.username}"/></td>
                        <td><c:out value="${developer.firstName}"/></td>
                        <td><c:out value="${developer.lastName}"/></td>
                    </tr>

                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>

    <br><br>

    <h2><fmt:message key="label.user.details.body.tester"/></h2>

    <c:choose>
        <c:when test="${empty(testerList)}">
            <h5><fmt:message key="label.user.details.body.noAssignedTester"/></h5>
        </c:when>

        <c:otherwise>
            <table id="table">
                <tr>
                    <th><fmt:message key="label.user.details.userList.table.columnHeader.username"/></th>
                    <th><fmt:message key="label.user.details.userList.table.columnHeader.firstName"/></th>
                    <th><fmt:message key="label.user.details.userList.table.columnHeader.lastName"/></th>
                </tr>

                <c:forEach var="tester" items="${testerList}">

                    <tr>
                        <td><c:out value="${tester.username}"/></td>
                        <td><c:out value="${tester.firstName}"/></td>
                        <td><c:out value="${tester.lastName}"/></td>
                    </tr>

                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>

</c:if>

</body>
</html>