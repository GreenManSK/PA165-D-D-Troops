<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="${update ? 'group.update' : 'group.create'}"/>
<my:admintemplate title="${title}">
    <jsp:attribute name="body">

        <h1><fmt:message key="${update ? 'group.update' : 'group.create'}"/></h1>

        <form:form method="post"
                   action="${pageContext.request.contextPath}/group/${update ? groupObj.id : ''}${update ? '/update' : 'create'}"
                   modelAttribute="groupObj" id="newGroup">
            <div class="form-group">
                <form:label path="name"><fmt:message key="group.name"/></form:label>
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="invalid-feedback"/>
            </div>
            <button class="btn btn-primary" type="submit">
                <fmt:message key="${update ? 'group.update' : 'group.create'}"/>
            </button>
        </form:form>

    </jsp:attribute>
</my:admintemplate>