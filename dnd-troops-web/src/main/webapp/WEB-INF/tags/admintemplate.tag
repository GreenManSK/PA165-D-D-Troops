<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pagetemplate title="${title}">
<jsp:attribute name="body">
<nav class="navbar navbar-expand navbar-dark bg-dark static-top">

    <a class="navbar-brand mr-1" href="${pageContext.request.contextPath}"><f:message key="navigation.project"/></a>

    <c:if test="${isAuthenticated}">
        <button class="btn btn-link btn-sm text-white order-1 order-sm-0" id="sidebarToggle" href="#">
            <i class="fas fa-bars"></i>
        </button>
    </c:if>

    <div class="ml-auto mr-0 mr-md-3 my-2 my-md-0">
    </div>

    <c:if test="${isAuthenticated}">
        <!-- Navbar -->
        <ul class="navbar-nav ml-auto ml-md-0">
            <li class="nav-item dropdown no-arrow">
                <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    <i class="fas fa-user-circle fa-fw"></i>
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                    <span class="dropdown-item-text">
                        <f:message key="navigation.hello"/>&nbsp;
                        <c:out value="${getLogin}"/>
                    </span>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/logout"><f:message
                            key="auth.logout"/></a>
                </div>
            </li>
        </ul>
    </c:if>

</nav>

<div id="wrapper">

    <c:if test="${isAuthenticated}">
        <!-- Sidebar -->
        <ul class="sidebar navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}">
                    <i class="fas fa-fw fa-home"></i>
                    <span>
                        <f:message key="navigation.home"/>
                    </span>
                </a>
            </li>
            <c:if test="${isUser}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/hero/user">
                                    <i class="fas fa-fw fa-user"></i>
                                    <span>
                        <f:message key="navigation.myHero"/>
                    </span>
                                </a>
                            </li>
            </c:if>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/groups/all">
                    <i class="fas fa-fw fa-users-cog"></i>
                    <span>
                        <f:message key="navigation.groups"/>
                    </span>
                </a>
            </li>
            <c:if test="${isAdmin}">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/heroes/all">
                    <i class="fas fa-fw fa-users"></i>
                    <span>
                        <f:message key="navigation.heroes"/>
                    </span>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/troop/all">
                    <i class="fas fa-fw fa-user-ninja"></i>
                    <span>
                        <f:message key="navigation.troops"/>
                    </span>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/roles/all">
                    <i class="fas fa-fw fa-dice-d20"></i>
                    <span>
                        <f:message key="navigation.roles"/>
                    </span>
                </a>
            </li>
            </c:if>
        </ul>
    </c:if>

    <div id="content-wrapper">

        <div class="container-fluid">
            <jsp:invoke fragment="body"/>
        </div>
        <!-- /.container-fluid -->

        <c:if test="${isAuthenticated}">
            <!-- Sticky Footer -->
            <footer class="sticky-footer">
                <div class="container my-auto">
                    <div class="copyright text-center my-auto">
                    <span>
                        <f:message key="footer.text"/><br>
                        <a href="https://github.com/GreenManSK/PA165-D-D-Troops"
                           target="_blank"><f:message key="footer.source"/></a>
                    </span>
                    </div>
                </div>
            </footer>
        </c:if>

    </div>
    <!-- /.content-wrapper -->

</div>
<!-- /#wrapper -->
    </jsp:attribute>
</my:pagetemplate>