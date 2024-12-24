<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="PROFILE">
    <p class="user-info-title">PROFILE</p>
    <div class="user-info">
        <p class="user-info-item">Name: ${name}</p>
        <p class="user-info-item">Surname: ${surname}</p>
        <p class="user-info-item">Email: ${email}</p>
    </div>
    <form class="logout-form" action="${pageContext.request.contextPath}/logout" method="get">
        <button type="submit" class="logout-button">LOGOUT</button>
    </form>
</t:mainLayout>
