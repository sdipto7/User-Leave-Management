<%--
* @author rumi.dipto
* @since 11/30/21
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="label.user.profile.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/view/components/navbar.jsp"/>

<div id="success">
    <c:out value="${doneMessage}"/>
</div>

<ul class="list-group">
    <li class="list-group-item"><fmt:message key="label.user.profile.body.welcome"/>
        <c:out value="${userProfileCommand.user.firstName}"/></li>
    <li class="list-group-item"><fmt:message key="label.user.userName"/>
        <c:out value="${userProfileCommand.user.username}"/></li>
    <li class="list-group-item"><fmt:message key="label.user.firstName"/>
        <c:out value="${userProfileCommand.user.firstName}"/></li>
    <li class="list-group-item"><fmt:message key="label.user.lastName"/>
        <c:out value="${userProfileCommand.user.lastName}"/></li>
    <li class="list-group-item"><fmt:message key="label.user.designation"/>
        <c:out value="${userProfileCommand.user.designation.naturalName}"/></li>
    <li class="list-group-item"><fmt:message key="label.user.salary"/>
        <c:out value="${userProfileCommand.user.salary}"/></li>
    <li class="list-group-item"><fmt:message key="label.user.sickLeaves"/>
        <c:out value="${leaveStat.sickLeaveCount}"/></li>
    <li class="list-group-item"><fmt:message key="label.user.casualLeaves"/>
        <c:out value="${leaveStat.casualLeaveCount}"/></li>

    <c:if test="${userProfileCommand.user.designation.naturalName == 'Developer' ||
                userProfileCommand.user.designation.naturalName == 'Tester'}">
        <li class="list-group-item"><fmt:message key="label.user.teamLead"/>
            <c:out value="${teamLead.firstName}"/></li>
    </c:if>
</ul>

<div class="form">
    <form:form action="/user/updatePassword" method="post" modelAttribute="userProfileCommand">
        <div class="form-group">
            <label for="currentPassword"><fmt:message key="label.user.profile.body.currentPassword"/></label>
            <form:password path="currentPassword" cssClass="form-control" placeholder="Enter current password"/>
            <form:errors path="currentPassword" cssClass="errorBlock" element="div"/>
        </div>
        <div class="form-group">
            <label for="newPassword"><fmt:message key="label.user.profile.body.newPassword"/></label>
            <form:password path="newPassword" cssClass="form-control" placeholder="Enter new password"/>
            <form:errors path="newPassword" cssClass="errorBlock" element="div"/>
        </div>
        <div class="form-group">
            <label for="confirmedNewPassword"><fmt:message key="label.user.profile.body.confirmNewPassword"/></label>
            <form:password path="confirmedNewPassword" cssClass="form-control" placeholder="Re-enter new password"/>
            <form:errors path="confirmedNewPassword" cssClass="errorBlock" element="div"/>
        </div>

        <input type="submit" value="Change Password" class="button">
    </form:form>
</div>
</body>
</html>
