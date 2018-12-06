<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="role.all.title"/>
<my:admintemplate title="${title}">
    <jsp:attribute name="body">
        <h1><fmt:message key="role.all.title"/></h1>

        <form:form method="get" action="${pageContext.request.contextPath}/role/all" modelAttribute="byName">
            <div class="form-group">
                <form:label path="name">
                    <fmt:message key="role.name"/>
                </form:label>
                <form:input path="name"/>
                <button class="btn btn-primary" type="submit">
                    <fmt:message key="role.search"/>
                </button>
            </div>
        </form:form>

        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" with="100%" cellspacing="0">
                <thead>
                <tr>
                    <th><fmt:message key="role.name"/></th>
                    <th><fmt:message key="role.description"/></th>
                    <th></th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${roles}" var="role">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/role/${role.id}">
                                <c:out value="${role.name}"/>
                            </a>
                        </td>
                        <td><c:out value="${role.description}"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/role/${role.id}/update" class="btn btn-primary">
                                <i class="fas fa-edit"></i>
                            </a>
                            <a href="${pageContext.request.contextPath}/role/${role.id}/delete" class="btn btn-danger">
                                <i class="fas fa-trash-alt"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <a href="${pageContext.request.contextPath}/role/create" class="btn btn-success">
            <fmt:message key="role.all.add"/>
        </a>
    </jsp:attribute>
</my:admintemplate>