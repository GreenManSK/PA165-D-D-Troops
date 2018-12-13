<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="group.all.title"/>
<my:admintemplate title="${title}">
    <jsp:attribute name="body">

        <h1><fmt:message key="group.all.title"/></h1>

        <form:form method="post" action="${pageContext.request.contextPath}/group/all"
                   modelAttribute="search" id="findGroupByName">
            <div class="form-group col-md-3">
                <form:label path="name">
                    <fmt:message key="group.name"/>
                </form:label>&nbsp;
                <form:input path="name"/>&nbsp;
                <button class="btn btn-primary" type="submit">
                    <fmt:message key="group.search"/>
                </button>
            </div>
         </form:form>
        <div>
            <a href="${pageContext.request.contextPath}/group/findWithoutRoles" class="btn btn-primary">
                <fmt:message key="group.search.byRole"/>
            </a>
        </div><br><br>

        <c:forEach items="${groups}" var="group">
            <h4>
                <a href="${pageContext.request.contextPath}/group/${group.id}">
                    <c:out value="${group.name}"/>
                </a>
            </h4>
            <div class="table-responsive">
                <table class="table table-bordered" id="heroTable${group.id}" width="100%" cellspacing="0">
                    <thead>
                    <tr>
                        <th width="30%"><fmt:message key="group.heroes"/></th>
                        <th><fmt:message key="group.heroes.role"/></th>
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
                        </tr>
                    </c:if>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <c:if test="${isAdmin}">
                <br>
            <div>
                <a href="${pageContext.request.contextPath}/group/${group.id}/update" class="btn btn-primary">
                    <i class="fas fa-edit"></i>
                </a>
                <a href="${pageContext.request.contextPath}/group/${group.id}/delete" class="btn btn-danger">
                    <i class="fas fa-trash-alt"></i>
                </a>
            </div>
            </c:if>
            <br>
            <br>
        </c:forEach>

        <c:if test="${isAdmin}">
        <a href="${pageContext.request.contextPath}/group/create" class="btn btn-success">
            <fmt:message key="group.all.add"/>
        </a>
        </c:if>


    </jsp:attribute>
</my:admintemplate>
