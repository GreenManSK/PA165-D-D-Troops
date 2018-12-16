<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="troop.all.title"/>
<my:admintemplate title="${title}">
    <jsp:attribute name="body">

        <c:choose>
            <c:when test="${latestSearch == ''}">
                <h1><fmt:message key="troop.all.title"/></h1>
            </c:when>
            <c:otherwise>
                <h1>
                    Search for
                    <c:out value="${latestSearch}"/>
                </h1>
            </c:otherwise>
        </c:choose>

        <div class="row">
            <div class="col-md-6">
                <form:form method="post" action="${pageContext.request.contextPath}/troop/findByName"
                           modelAttribute="search" id="searchTroopName">
                    <div class="form-group col-md-3">
                    <form:label path="name">
                        <fmt:message key="troop.name"/>
                    </form:label>
                        <form:input path="name" />
                        <button class="btn btn-primary" style="margin-top: 5px" type="submit">
                            <fmt:message key="troop.search"/>
                        </button>
                    </div>
                </form:form>
            </div>
            <div class="col-md-6">
                <form:form method="post" action="${pageContext.request.contextPath}/troop/findByMission"
                           modelAttribute="searchMission" id="searchTroopMission">
                    <div class="form-group col-md-3">
                    <form:label path="mission">
                        <fmt:message key="troop.mission"/>
                    </form:label>
                        <form:input path="mission" id="mission"/>
                        <c:set var="mission" value="${mission}"/>
                        <button class="btn btn-primary" style="margin-top: 5px" type="submit">
                            <fmt:message key="troop.search"/>
                        </button>
                    </div>
                </form:form>
            </div>
        </div>

        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" with="100%" cellspacing="0">
                <thead>
                <tr>
                    <th><fmt:message key="troop.name"/></th>
                    <th><fmt:message key="troop.mission"/></th>
                    <th><fmt:message key="troop.gold"/></th>
                    <th></th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${troops}" var="troop">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/troop/${troop.id}">
                                <c:out value="${troop.name}"/>
                            </a>
                        </td>
                        <td><c:out value="${troop.mission}"/></td>
                        <td><c:out value="${troop.gold}"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/troop/${troop.id}/update"
                               class="btn btn-primary">
                                <i class="fas fa-edit"></i>
                            </a>
                            <a href="${pageContext.request.contextPath}/troop/${troop.id}/delete"
                               class="btn btn-danger">
                                <i class="fas fa-trash-alt"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <a href="${pageContext.request.contextPath}/troop/create" class="btn btn-success" style="margin-bottom: 5px">
            <fmt:message key="troop.all.add"/>
        </a>
    </jsp:attribute>
</my:admintemplate>