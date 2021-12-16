<%--
* @author rumi.dipto
* @since 11/29/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><fmt:message key="label.user.save.title"/></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="<c:url value="/js/script-1.0.0.js"/>"></script>
</head>
<body>
<jsp:include page="/WEB-INF/view/components/navbar.jsp"/>

<div class="form">
    <form:form action="/user/submit" method="post" modelAttribute="userSaveCommand">
        <div class="form-group">
            <label for="user.firstName"><fmt:message key="label.user.save.body.firstName"/></label>
            <form:input path="user.firstName" cssClass="form-control" placeholder="Enter first name"/>
            <form:errors path="user.firstName" cssClass="errorBlock" element="div"/>
        </div>
        <div class="form-group">
            <label for="user.lastName"><fmt:message key="label.user.save.body.lastName"/></label>
            <form:input path="user.lastName" cssClass="form-control" placeholder="Enter last name"/>
            <form:errors path="user.lastName" cssClass="errorBlock" element="div"/>
        </div>
        <div class="form-group">
            <label for="user.username"><fmt:message key="label.user.save.body.username"/></label>
            <form:input path="user.username" cssClass="form-control" placeholder="Enter username"/>
            <form:errors path="user.username" cssClass="errorBlock" element="div"/>
        </div>
        <c:if test="${canInputPassword}">
            <div class="form-group">
                <label for="user.password"><fmt:message key="label.user.save.body.password"/></label>
                <form:password path="user.password" cssClass="form-control" placeholder="Enter password"/>
                <form:errors path="user.password" cssClass="errorBlock" element="div"/>
            </div>
        </c:if>

        <div class="form-group">
            <label for="user.salary"><fmt:message key="label.user.save.body.salary"/></label>
            <form:input path="user.salary" cssClass="form-control" placeholder="Enter salary"/>
            <form:errors path="user.salary" cssClass="errorBlock" element="div"/>
        </div>

        <c:if test="${canSelectDesignation}">
            <div class="radio-input">
                <label for="user.designation"><fmt:message key="label.user.save.body.designation"/></label>
                <form:radiobuttons path="user.designation" items="${designationList}" itemLabel="naturalName"/>
                <form:errors path="user.designation" cssClass="errorBlock" element="div"/>
            </div>
        </c:if>

        <div id="teamLeadSection" style="display: none">
            <label for="teamLead"><fmt:message key="label.user.save.body.dropdown.teamLead"/></label>
            <form:select path="teamLead">
                <form:options items="${teamLeadList}" itemLabel="firstName" itemValue="id"/>
            </form:select>
            <form:errors path="teamLead" cssClass="errorBlock" element="div"/>
        </div>

        <input type="submit" value="Save" class="button" name="action_save_or_update">
    </form:form>
</div>
</body>
</html>
