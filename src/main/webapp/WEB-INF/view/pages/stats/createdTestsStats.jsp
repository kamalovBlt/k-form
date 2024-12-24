<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="STATS">
    <div class="created-tests-container">
        <h1 class="created-tests-title">CREATED TESTS STATS</h1>
        <c:forEach var="testStats" items="${CreatedTestStats}">
            <div class="test-block">
                <h2 class="test-title">Test: ${testStats.test.title}</h2>
                <table class="test-results-table">
                    <thead>
                    <tr>
                        <th>User Name</th>
                        <th>User Surname</th>
                        <th>Score</th>
                        <th>Completed At</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="userResult" items="${testStats.usersResult}">
                        <tr>
                            <td>${userResult.user.name}</td>
                            <td>${userResult.user.surname}</td>
                            <td>${userResult.score}</td>
                            <td>${userResult.completedAt}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:forEach>
    </div>
</t:mainLayout>
