function calculate() {
    const testId = document.querySelector('#testId').value;
    let score = 0;

    const questions = document.querySelectorAll('.test-page-question-container');
    questions.forEach(question => {
        const selectedAnswers = question.querySelectorAll('input[type="checkbox"]:checked');
        let isCorrect = true;

        selectedAnswers.forEach(answer => {
            if (answer.dataset.correct !== "true") {
                isCorrect = false;
            }
        });

        const allCorrectAnswers = question.querySelectorAll('input[data-correct="true"]');
        if (isCorrect && selectedAnswers.length === allCorrectAnswers.length) {
            const questionScore = parseInt(question.dataset.score, 10) || 1; // Получаем балл из data-атрибута
            score += questionScore;
        }
    });

    sendToServer(testId, score);
}
function sendToServer(testId, score) {
    const formData = new URLSearchParams();
    formData.append('testId', testId);
    formData.append('score', score);

    console.log(testId)

    fetch('/k-form/testResult', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData.toString(),
    })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            }})
}