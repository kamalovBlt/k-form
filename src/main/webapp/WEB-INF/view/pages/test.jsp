<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="TEST">
    <div class="test-page-form-container">
        <input type="hidden" id="testId" value="${testId}">

        <h1 class="test-page-title">${test.title}</h1>
        <p class="test-page-description">${test.description}</p>
        <c:forEach var="question" items="${test.questions}">
            <div class="test-page-question-container" data-score="${question.score}">
                <div class="test-page-question-header">
                    <span class="test-page-question-number">${question.number}</span>
                    <span class="test-page-question-text">${question.text}</span>
                </div>
                <ul class="test-page-answer-list">
                    <c:forEach var="answer" items="${question.answers}">
                        <li>
                            <label>
                                <input
                                        type="checkbox"
                                        name="question_${question.number}"
                                        value="${answer.text}"
                                        data-correct="${answer.correct}"
                                />
                                    ${answer.text}
                            </label>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:forEach>
        <div class="test-page-submit-button-container">
            <button type="button" class="test-page-submit-button" onclick="calculate()">SEND</button>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/static/js/testCalculateResult.js"></script>
</t:mainLayout>

