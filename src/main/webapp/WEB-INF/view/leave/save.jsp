<%--
* @author rumi.dipto
* @since 12/05/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
  <title><fmt:message key="label.leave.save.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/view/components/navbar.jsp"/>
<br>

<form:form action="/leave/form/save" method="post" modelAttribute="leave">
  <label for="startDate"><fmt:message key="label.leave.save.body.startDate"/></label>
  <form:input path="startDate"/>
  <form:errors path="startDate" cssClass="errorBlock" element="div"/>

  <br><br>

  <label for="endDate"><fmt:message key="label.leave.save.body.endDate"/></label>
  <form:input path="endDate"/>
  <form:errors path="endDate" cssClass="errorBlock" element="div"/>

  <br><br>

  <label for="leaveType"><fmt:message key="label.leave.save.body.leaveType"/></label>
  <form:radiobuttons path="leaveType" items="${leaveTypeList}"/>
  <form:errors path="leaveType" cssClass="errorBlock" element="div"/>

  <br><br>

  <label for="note"><fmt:message key="label.leave.save.body.note"/></label>
  <form:input path="note"/>
  <form:errors path="note" cssClass="errorBlock" element="div"/>

  <br><br>

  <input type="submit" value="Save" class="button">
</form:form>
</body>
</html>
