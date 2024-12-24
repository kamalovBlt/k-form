<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:mainLayout title="TEST RESULT">
    <div class="result-container">
        <h1 class="result-title">Test Result</h1>
        <p class="result-item">Score: <span>${score}</span></p>
        <p class="result-item">Result: <span>${description}</span></p>
        <p class="result-item">Completed at: <span>${completedAt}</span></p>
    </div>
</t:mainLayout>
