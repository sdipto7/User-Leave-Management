<%--
* @author rumi.dipto
* @since 11/24/21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link href="<c:url value="/styles/styles-1.0.0.css" />" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title><fmt:message key="label.login.title"/></title>
</head>
<body>
<section class="container-fluid">
    <section class="row justify-content-center">
        <section class="col-12 col-sm-6 col-md-3">
            <form:form action="login" method="post" modelAttribute="loginCommand" cssClass="form-container">
                <div class="form-group row">
                    <label for="username" class="col-form-label col-3"><fmt:message
                            key="label.login.body.username"/></label>
                    <div class="col-9">
                        <form:input path="username" cssClass="form-control" placeholder="Enter username"/>
                    </div>
                </div>
                <div class="col-8">
                    <form:errors path="username" cssClass="errorBlock" element="div"/>
                </div>

                <div class="form-group row">
                    <label for="password" class="col-form-label col-3"><fmt:message
                            key="label.login.body.password"/></label>
                    <div class="col-9">
                        <form:password path="password" cssClass="form-control" placeholder="Enter password"/>
                    </div>
                </div>
                <div class="col-8">
                    <form:errors path="password" cssClass="errorBlock" element="div"/>
                </div>

                <div class="form-group row justify-content-center">
                    <input type="submit" value="<fmt:message key="label.button.login"/>" class="button col-3">
                </div>
            </form:form>

            <c:if test="${!empty error}">
                <div class="errorBlock">
                    <c:out value="${error}"/>
                </div>
            </c:if>

            <c:if test="${!empty logoutMessage}">
                <div id="successBlock">
                    <c:out value="${logoutMessage}"/>
                </div>
            </c:if>
        </section>
    </section>
</section>
</body>
</html>
