<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="group.search.byRole"/>
<my:admintemplate title="${title}">
    <jsp:attribute name="body">

        <h1><fmt:message key="group.search.byRole"/></h1>

        <p><fmt:message key="group.findWithoutRoles.description"/></p>

        <form:form method="post" action="${pageContext.request.contextPath}/group/findWithoutRoles"
                   modelAttribute="rolesObj" id="searchGroups">
            <div class="form-group">
                <form:select path="roleIds" cssClass="form-control" multiple="true">
                    <c:forEach items="${roles}" var="role">
                        <form:option value="${role.id}">
                            <c:out value="${role.name}"/>
                        </form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="roleIds" cssClass="invalid-feedback"/>
            </div>
            <button class="btn btn-primary" type="submit">
                <fmt:message key="group.search"/>
            </button>
         </form:form>

    </jsp:attribute>
</my:admintemplate>