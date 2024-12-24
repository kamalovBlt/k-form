<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="TESTS">
    <form class="search-form" action="${pageContext.request.contextPath}/tests" method="get">
        <label class="search-label">
            <input class="search-input" type="text" name="search" placeholder="Enter text...">
        </label>
        <input class="search-button" type="submit" value="SEARCH">
    </form>

    <div class="tests-container">
        <c:forEach var="test" items="${foundedTests}">
            <div class="test-card">
                <h3 class="test-title">${test.title}</h3>
                <p class="test-description">${test.description}</p>
                <a class="test-link" href="${pageContext.request.contextPath}/test?id=${test.id}">START TEST</a>
            </div>
        </c:forEach>
    </div>
</t:mainLayout>
