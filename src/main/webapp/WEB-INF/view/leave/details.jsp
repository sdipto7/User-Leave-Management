<%--
* @author rumi.dipto
* @since 12/02/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

<fmt:message key="label.leave.details.body.leaveType"/> <c:out value="${leave.leaveType.naturalName}"/>

<br>

<fmt:message key="label.leave.details.body.leaveStatus"/> <c:out value="${leave.leaveStatus.naturalName}"/>

<br>

<fmt:message key="label.leave.details.body.note"/> <c:out value="${leave.note}"/>

<br>


<form:form action="/leave/action" method="post" modelAttribute="leave">
    <form:errors cssClass="errorBlock" element="div"/>

    <c:if test="${((SESSION_USER.designation.naturalName == 'Team Lead') &&
                (leave.leaveStatus.naturalName == 'Pending by Team Lead')) ||
                ((SESSION_USER.designation.naturalName == 'HR Executive') &&
                (leave.leaveStatus.naturalName == 'Pending by HR Executive'))}">

        <input type="submit" value="Approve" class="button" name="action_approve">
        <input type="submit" value="Reject" class="button" name="action_reject">
    </c:if>

    <c:if test="${(((SESSION_USER.designation.naturalName == 'Developer') or (SESSION_USER.designation.naturalName == 'Tester'))
                and (leave.leaveStatus.naturalName == 'Pending by Team Lead')) or
                ((SESSION_USER.designation.naturalName == 'Team Lead') and (leave.leaveStatus.naturalName == 'Pending by HR Executive')
                and (leave.user.designation.naturalName == 'Team Lead'))}">

        <input type="submit" value="Delete" class="button" name="action_delete">
    </c:if>
</form:form>
</body>
</html>
