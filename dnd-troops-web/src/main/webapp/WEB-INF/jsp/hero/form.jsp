<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="${update ? 'hero.update' : 'hero.create'}"/>
<my:admintemplate title="${title}">
<jsp:attribute name="body">

    <h1><fmt:message key="${update ? 'hero.update' : 'hero.create'}"/></h1>

    <form:form method="post" action="${pageContext.request.contextPath}/hero/${update ? heroObj.id : ''}${update ? '/update' : 'create'}"
               modelAttribute="heroObj" id="newHero">
        <div class="form-group">
            <form:label path="name"><fmt:message key="hero.name"/> </form:label>
            <form:input path="name" cssClass="form-control"/>
            <form:errors path="name" cssClass="invalid-feedback"/>
        </div>

        <div class="form-group">
            <form:label path="race"><fmt:message key="hero.race"/> </form:label>

            <form:select path="race" cssClass="form-control">
                <c:forEach items="${races}" var="race">
                    <form:option value="${race}">
                    <c:if test="${race == 'DWARF'}">
                        <fmt:message key="hero.race.dwarf"/>
                    </c:if>
                        <c:if test="${race == 'ELF'}">
                        <fmt:message key="hero.race.elf"/>
                    </c:if>
                        <c:if test="${race == 'GNOME'}">
                        <fmt:message key="hero.race.gnome"/>
                    </c:if>
                        <c:if test="${race == 'HALFLING'}">
                        <fmt:message key="hero.race.halfling"/>
                    </c:if>
                        <c:if test="${race == 'HUMAN'}">
                        <fmt:message key="hero.race.human"/>
                    </c:if>
                        <c:if test="${race == 'ORC'}">
                        <fmt:message key="hero.race.orc"/>
                    </c:if>
                    </form:option>
                </c:forEach>
            </form:select>

            <form:errors path="race" cssClass="invalid-feedback"/>
        </div>

        <div class="form-group">
            <form:label path="experience"><fmt:message key="hero.experience"/> </form:label>
            <form:input path="experience" cssClass="form-control" type="number" min="0"/>
            <form:errors path="experience" cssClass="invalid-feedback"/>
        </div>

        <div class="form-group">
            <form:label path="groupId"><fmt:message key="hero.group"/> </form:label>
            <form:select path="groupId" cssClass="form-control">
                <form:option value="0">
                    <fmt:message key="hero.group.none"/>
                </form:option>
                <c:forEach items="${groups}" var="group">
                    <form:option value="${group.id}">
                        <c:out value="${group.name}"/>
                    </form:option>
                </c:forEach>
            </form:select>
            <form:errors path="groupId" cssClass="invalid-feedback"/>
        </div>

        <div class="form-group">
            <form:label path="roleIds"><fmt:message key="hero.roles"/> </form:label>
            <form:select path="roleIds" cssClass="form-control"  multiple="true">
                <c:forEach items="${roles}" var="role">
                    <form:option value="${role.id}">
                        <c:out value="${role.name}"/>
                    </form:option>
                </c:forEach>
            </form:select>
            <form:errors path="roleIds" cssClass="invalid-feedback"/>
        </div>

        <button class="btn btn-primary" type="submit"><fmt:message key="${update ? 'hero.update' : 'hero.create'}"/></button>
    </form:form>


</jsp:attribute>
</my:admintemplate>
