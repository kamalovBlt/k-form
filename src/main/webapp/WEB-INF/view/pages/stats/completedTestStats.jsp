<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="STATS">
    <div class="completed-tests-container">
        <h1 class="completed-tests-title">COMPLETED TESTS STATS</h1>
        <c:choose>
            <c:when test="${not empty CompletedTestStats}">
                <table class="completed-tests-table">
                    <thead>
                    <tr>
                        <th>Test Title</th>
                        <th>Category</th>
                        <th>Max Ball</th>
                        <th>Result</th>
                        <th>Completed At</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="stat" items="${CompletedTestStats}">
                        <tr>
                            <td>${stat.test.title}</td>
                            <td>${stat.test.category}</td>
                            <td>${stat.test.maxBall}</td>
                            <td>${stat.result}</td>
                            <td>${stat.completedAt}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p class="no-tests-message">No completed tests to display.</p>
            </c:otherwise>
        </c:choose>
    </div>
</t:mainLayout>
