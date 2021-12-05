<%--
* @author rumi.dipto
* @since 12/02/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title><fmt:message key="label.leave.details.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/view/components/navbar.jsp"/>

<fmt:message key="label.leave.details.body.startDate"/> <fmt:formatDate value="${leave.startDate}"/>

<br>

<fmt:message key="label.leave.details.body.endDate"/> <fmt:formatDate value="${leave.endDate}"/>

<br>

<fmt:message key="label.leave.details.body.leaveType"/> <c:out value="${leave.leaveType}"/>

<br>

<fmt:message key="label.leave.details.body.leaveStatus"/> <c:out value="${leave.leaveStatus}"/>

<br>

<fmt:message key="label.leave.details.body.note"/> <c:out value="${leave.note}"/>

<br>


<%--<c:if test="${isSessionUserRegularEmployee}">--%>
<%--    <fmt:message key="label.leave.details.body.leaveStatus"/> <c:out value="${leave.teamLeadStatus}"/>--%>
<%--    <br>--%>
<%--&lt;%&ndash;    <fmt:message key="label.leave.details.body.hrStatus"/> <c:out value="${leave.hrStatus}"/>&ndash;%&gt;--%>
<%--</c:if>--%>

<%--<br><br>--%>

<%--<c:if test="${isSessionUserHr}">--%>
<%--    <fmt:message key="label.leave.details.body.leaveStatus"/> <c:out value="${leave.teamLeadStatus}"/>--%>
<%--</c:if>--%>

<%--<c:if test="${(isSessionUserTeamLead) && (isPendingByTeamLead)}">--%>

<%--    <form action="/leave/action" method="post">--%>
<%--        <input type="hidden" name="id" value="${leave.id}">--%>
<%--        <input type="submit" value="Approve" class="button" name="_action_approve">--%>
<%--    </form>--%>

<%--    <br>--%>

<%--    <form action="/leave/action" method="post">--%>
<%--        <input type="hidden" name="id" value="${leave.id}">--%>
<%--        <input type="submit" value="Reject" class="button" name="_action_reject">--%>
<%--    </form>--%>
<%--</c:if>--%>

<%--<c:if test="${(isSessionUserHr) && (isApprovedByTeamLead) && (isPendingByHr)}">--%>

<%--    <form action="/leave/action" method="post">--%>
<%--        <input type="hidden" name="id" value="${leave.id}">--%>
<%--        <input type="submit" value="Approve" class="button" name="_action_approve">--%>
<%--    </form>--%>

<%--    <br>--%>

<%--    <form action="/leave/action" method="post">--%>
<%--        <input type="hidden" name="id" value="${leave.id}">--%>
<%--        <input type="submit" value="Reject" class="button" name="_action_reject">--%>
<%--    </form>--%>

<%--</c:if>--%>

<%--<c:if test="${(isSessionUserTeamLead) && (!isPendingByTeamLead)}">--%>
<%--    <fmt:message key="label.leave.details.body.leaveStatus"/> <c:out value="${leave.teamLeadStatus}"/>--%>
<%--    <br>--%>
<%--    <fmt:message key="label.leave.details.body.hrStatus"/> <c:out value="${leave.hrStatus}"/>--%>
<%--</c:if>--%>

<%--<c:if test="${(isSessionUserHr) && (!isPendingByHr)}">--%>
<%--    <br>--%>
<%--    <fmt:message key="label.leave.details.body.hrStatus"/> <c:out value="${leave.hrStatus}"/>--%>
<%--</c:if>--%>

<%--<c:if test="${(isSessionUserRegularEmployee) && (isPendingByTeamLead)}">--%>
<%--    <form action="/leave/action" method="post">--%>
<%--        <input type="hidden" name="id" value="${leave.id}">--%>
<%--        <input type="submit" value="Delete" class="button" name="_action_delete">--%>
<%--    </form>--%>
<%--</c:if>--%>

</body>
</html>
