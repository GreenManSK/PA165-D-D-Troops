<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<fmt:message var="title" key="navigation.project"/>
<my:pagetemplate title="616">
<jsp:attribute name="body">
    <c:if test="${not isAuthenticated}">
        testing <c:out value="${getLogin}"/>
testing
    </c:if>
    <c:if test="${isAuthenticated}">
        testing better! <c:out value="${getLogin}"/>
    </c:if>
</jsp:attribute>
</my:pagetemplate>
