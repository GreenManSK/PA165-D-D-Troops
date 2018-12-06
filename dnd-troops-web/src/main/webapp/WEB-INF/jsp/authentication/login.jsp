<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<fmt:message var="title" key="auth.signin"/>
<my:pagetemplate title="${title}" bodyclass="bg-dark">
<jsp:attribute name="body">

    <div class="container">
        <div class="card card-login mx-auto mt-5">
            <div class="card-body">
    <c:if test="${not empty param.error}">
        <div class="alert alert-danger" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            <fmt:message key="err.invalid_username_password"/>
        </div>
    </c:if>
                <form method="post">
                    <h2 class="form-signin-heading"><fmt:message key="auth.signin"/></h2>
                    <div class="form-group">
                        <label for="user_login" class="sr-only"><fmt:message key="auth.login"/></label>
                        <input type="text" name="user_login" id="user_login" class="form-control"
                               placeholder="<fmt:message key="auth.login" />" required autofocus>
                    </div>
                    <div class="form-group">
                        <label for="user_password" class="sr-only">Password</label>
                        <input type="password" name="user_password" id="user_password" class="form-control"
                               placeholder="<fmt:message key="auth.password" />" required>
                    </div>
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message
                            key="auth.signin"/></button>
                </form>
            </div>
        </div>
    </div>
</jsp:attribute>
</my:pagetemplate>
