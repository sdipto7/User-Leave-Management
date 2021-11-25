<%--
* @author rumi.dipto
* @since 11/25/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

            <c:url var="showTeamleadListLink" value="/user/teamleadlist"/>

            <c:url var="showDeveloperListLink" value="/user/developerList"/>

            <c:url var="showTesterListLink" value="/user/testerList"/>

            <c:url var="addUserLink" value="/user/form"/>

            <c:url var="showLeaveListLink" value="/leave/leaveList"/>

            <c:url var="showPendingLeaveListLink" value="/leave/pendingLeaveList"/>

            <c:url var="addLeaveLink" value="/leave/form">
<%--                <c:param name="userId" value="${SESSION_USER.id}"/>--%>
            </c:url>

            <c:if test="${isSessionUserHumanResource}">
                <tr>
                    <td><fmt:message key="label.dashboard.body.teamlead"/></td>
                    <td>
                        <a href="${showTeamleadListLink}"><fmt:message key="label.dashboard.action.showListLink"/></a>
                        <a href="${addUserLink}"><fmt:message key="label.dashboard.action.addLink"/></a>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="label.dashboard.body.developer"/></td>
                    <td>
                        <a href="${showDeveloperListLink}"><fmt:message key="label.dashboard.action.showListLink"/></a>
                        <a href="${addUserLink}"><fmt:message key="label.dashboard.action.addLink"/></a>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="label.dashboard.body.tester"/></td>
                    <td>
                        <a href="${showTesterListLink}"><fmt:message key="label.dashboard.action.showListLink"/></a>
                        <a href="${addUserLink}"><fmt:message key="label.dashboard.action.addLink"/></a>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="label.dashboard.body.leaves"/></td>
                    <td>
                        <a href="${showLeaveListLink}"><fmt:message key="label.dashboard.action.showListLink"/></a>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="label.dashboard.body.pendingLeaves"/></td>
                    <td>
                        <a href="${showPendingLeaveListLink}"><fmt:message key="label.dashboard.action.showListLink"/></a>
                    </td>
                </tr>
            </c:if>

            <c:if test="${isSessionUserTeamlead}">
                <tr>
                    <td><fmt:message key="label.dashboard.body.developer"/></td>
                    <td>
                        <a href="${showDeveloperListLink}"><fmt:message key="label.dashboard.action.showListLink"/></a>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="label.dashboard.body.tester"/></td>
                    <td>
                        <a href="${showTesterListLink}"><fmt:message key="label.dashboard.action.showListLink"/></a>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="label.dashboard.body.leaves"/></td>
                    <td>
                        <a href="${showLeaveListLink}"><fmt:message key="label.dashboard.action.showListLink"/></a>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="label.dashboard.body.pendingLeaves"/></td>
                    <td>
                        <a href="${showPendingLeaveListLink}"><fmt:message key="label.dashboard.action.showListLink"/></a>
                    </td>
                </tr>
            </c:if>

            <c:if test="${isSessionUserDeveloper || isSessionUserTester}">
                <tr>
                    <td><fmt:message key="label.dashboard.body.leaves"/></td>
                    <td>
                        <a href="${showLeaveListLink}"><fmt:message key="label.dashboard.action.showListLink"/></a>
                        <a href="${addLeaveLink}"><fmt:message key="label.dashboard.action.addLink"/></a>
                    </td>
                </tr>
            </c:if>

        </table>

    </div>
</div>
</body>
</html>
