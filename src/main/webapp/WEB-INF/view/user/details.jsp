<%--
* @author rumi.dipto
* @since 11/27/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<jsp:include page="/WEB-INF/view/components/navbar.jsp"/>

<section class="container-fluid" style="margin-top: 25px">
    <section class="row justify-content-center">
        <section class="col-12 col-sm-6 col-md-3">
            <ul class="list-group">
                <li class="list-group-item"><fmt:message key="label.user.userName"/>
                    <c:out value="${user.username}"/></li>
                <li class="list-group-item"><fmt:message key="label.user.firstName"/>
                    <c:out value="${user.firstName}"/></li>
                <li class="list-group-item"><fmt:message key="label.user.lastName"/>
                    <c:out value="${user.lastName}"/></li>
                <li class="list-group-item"><fmt:message key="label.user.designation"/>
                    <c:out value="${user.designation.naturalName}"/></li>
                <li class="list-group-item"><fmt:message key="label.user.salary"/>
                    <c:out value="${user.salary}"/></li>
                <li class="list-group-item"><fmt:message key="label.user.sickLeaves"/>
                    <c:out value="${leaveStat.sickLeaveCount}"/></li>
                <li class="list-group-item"><fmt:message key="label.user.casualLeaves"/>
                    <c:out value="${leaveStat.casualLeaveCount}"/></li>

                <c:if test="${user.designation.naturalName == 'Developer' || user.designation.naturalName == 'Tester'}">
                    <li class="list-group-item"><fmt:message key="label.user.teamLead"/>
                        <c:out value="${teamLead.firstName}"/></li>
                </c:if>
            </ul>

            <div class="form-group row justify-content-center">
                <c:if test="${SESSION_USER.designation.naturalName == 'HR Executive'}">
                    <c:url var="updateUserLink" value="/user/form/">
                        <c:param name="id" value="${user.id}"/>
                    </c:url>

                    <div class="col-3">
                        <input type="submit" value="<fmt:message key="label.user.details.button.edit"/>"
                               onclick="window.location.href='${updateUserLink}'; return false;"
                               class="button">
                    </div>
                    <div class="col-4">
                        <form:form action="/user/submit" method="post" modelAttribute="user">
                            <input type="submit" value="<fmt:message key="label.button.delete"/>" class="button"
                                   name="action_delete">
                        </form:form>
                    </div>
                </c:if>
            </div>
        </section>
    </section>
</section>

<c:if test="${user.designation.naturalName == 'Team Lead'}">

    <div style="margin-top: 40px">
        <h3 style="text-align: center"><fmt:message key="label.user.details.body.developer"/></h3>
    </div>
    <c:choose>
        <c:when test="${empty(developerList)}">
            <h6 style="text-align: center"><fmt:message key="label.user.details.body.noAssignedDeveloper"/></h6>
        </c:when>
        <c:otherwise>
            <div class="container">
                <table class="table table-bordered table-hover">
                    <thead class="bg-success">
                    <tr>
                        <th scope="col"><fmt:message
                                key="label.user.details.userList.table.columnHeader.username"/></th>
                        <th scope="col"><fmt:message
                                key="label.user.details.userList.table.columnHeader.firstName"/></th>
                        <th scope="col"><fmt:message
                                key="label.user.details.userList.table.columnHeader.lastName"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="developer" items="${developerList}">
                        <tr>
                            <td><c:out value="${developer.username}"/></td>
                            <td><c:out value="${developer.firstName}"/></td>
                            <td><c:out value="${developer.lastName}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>

    <div style="margin-top: 40px">
        <h3 style="text-align: center"><fmt:message key="label.user.details.body.tester"/></h3>
    </div>
    <c:choose>
        <c:when test="${empty(testerList)}">
            <h6 style="text-align: center"><fmt:message key="label.user.details.body.noAssignedTester"/></h6>
        </c:when>
        <c:otherwise>
            <div class="container">
                <table class="table table-bordered table-hover">
                    <thead class="bg-success">
                    <tr>
                        <th><fmt:message key="label.user.details.userList.table.columnHeader.username"/></th>
                        <th><fmt:message key="label.user.details.userList.table.columnHeader.firstName"/></th>
                        <th><fmt:message key="label.user.details.userList.table.columnHeader.lastName"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="tester" items="${testerList}">
                        <tr>
                            <td><c:out value="${tester.username}"/></td>
                            <td><c:out value="${tester.firstName}"/></td>
                            <td><c:out value="${tester.lastName}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>
</body>
<head>
    <title><fmt:message key="label.user.details.title"/></title>
</head>
</html>