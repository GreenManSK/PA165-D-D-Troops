<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:admintemplate title="${role.name}">
    <jsp:attribute name="body">
        <h1><c:out value="${role.name}"/></h1>
         <a href="${pageContext.request.contextPath}/role/${role.id}/update" class="btn btn-primary">
             <i class="fas fa-edit"></i>
         </a><br>
        <strong><fmt:message key="role.description"/>:</strong>
        <c:out value="${role.description}"/>

    </jsp:attribute>
</my:admintemplate>