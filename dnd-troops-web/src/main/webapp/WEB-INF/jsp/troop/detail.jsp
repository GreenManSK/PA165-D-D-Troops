<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:admintemplate title="${troop.name}">
    <jsp:attribute name="body">
        <h1><c:out value="${troop.name}"/></h1>
         <a href="${pageContext.request.contextPath}/troop/${troop.id}/update" class="btn btn-primary">
             <i class="fas fa-edit"></i>
         </a><br>
        <strong><fmt:message key="troop.mission"/>:</strong>
        <c:out value="${troop.gold}"/><br>
        <strong><fmt:message key="troop.gold"/>:</strong>
        <c:out value="${troop.gold}"/>

    </jsp:attribute>
</my:admintemplate>