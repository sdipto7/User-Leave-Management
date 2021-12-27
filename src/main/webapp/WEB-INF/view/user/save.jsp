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

<section class="container-fluid">
    <section class="row justify-content-center">
        <section class="col-12 col-sm-6 col-md-3">
            <form:form action="/user/submit" method="post" modelAttribute="userSaveCommand" cssClass="form-container">
                <div style="margin: 30px">
                    <h3 style="text-align: center"><fmt:message key="label.user.save.body.form.header"/></h3>
                </div>
                <div class="form-group row">
                    <label for="user.firstName" class="col-form-label col-3"><fmt:message
                            key="label.user.save.body.firstName"/></label>
                    <div class="col-9">
                        <form:input path="user.firstName" cssClass="form-control" placeholder="Enter first name"/>
                    </div>
                </div>
                <div class="col-8">
                    <form:errors path="user.firstName" cssClass="errorBlock" element="div"/>
                </div>

                <div class="form-group row">
                    <label for="user.lastName" class="col-form-label col-3"><fmt:message
                            key="label.user.save.body.lastName"/></label>
                    <div class="col-9">
                        <form:input path="user.lastName" cssClass="form-control" placeholder="Enter last name"/>
                    </div>
                </div>
                <div class="col-8">
                    <form:errors path="user.lastName" cssClass="errorBlock" element="div"/>
                </div>

                <div class="form-group row">
                    <label for="user.username" class="col-form-label col-3"><fmt:message
                            key="label.user.save.body.username"/></label>
                    <div class="col-9">
                        <form:input path="user.username" cssClass="form-control" placeholder="Enter username"/>
                    </div>
                </div>
                <div class="col-8">
                    <form:errors path="user.username" cssClass="errorBlock" element="div"/>
                </div>

                <c:if test="${canInputPassword}">
                    <div class="form-group row">
                        <label for="user.password" class="col-form-label col-3"><fmt:message
                                key="label.user.save.body.password"/></label>
                        <div class="col-9">
                            <form:password path="user.password" cssClass="form-control" placeholder="Enter password"/>
                        </div>
                    </div>
                    <div class="col-8">
                        <form:errors path="user.password" cssClass="errorBlock" element="div"/>
                    </div>
                </c:if>

                <div class="form-group row">
                    <label for="user.salary" class="col-form-label col-3"><fmt:message
                            key="label.user.save.body.salary"/></label>
                    <div class="col-9">
                        <form:input path="user.salary" cssClass="form-control" placeholder="Enter salary"/>
                    </div>
                </div>
                <div class="col-8">
                    <form:errors path="user.salary" cssClass="errorBlock" element="div"/>
                </div>

                <c:if test="${canSelectDesignation}">
                    <div class="radio-input form-group row">
                        <label for="user.designation" class="col-form-label col-3"><fmt:message
                                key="label.user.save.body.designation"/></label>
                        <div class="col-6">
                            <form:radiobuttons path="user.designation" items="${designationList}"
                                               itemLabel="naturalName" cssStyle="margin: 7px;"/>
                        </div>
                    </div>
                    <div class="col-8">
                        <form:errors path="user.designation" cssClass="errorBlock" element="div"/>
                    </div>
                </c:if>

                <div id="teamLeadSection" class="form-group row" style="display: none">
                    <label for="teamLead" class="col-form-label col-3"><fmt:message
                            key="label.user.save.body.dropdown.teamLead"/></label>
                    <div class="col-8">
                        <form:select path="teamLead">
                            <form:options items="${teamLeadList}" itemLabel="firstName" itemValue="id"/>
                        </form:select>
                    </div>
                </div>
                <div class="col-8">
                    <form:errors path="teamLead" cssClass="errorBlock" element="div"/>
                </div>

                <div class="form-group row justify-content-center">
                    <input type="submit" value="<fmt:message key="label.button.save"/>" class="button col-3"
                           name="action_save_or_update">
                </div>
            </form:form>
        </section>
    </section>
</section>
</body>
</html>
