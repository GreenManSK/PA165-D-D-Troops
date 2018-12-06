<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="${update ? 'role.update' : 'role.create'}"/>
<my:admintemplate title="${title}">
    <jsp:attribute name="body">
        <h1><fmt:message key="${update ? 'role.update' : 'role.create'}"/></h1>
        <form:form method="post" action="${pageContext.request.contextPath}/role/${update ? roleObj.id : ''}${update ? '/update' : 'create'}"
                   modelAttribute="roleObj" id="newRole">
            <div class="form-group">
                <form:label path="name"><fmt:message key="role.name"/> </form:label>
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="invalid-feedback"/>
            </div>

            <div class="form-group">
                <form:label path="description"><fmt:message key="role.description"/> </form:label>
                <form:input path="description" cssClass="form-control"/>
            </div>
            <button class="btn btn-primary" type="submit"><fmt:message key="${update ? 'role.update': 'role.create'}"/> </button>

        </form:form>
    </jsp:attribute>
</my:admintemplate>