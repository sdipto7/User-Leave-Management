<%--
* @author rumi.dipto
* @since 11/26/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><fmt:message key="label.user.list.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/view/components/navbar.jsp"/>

<div class="row">

    <table id="table">

        <tr>
            <th><fmt:message key="label.user.list.body.columnHeader.id"/></th>
            <th><fmt:message key="label.user.list.body.columnHeader.firstName"/></th>
            <th><fmt:message key="label.user.list.body.columnHeader.lastName"/></th>
            <th><fmt:message key="label.user.list.body.columnHeader.designation"/></th>
            <th><fmt:message key="label.user.list.body.columnHeader.salary"/></th>
            <th><fmt:message key="label.actions"/></th>
        </tr>

        <c:forEach var="user" items="${userList}">

            <c:url var="showDetailsLink" value="/user/details">
                <c:param name="id" value="${user.id}"/>
            </c:url>

            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.firstName}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td><c:out value="${user.designation.naturalName}"/></td>
                <td><c:out value="${user.salary}"/></td>

                <td>
                    <a href="${showDetailsLink}"><fmt:message key="label.link.details"/></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</div>
</body>
</html>
