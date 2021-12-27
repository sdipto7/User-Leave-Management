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

<section class="container-fluid" style="margin-top: 25px">
    <section class="row justify-content-center">
        <section class="col-12 col-sm-6 col-md-3">
            <ul class="list-group">
                <li class="list-group-item"><fmt:message key="label.leave.details.body.startDate"/>
                    <fmt:formatDate value="${leave.startDate}"/></li>
                <li class="list-group-item"><fmt:message key="label.leave.details.body.endDate"/>
                    <fmt:formatDate value="${leave.endDate}"/></li>
                <li class="list-group-item"><fmt:message key="label.leave.details.body.leaveType"/>
                    <c:out value="${leave.leaveType.naturalName}"/></li>
                <li class="list-group-item"><fmt:message key="label.leave.details.body.leaveStatus"/>
                    <c:out value="${leave.leaveStatus.naturalName}"/></li>
                <li class="list-group-item"><fmt:message key="label.leave.details.body.note"/>
                    <c:out value="${leave.note}"/></li>
            </ul>

            <form:form action="/leave/action" method="post" modelAttribute="leave">
                <form:errors cssClass="errorBlock" element="div"/>
                <div class="form-group row justify-content-center" style="margin-top: 40px">
                    <c:if test="${canReview}">
                        <input type="submit" value="<fmt:message key="label.leave.details.button.approve"/>"
                               class="button col-4" name="action_approve">
                        <input type="submit" value="<fmt:message key="label.leave.details.button.reject"/>"
                               class="button col-4" name="action_reject">
                    </c:if>

                    <c:if test="${canDelete}">
                        <input type="submit" value="<fmt:message key="label.button.delete"/>" class="button col-4"
                               name="action_delete">
                    </c:if>
                </div>
            </form:form>
        </section>
    </section>
</section>
</body>
</html>
