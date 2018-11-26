<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:admintemplate title="${hero.name}">
<jsp:attribute name="body">

    <h1><c:out value="${hero.name}"/></h1>

    <strong><fmt:message key="hero.experience"/>:</strong>
    <c:out value="${hero.experience}"/>
    <br>

    <strong><fmt:message key="hero.race"/>:</strong>
    <c:if test="${hero.race == 'DWARF'}">
        <fmt:message key="hero.race.dwarf"/>
    </c:if>
        <c:if test="${hero.race == 'ELF'}">
        <fmt:message key="hero.race.elf"/>
    </c:if>
        <c:if test="${hero.race == 'GNOME'}">
        <fmt:message key="hero.race.gnome"/>
    </c:if>
        <c:if test="${hero.race == 'HALFLING'}">
        <fmt:message key="hero.race.halfling"/>
    </c:if>
        <c:if test="${hero.race == 'HUMAN'}">
        <fmt:message key="hero.race.human"/>
    </c:if>
        <c:if test="${hero.race == 'ORC'}">
        <fmt:message key="hero.race.orc"/>
    </c:if>
    <br>

    <strong><fmt:message key="hero.group"/>:</strong>
    <c:if test="${hero.group != null}">
    <a href="${pageContext.request.contextPath}/group/${hero.group.id}">
        <c:out value="${hero.group.name}"/>
    </a>
    </c:if>
    <c:if test="${hero.group == null}">-</c:if>
    <br>

    <strong><fmt:message key="hero.roles"/>:</strong>
    <c:forEach items="${hero.roles}" var="role">
        <c:if test="${!role.equals(hero.roles.get(0))}">, </c:if>
        <span><c:out value="${role.name}"/></span>
    </c:forEach>

</jsp:attribute>
</my:admintemplate>
