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
<head>
    <title><fmt:message key="label.user.details.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/view/components/navbar.jsp"/>

<ul class="list-group w-25">
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

<c:if test="${SESSION_USER.designation.naturalName == 'HR Executive'}">
    <c:url var="updateUserLink" value="/user/form/">
        <c:param name="id" value="${user.id}"/>
    </c:url>

    <input type="button" value="Edit" onclick="window.location.href='${updateUserLink}'; return false;" class="button">
    
    <form:form action="/user/submit" method="post" modelAttribute="user">
        <input type="submit" value="Delete" class="button" name="action_delete">
    </form:form>
</c:if>

<c:if test="${user.designation.naturalName == 'Team Lead'}">

    <h2><fmt:message key="label.user.details.body.developer"/></h2>

    <c:choose>
        <c:when test="${empty(developerList)}">
            <h5><fmt:message key="label.user.details.body.noAssignedDeveloper"/></h5>
        </c:when>
        <c:otherwise>
            <table class="table table-bordered table-hover">
                <thead class="bg-success">
                <tr>
                    <th scope="col"><fmt:message key="label.user.details.userList.table.columnHeader.username"/></th>
                    <th scope="col"><fmt:message key="label.user.details.userList.table.columnHeader.firstName"/></th>
                    <th scope="col"><fmt:message key="label.user.details.userList.table.columnHeader.lastName"/></th>
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
        </c:otherwise>
    </c:choose>

    <h2><fmt:message key="label.user.details.body.tester"/></h2>

    <c:choose>
        <c:when test="${empty(testerList)}">
            <h5><fmt:message key="label.user.details.body.noAssignedTester"/></h5>
        </c:when>
        <c:otherwise>
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
        </c:otherwise>
    </c:choose>
</c:if>
</body>
</html>