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
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css"/>
    <script src="https://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="https://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
    <script type="text/javascript" src="<c:url value="/js/script-1.0.0.js"/>"></script>
    <title><fmt:message key="label.leave.save.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/view/components/navbar.jsp"/>

<section class="container-fluid">
    <section class="row justify-content-center">
        <section class="col-12 col-sm-6 col-md-3">
            <form:form action="/leave/submit" method="post" modelAttribute="leave" cssClass="form-container">
                <div style="margin: 30px">
                    <h3 style="text-align: center"><fmt:message key="label.leave.save.body.form.header"/></h3>
                </div>

                <div class="form-group row">
                    <label for="startDate" class="col-form-label col-3"><fmt:message
                            key="label.leave.save.body.startDate"/></label>
                    <div class="col-9">
                        <form:input path="startDate" cssClass="form-control date-picker" placeholder="MM/DD/YYYY"/>
                    </div>
                </div>
                <div class="col-8">
                    <form:errors path="startDate" cssClass="errorBlock" element="div"/>
                </div>

                <div class="form-group row">
                    <label for="endDate" class="col-form-label col-3"><fmt:message
                            key="label.leave.save.body.endDate"/></label>
                    <div class="col-9">
                        <form:input path="endDate" cssClass="form-control date-picker" placeholder="MM/DD/YYYY"/>
                    </div>
                </div>
                <div class="col-8">
                    <form:errors path="endDate" cssClass="errorBlock" element="div"/>
                </div>

                <div class="radio-input row">
                    <label for="leaveType" class="col-form-label col-4"><fmt:message
                            key="label.leave.save.body.leaveType"/></label>
                    <div class="col-8">
                        <form:radiobuttons path="leaveType" items="${leaveTypeList}" itemLabel="naturalName"
                                           cssStyle="margin: 8px;"/>
                    </div>
                </div>
                <div class="col-8">
                    <form:errors path="leaveType" cssClass="errorBlock" element="div"/>
                </div>

                <div id="sick-leave-count-section" class="form-group row" style="display: none">
                    <div class="col-9">
                        <label><fmt:message key="label.leave.save.body.sickLeaveCount"/><c:out
                                value="${sickLeaveCount}"/></label>
                    </div>
                </div>
                <div id="casual-leave-count-section" class="form-group row" style="display: none">
                    <div class="col-9">
                        <label><fmt:message key="label.leave.save.body.casualLeaveCount"/><c:out
                                value="${casualLeaveCount}"/></label>
                    </div>
                </div>

                <div class="form-group row">
                    <label for="note" class="col-form-label col-3"><fmt:message
                            key="label.leave.save.body.note"/></label>
                    <div class="col-9">
                        <form:textarea path="note" cssClass="form-control"/>
                    </div>
                </div>
                <div class="col-8">
                    <form:errors path="note" cssClass="errorBlock" element="div"/>
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
