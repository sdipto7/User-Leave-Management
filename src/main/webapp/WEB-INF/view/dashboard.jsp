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

<div class="row">

    <div class="container">

        <table id="table">

            <tr>
                <th><fmt:message key="label.domain"/></th>
                <th><fmt:message key="label.actions"/></th>
            </tr>

            <c:url var="showTeamLeadListLink" value="user/teamLeadList"/>

            <c:url var="showDeveloperListLink" value="/user/developerList"/>

            <c:url var="showTesterListLink" value="/user/testerList"/>

            <c:url var="addUserLink" value="/user/form"/>

            <c:url var="showMyLeaveListLink" value="/leave/myLeaveList"/>

            <c:url var="showMyPendingLeaveListLink" value="/leave/myPendingLeaveList"/>

            <c:url var="showLeaveListLink" value="/leave/userLeaveList"/>

            <c:url var="showPendingLeaveListLink" value="/leave/userPendingLeaveList"/>

            <c:url var="addLeaveLink" value="/leave/form">
                <c:param name="userId" value="${SESSION_USER.id}"/>
            </c:url>

            <c:if test="${SESSION_USER.designation == 'HUMAN_RESOURCE'}">
                <tr>
                    <td><fmt:message key="label.dashboard.body.teamLead"/></td>
                    <td>
                        <a href="${showTeamLeadListLink}"><fmt:message key="label.link.showList"/></a>
                    </td>
                </tr>
            </c:if>
            <c:if test="${SESSION_USER.designation == 'HUMAN_RESOURCE' || SESSION_USER.designation == 'TEAM_LEAD'}">
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
                <td><fmt:message key="label.dashboard.body.myleaves"/></td>
                <td>
                    <a href="${showMyLeaveListLink}"><fmt:message key="label.link.showList"/></a>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="label.dashboard.body.myPendingLeaves"/></td>
                <td>
                    <a href="${showMyPendingLeaveListLink}"><fmt:message key="label.link.showList"/></a>
                </td>
            </tr>
        </table>

        <br><br>

        <c:if test="${SESSION_USER.designation == 'HUMAN_RESOURCE'}">
            <input type="button" value="Add New User"
                   onclick="window.location.href='${addUserLink}'; return false;"
                   class="button">
        </c:if>

        <input type="button" value="Add Leave Request"
               onclick="window.location.href='${addLeaveLink}'; return false;"
               class="button">

    </div>
</div>
</body>
</html>