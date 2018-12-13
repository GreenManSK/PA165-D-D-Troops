<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:admintemplate title="${group.name}">
<jsp:attribute name="body">

    <h1><c:out value="${group.name}"/></h1>

    <c:if test="${isAdmin}">
    <a href="${pageContext.request.contextPath}/group/${group.id}/update" class="btn btn-primary">
        <i class="fas fa-edit"></i>
    </a>
    </c:if>
    <br>


    <strong><fmt:message key="group.heroes"/>:</strong>
    <div class="table-responsive">
        <table class="table table-bordered" id="heroTable" width="100%" cellspacing="0">
            <thead>
            <tr>
                <th width="30%"><fmt:message key="group.heroes"/></th>
                <th width="60%"><fmt:message key="group.heroes.role"/></th>
                <c:if test="${isAdmin}">
                    <th><fmt:message key="group.heroes.remove"/></th>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${heroes}" var="hero">
                    <c:if test="${hero.group.id == group.id}">
                        <tr>
                            <td>
                                <a href="${pageContext.request.contextPath}/hero/${hero.id}">
                                    <c:out value="${hero.name}"/>
                                </a>
                                <br>
                            </td>
                            <td>
                                <c:forEach items="${hero.roles}" var="role">
                                    <c:choose>
                                    <c:when test="${isAdmin}">
                                    <a href="${pageContext.request.contextPath}/role/${role.id}">
                                        <c:out value="${role.name}"/>
                                    </a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${role.name}"/>
                                    </c:otherwise>
                                    </c:choose>
                                    <br>
                                </c:forEach>
                            </td>
                            <c:if test="${isAdmin}">
                            <td>
                                <a href="${pageContext.request.contextPath}/group/${group.id}/removeHero/${hero.id}"
                                   class="btn btn-danger">
                                    <i class="fas fa-user-minus"></i>
                                </a>
                            </td>
                            </c:if>
                        </tr>
                    </c:if>
             </c:forEach>
            </tbody>
        </table>
    </div>

    <c:if test="${isAdmin}">
    <strong><fmt:message key="group.heroes.add"/>:</strong>
    <div class="table-responsive">
        <table class="table table-bordered" id="addHeroTable" width="100%" cellspacing="0">
            <thead>
            <tr>
                <th width="30%"><fmt:message key="group.heroes"/></th>
                <th width="60%"><fmt:message key="group.heroes.role"/></th>
                <th><fmt:message key="group.heroes.add"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${heroes}" var="hero">
                    <c:if test="${hero.group.id != group.id}">
                        <tr>
                            <td>
                                <a href="${pageContext.request.contextPath}/hero/${hero.id}">
                                    <c:out value="${hero.name}"/>
                                </a>
                                <br>
                            </td>
                            <td>
                                <c:forEach items="${hero.roles}" var="role">
                                    <a href="${pageContext.request.contextPath}/role/${role.id}">
                                        <c:out value="${role.name}"/>
                                    </a>
                                    <br>
                                </c:forEach>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/group/${group.id}/addHero/${hero.id}"
                                   class="btn btn-success">
                                    <i class="fas fa-user-plus"></i>
                                </a>
                            </td>
                        </tr>
                    </c:if>
             </c:forEach>
            </tbody>
        </table>
    </div>
    </c:if>

</jsp:attribute>
</my:admintemplate>