<%@tag description="header" pageEncoding="UTF-16" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<header>
    <div class="header-left">
        <h1 class="logo">Waterfall</h1>
    </div>
    <div class="header-right">
        <div class="header-menu">
        <a href="<c:url value="/feed"/>" class="menu-btn underline-on-hover">Статьи</a>
        <%--@elvariable id="auth" type="java.lang.Boolean"--%>
        <c:if test="${auth eq false or auth eq null}">
            <a href="<c:url value="/login"/>" class="menu-btn underline-on-hover">Войти</a>
        </c:if>
        <c:if test="${auth eq true}">
            <%--<a href="<c:url value="/userFeed"/>" class="menu-btn underline-on-hover">Ваша лента</a>--%>
            <a href="<c:url value="/profile"/>" class="menu-btn underline-on-hover">Профиль</a>
            <a href="<c:url value="/edit"/>" class="menu-btn underline-on-hover">Создать статью</a>
            <a href="<c:url value="/saved"/>" class="menu-btn underline-on-hover">Сохранённое</a>
            <a href="<c:url value="/userArticles"/>" class="menu-btn underline-on-hover">Ваши статьи</a>
        </c:if>
        </div>
        <div class="header-dropdown">
            <div class="dropdown">
                <a data-toggle="dropdown">
                    <img class="menu-img" src="<c:url value="/res/menu.png"/>" alt="menu">
                </a>
                <div class="dropdown-menu">
                    <a href="<c:url value="/feed"/>" class="dropdown-item">Статьи</a>
                    <%--@elvariable id="auth" type="java.lang.Boolean"--%>
                    <c:if test="${auth eq false or auth eq null}">
                        <a href="<c:url value="/login"/>" class="dropdown-item">Войти</a>
                    </c:if>
                    <c:if test="${auth eq true}">
                        <%--<a href="<c:url value="/userFeed"/>" class="dropdown-item">Ваша лента</a>--%>
                        <a href="<c:url value="/profile"/>" class="dropdown-item">Профиль</a>
                        <a href="<c:url value="/edit"/>" class="dropdown-item">Создать статью</a>
                        <a href="<c:url value="/saved"/>" class="dropdown-item">Сохранённое</a>
                        <a href="<c:url value="/userArticles"/>" class="dropdown-item">Ваши статьи</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</header>