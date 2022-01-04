<%--
* @author rumi.dipto
* @since 11/25/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><fmt:message key="label.dashboard.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/view/components/navbar.jsp"/>

<c:url var="showTeamLeadListLink" value="user/teamLeadList"/>

<c:url var="showDeveloperListLink" value="/user/developerList"/>

<c:url var="showTesterListLink" value="/user/testerList"/>

<c:url var="addUserLink" value="/user/form"/>

<c:url var="showSessionUserLeaveListLink" value="/leave/myLeaveList">
    <c:param name="userId" value="${SESSION_USER.id}"/>
</c:url>

<c:url var="showSessionUserPendingLeaveListLink" value="/leave/myPendingLeaveList">
    <c:param name="userId" value="${SESSION_USER.id}"/>
</c:url>

<c:url var="showLeaveListLink" value="/leave/leaveList"/>

<c:url var="showPendingLeaveListLink" value="leave/pendingLeaveList"/>

<c:url var="addLeaveLink" value="/leave/form">
    <c:param name="userId" value="${SESSION_USER.id}"/>
</c:url>

<div class="container">
    <table class="table table-bordered table-hover">
        <thead class="bg-success">
        <tr>
            <th scope="col"><fmt:message key="label.domain"/></th>
            <th scope="col"><fmt:message key="label.actions"/></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${SESSION_USER.designation.naturalName == 'HR Executive'}">
            <tr>
                <td><fmt:message key="label.dashboard.body.teamLead"/></td>
                <td>
                    <a href="${showTeamLeadListLink}"><fmt:message key="label.link.showList"/></a>
                </td>
            </tr>
        </c:if>
        <c:if test="${SESSION_USER.designation.naturalName == 'HR Executive' ||
                            SESSION_USER.designation.naturalName == 'Team Lead'}">
            <tr>
                <td><fmt:message key="label.dashboard.body.developer"/></td>
                <td>
                    <a href="${showDeveloperListLink}"><fmt:message key="label.link.showList"/></a>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="label.dashboard.body.tester"/></td>
                <td>
                    <a href="${showTesterListLink}"><fmt:message key="label.link.showList"/></a>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="label.dashboard.body.leaves"/></td>
                <td>
                    <a href="${showLeaveListLink}"><fmt:message key="label.link.showList"/></a>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="label.dashboard.body.pendingLeaves"/></td>
                <td>
                    <a href="${showPendingLeaveListLink}"><fmt:message key="label.link.showList"/></a>
                </td>
            </tr>
        </c:if>
        <tr>
            <td><fmt:message key="label.dashboard.body.myLeaves"/></td>
            <td>
                <a href="${showSessionUserLeaveListLink}"><fmt:message key="label.link.showList"/></a>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="label.dashboard.body.myPendingLeaves"/></td>
            <td>
                <a href="${showSessionUserPendingLeaveListLink}"><fmt:message key="label.link.showList"/></a>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<div class="row justify-content-center" style="margin-top: 40px">
    <c:if test="${SESSION_USER.designation.naturalName == 'HR Executive'}">
        <input type="button" value="<fmt:message key="label.dashboard.button.newUser"/>"
               onclick="window.location.href='${addUserLink}'; return false;"
               class="button col-1">
    </c:if>

    <input type="button" value="<fmt:message key="label.dashboard.button.newLeave"/>"
           onclick="window.location.href='${addLeaveLink}'; return false;"
           class="button col-1">
</div>
</body>
</html>
