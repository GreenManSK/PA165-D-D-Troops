<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<fmt:message var="title" key="hero.all.title"/>
<my:admintemplate title="${title}">
<jsp:attribute name="body">

    <h1><fmt:message key="hero.all.title"/></h1>

    <form:form method="post" action="${pageContext.request.contextPath}/hero/all"
               modelAttribute="search" id="searchHeroes">
        <div class="form-row">
            <div class="form-group col-md-3">
                <form:label path="roleId"><fmt:message key="hero.search.role"/> </form:label>
                <form:select path="roleId" cssClass="form-control">
                    <form:option value="">
                        <fmt:message key="hero.search.any_role"/>
                    </form:option>
                    <c:forEach items="${roles}" var="role">
                        <form:option value="${role.id}">
                            <c:out value="${role.name}"/>
                        </form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="roleId" cssClass="invalid-feedback"/>
            </div>
            <div class="form-group col-md-3">
                <form:label path="race"><fmt:message key="hero.race"/> </form:label>
                <form:select path="race" cssClass="form-control">
                    <form:option value="">
                        <fmt:message key="hero.search.any_race"/>
                    </form:option>
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
            <div class="form-group col-md-2">
                <form:label path="fromExperience"><fmt:message key="hero.search.from_experience"/> </form:label>
                <form:input path="fromExperience" cssClass="form-control" type="number"/>
                <form:errors path="fromExperience" cssClass="invalid-feedback"/>
            </div>
            <div class="form-group col-md-2">
                <form:label path="toExperience"><fmt:message key="hero.search.to_experience"/> </form:label>
                <form:input path="toExperience" cssClass="form-control" type="number"/>
                <form:errors path="toExperience" cssClass="invalid-feedback"/>
            </div>
            <div class="form-group col-md-2">
                <label>&nbsp;</label><br>
                <button class="btn btn-primary" type="submit"><fmt:message key="hero.search"/></button>
            </div>
        </div>
    </form:form>

    <div class="table-responsive">
        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
            <thead>
            <tr>
                <th><fmt:message key="hero.name"/></th>
                <th><fmt:message key="hero.experience"/></th>
                <th><fmt:message key="hero.race"/></th>
                <th><fmt:message key="hero.group"/></th>
                <th><fmt:message key="hero.roles"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${heroes}" var="hero">
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/hero/${hero.id}">
                            <c:out value="${hero.name}"/>
                        </a>
                    </td>
                    <td><c:out value="${hero.experience}"/></td>
                    <td>
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
                    </td>
                    <td>
                    <c:if test="${hero.group != null}">
                    <a href="${pageContext.request.contextPath}/group/${hero.group.id}">
                        <c:out value="${hero.group.name}"/>
                    </a>
                    </c:if>
                        <c:if test="${hero.group == null}">-</c:if>
                    </td>
                    <td>
                    <c:forEach items="${hero.roles}" var="role">
                        <c:if test="${!role.equals(hero.roles.get(0))}">, </c:if>
                        <span><c:out value="${role.name}"/></span>
                    </c:forEach>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/hero/${hero.id}/update" class="btn btn-primary">
                            <i class="fas fa-edit"></i>
                        </a>
                        <a href="${pageContext.request.contextPath}/hero/${hero.id}/delete" class="btn btn-danger">
                            <i class="fas fa-trash-alt"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <a href="${pageContext.request.contextPath}/hero/create" class="btn btn-success">
        <fmt:message key="hero.all.add"/></a>

</jsp:attribute>
</my:admintemplate>
