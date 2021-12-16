<%--
* @author rumi.dipto
* @since 12/05/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><fmt:message key="label.leave.save.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/view/components/navbar.jsp"/>

<div class="form">
    <form:form action="/leave/submit" method="post" modelAttribute="leave">
        <div class="form-group">
            <label for="startDate"><fmt:message key="label.leave.save.body.startDate"/></label>
            <form:input path="startDate" cssClass="form-control" placeholder="MM/DD/YYYY"/>
            <form:errors path="startDate" cssClass="errorBlock" element="div"/>
        </div>
        <div class="form-group">
            <label for="endDate"><fmt:message key="label.leave.save.body.endDate"/></label>
            <form:input path="endDate" cssClass="form-control" placeholder="MM/DD/YYYY"/>
            <form:errors path="endDate" cssClass="errorBlock" element="div"/>
        </div>
        <div class="radio-input">
            <label for="leaveType"><fmt:message key="label.leave.save.body.leaveType"/></label>
            <form:radiobuttons path="leaveType" items="${leaveTypeList}"/>
            <form:errors path="leaveType" cssClass="errorBlock" element="div"/>
        </div>
        <div class="form-group">
            <label for="note"><fmt:message key="label.leave.save.body.note"/></label>
            <form:input path="note" cssClass="form-control"/>
            <form:errors path="note" cssClass="errorBlock" element="div"/>
        </div>

        <input type="submit" value="Save" class="button" name="action_save_or_update">
    </form:form>
</div>
</body>
</html>
