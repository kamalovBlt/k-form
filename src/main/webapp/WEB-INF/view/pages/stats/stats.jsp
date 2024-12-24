<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="STATS">
    <div class="stats-container">
        <h1 class="stats-title">YOUR STATS</h1>
        <p class="stats-item">TESTS TAKE: <span class="stats-value">${testsTake}</span></p>
        <p class="stats-item">TESTS CREATED: <span class="stats-value">${testsCreated}</span></p>
    </div>
    <form class="stats-to-create-stats-form" action="${pageContext.request.contextPath}/stats/create" method="get">
        <input class="stats-to-create-stats-input" type="submit" value="CREATED TESTS STATS">
    </form>
    <form class="stats-to-completed-stats-form" action="${pageContext.request.contextPath}/stats/complete" method="get">
        <input class="stats-to-completed-stats-input" type="submit" value="COMPLETED TESTS STATS">
    </form>
</t:mainLayout>
