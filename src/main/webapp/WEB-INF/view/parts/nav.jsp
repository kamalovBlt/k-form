<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar">
    <a class="navbar-brand" href="${pageContext.request.contextPath}">
        <img src="${pageContext.request.contextPath}/static/img/k-form-logotype.png"
             style="width: 110px; height: 40px" alt="K-FORM">
    </a>
    <div class="navbar-links">
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/create">CREATE</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/tests">TESTS</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/stats">STATS</a></li>
    </div>
    <div style="width: 70px;"></div>
    <a href="${pageContext.request.contextPath}/profile"><img src="${pageContext.request.contextPath}/static/img/lkicon.png"
                     style="width: 40px;height: 40px;" alt="PROFILE"></a>
</nav>
<hr>
