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

<br>

<form:form action="/user/submit" method="post" modelAttribute="userSaveCommand">
    <label for="user.firstName"><fmt:message key="label.user.save.body.firstName"/></label>
    <form:input path="user.firstName"/>
    <form:errors path="user.firstName" cssClass="errorBlock" element="div"/>

    <br><br>

    <label for="user.lastName"><fmt:message key="label.user.save.body.lastName"/></label>
    <form:input path="user.lastName"/>
    <form:errors path="user.lastName" cssClass="errorBlock" element="div"/>

    <br><br>

    <label for="user.username"><fmt:message key="label.user.save.body.username"/></label>
    <form:input path="user.username"/>
    <form:errors path="user.username" cssClass="errorBlock" element="div"/>

    <br><br>

    <c:if test="${userSaveCommand.user.id == 0}">
        <label for="user.password"><fmt:message key="label.user.save.body.password"/></label>
        <form:password path="user.password"/>
        <form:errors path="user.password" cssClass="errorBlock" element="div"/>

        <br><br>
    </c:if>

    <c:if test="${(userSaveCommand.user.id == 0 ||
     ((userSaveCommand.user.id != 0) && ((userSaveCommand.user.designation.naturalName == 'Developer')
        || (userSaveCommand.user.designation.naturalName == 'Tester'))))}">
        <label for="user.designation"><fmt:message key="label.user.save.body.designation"/></label>
        <form:radiobuttons path="user.designation" items="${designationList}" itemLabel="naturalName"/>
        <form:errors path="user.designation" cssClass="errorBlock" element="div"/>

        <br><br>
    </c:if>

    <label for="user.salary"><fmt:message key="label.user.save.body.salary"/></label>
    <form:input path="user.salary"/>
    <form:errors path="user.salary" cssClass="errorBlock" element="div"/>

    <br><br>

    <div id="teamLeadSection" style="display: none">
        <label for="teamLead"><fmt:message key="label.user.save.body.dropdown.teamLead"/></label>
        <form:select path="teamLead">
            <form:options items="${teamLeadList}" itemLabel="firstName" itemValue="id"/>
        </form:select>
        <form:errors path="teamLead" cssClass="errorBlock" element="div"/>
    </div>

    <br><br>

    <input type="submit" value="Save" class="button" name="action_save_or_update">
</form:form>
</body>
</html>
