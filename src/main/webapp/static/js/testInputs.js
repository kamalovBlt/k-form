let testCreateData = {};
let askFieldData = new Map();
let asksData = [];
let resultData = [];

function renderSecondInput() {
    const titleInput = document.getElementById("test-title");
    const categoryInputElement = document.querySelector('input[name="test-category"]:checked');
    const numberOfQuestionsInput = document.getElementById("test-number-of-questions");

    if (!titleInput.value.trim()) {
        alert("Please enter a test title.");
        titleInput.style.border = '1px solid red';
        return;
    } else {
        titleInput.style.border = '';
    }

    if (!categoryInputElement) {
        alert("Please select a category.");
        const categoryInputs = document.querySelectorAll('input[name="test-category"]');
        categoryInputs.forEach(input => input.parentElement.style.border = '1px solid red');
        return;
    } else {
        const categoryInputs = document.querySelectorAll('input[name="test-category"]');
        categoryInputs.forEach(input => input.parentElement.style.border = '');
    }

    const categoryInput = categoryInputElement.value;

    if (!numberOfQuestionsInput.value.trim() || isNaN(numberOfQuestionsInput.value) || numberOfQuestionsInput.value <= 0) {
        alert("Please enter a valid number of questions.");
        numberOfQuestionsInput.style.border = '1px solid red';
        return;
    } else {
        numberOfQuestionsInput.style.border = '';
    }

    testCreateData.title = titleInput.value;
    testCreateData.category = categoryInput;
    testCreateData.numberOfQuestions = parseInt(numberOfQuestionsInput.value);

    const mainContainer = document.getElementById("create-page-container");
    const firstInputContainer = document.getElementById("first-input-container");
    const secondInputContainer = document.createElement('div');

    firstInputContainer.remove();

    secondInputContainer.classList.add('description-container');
    secondInputContainer.id = 'second-input-container';
    secondInputContainer.innerHTML = `
        <p>DESCRIPTION</p>
        <textarea id="description-input" placeholder="Enter your description here..."></textarea>
        <button class="continue-button" onclick="renderThirdInput()">CONTINUE</button>
    `;
    mainContainer.appendChild(secondInputContainer);
}



function renderThirdInput() {
    const mainContainer = document.getElementById("create-page-container");
    const secondInputContainer = document.getElementById("second-input-container");
    const thirdInputContainer = document.createElement('div');

    testCreateData.description = document.getElementById("description-input").value;
    secondInputContainer.remove();

    thirdInputContainer.classList.add('create-asks-container-with-button');
    thirdInputContainer.id = 'create-asks-container-with-button';
    thirdInputContainer.innerHTML = `
        <div class="create-asks-container" id="create-asks-container"></div>
        <div class="ask-creating-add-ask-button">
            <button class="ask-creating-create-ask-button" onclick="createAsk(0)">+ ADD ASK</button>
        </div>
        <div class="create-page-submit-container">
            <button class="continue-button" onclick="collectAllAsksAngRenderFourthInput()">CONTINUE</button>
        </div>
    `;
    mainContainer.appendChild(thirdInputContainer);

    for (let i = 1; i <= testCreateData.numberOfQuestions; i++) {
        createAsk(i);
    }
}

function calculateMaxScore() {
    let sum = 0;
    asksData.forEach((ask) => {
        const askScore = parseInt(ask.askScore, 10);
        sum += (isNaN(askScore) ? 0 : askScore);
    });
    return sum;
}


function renderFourthInput() {
    const mainContainer = document.getElementById("create-page-container");
    const thirdInputContainer = document.getElementById("create-asks-container-with-button");
    thirdInputContainer.remove();

    const maxScore = calculateMaxScore();

    const fourthInputContainer = document.createElement('div');
    fourthInputContainer.classList.add('results-container');
    fourthInputContainer.id = 'results-container';

    fourthInputContainer.innerHTML = `
        <div class="test-result-creating">
            <p>DEFINE TEST RESULTS</p>
            <p>Maximum Score: ${maxScore}</p>
        </div>
        <div id="results-list"></div>
        <button class="add-result-button" onclick="addResultRange(${maxScore})">+ ADD RESULT RANGE</button>
        <button class="continue-button" onclick="submitTest(${maxScore})">FINISH TEST</button>
    `;

    mainContainer.appendChild(fourthInputContainer);

    addResultRange(maxScore);
}

function addResultRange(maxScore) {
    const resultsList = document.getElementById('results-list');
    const resultId = `result-${resultsList.children.length}`;

    const resultRange = document.createElement('div');
    resultRange.classList.add('result-range');
    resultRange.id = resultId;

    resultRange.innerHTML = `
        <div class="result-range-inputs">
            <label for="${resultId}-min">Min:</label>
            <input type="number" id="${resultId}-min" value="${resultsList.children.length === 0 ? 0 : ''}" min="0" readonly />

            <label for="${resultId}-max">Max:</label>
            <input type="number" id="${resultId}-max" min="0" max="${maxScore}" placeholder="Max Score" />
        </div>
        <div class="result-description">
            <label for="${resultId}-description">Description:</label>
            <textarea id="${resultId}-description" placeholder="Enter result description"></textarea>
        </div>
        <button class="remove-result-button" onclick="removeResultRange('${resultId}')">Remove</button>
    `;

    resultsList.appendChild(resultRange);

    updateRangeInputs();
}

function removeResultRange(resultId) {
    const resultRange = document.getElementById(resultId);
    if (resultRange) {
        resultRange.remove();
        updateRangeInputs(); // Обновляем вводы диапазонов
    }
}

function updateRangeInputs() {
    const resultsList = document.getElementById('results-list');
    const ranges = Array.from(resultsList.children);

    ranges.forEach((range, index) => {
        const minInput = range.querySelector('input[id$="-min"]');
        const maxInput = range.querySelector('input[id$="-max"]');

        if (index === 0) {
            minInput.value = 0;
        } else {
            const prevMax = ranges[index - 1].querySelector('input[id$="-max"]');
            minInput.value = prevMax ? parseInt(prevMax.value, 10) + 1 : 0;
        }

        minInput.readOnly = true;
    });
}

function submitTest(maxScore) {
    const resultsList = document.getElementById('results-list');
    const resultRanges = [];
    let isValid = true;

    Array.from(resultsList.children).forEach(resultElement => {
        const minInput = resultElement.querySelector('input[id$="-min"]');
        const maxInput = resultElement.querySelector('input[id$="-max"]');
        const descriptionInput = resultElement.querySelector('textarea[id$="-description"]');

        const minScore = parseInt(minInput.value, 10);
        const maxScoreValue = parseInt(maxInput.value, 10);

        if (isNaN(maxScoreValue) || maxScoreValue < minScore || maxScoreValue > maxScore) {
            isValid = false;
            maxInput.style.border = '1px solid red';
        } else {
            maxInput.style.border = '';
        }

        const description = descriptionInput.value.trim();
        if (!description) {
            isValid = false;
            descriptionInput.style.border = '1px solid red';
        } else {
            descriptionInput.style.border = '';
        }

        resultRanges.push({
            min: minScore,
            max: maxScoreValue,
            description,
        });
    });

    if (!isValid) {
        alert('Please fix the errors in the result ranges.');
        return;
    }

    resultData = resultRanges;
    submitTestToServer();
}


function createAsk(num) {
    if (num === 0) {
        num = Math.max(0, ...askFieldData.keys()) + 1;
    }
    askFieldData.set(num, []);
    const ask = document.createElement("div");
    ask.classList.add("ask-creating");
    ask.id = `ask-creating-${num}`;
    ask.innerHTML = `
        <div class="ask-creating-header">
            <div class="ask-creating-value">
                <label for="ask-creating-${num}-input"></label>
                <textarea id="ask-creating-${num}-input" name="ask-creating-${num}-input"></textarea>
            </div>
            <div class="ask-creating-ask-ball">
                <input type="number" id="ask-creating-ask-${num}-ball" min="1" oninput="if(this.value < 1) this.value = 1;">
            </div>
            <div class="ask-creating-ask-remove-div">
            <button class="ask-creating-ask-remove-button" onclick="removeAsk(${num})">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#ff6b6b" width="24px" height="24px">
                    <path d="M3 6h18v2H3zM9 9h2v10H9zm4 0h2v10h-2z" />
                </svg>
            </button>
        </div>
        </div>
        <div class="ask-creating-ask-field-container" id="ask-creating-ask-${num}-field-container"></div>
        <div class="ask-creating-ask-add-field">
            <button class="ask-creating-ask-add-field-button" onclick="addFieldToAsk(${num})">+ ADD FIELD</button>
        </div>
    `;
    document.getElementById('create-asks-container').appendChild(ask);
}


function addFieldToAsk(askNum) {
    const fieldNums = askFieldData.get(askNum);
    const fieldNum = (fieldNums.length > 0 ? Math.max(...fieldNums) : 0) + 1;

    fieldNums.push(fieldNum);
    askFieldData.set(askNum, fieldNums);

    const field = document.createElement("div");
    field.classList.add("ask-creating-field");
    field.id = `ask-${askNum}-creating-field-${fieldNum}`;
    field.innerHTML = `
        <div class="ask-creating-field-is-correct">
            <label for="ask-${askNum}-creating-field-${fieldNum}-is-correct"></label>
            <input type="checkbox" id="ask-${askNum}-creating-field-${fieldNum}-is-correct" name="ask-${askNum}-creating-field-${fieldNum}-is-correct">
        </div>
        <div class="ask-creating-field-content-div">
            <label></label>
            <textarea id="ask-${askNum}-creating-field-${fieldNum}-content" name="ask-${askNum}-creating-field-${fieldNum}-content"></textarea>
        </div>
        <div class="ask-creating-field-remove-div">
            <button class="ask-creating-field-remove-button" onclick="removeField(${askNum}, ${fieldNum})">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#ff6b6b" width="24px" height="24px">
                    <path d="M3 6h18v2H3zM9 9h2v10H9zm4 0h2v10h-2z" />
                </svg>
            </button>
        </div>
    `;
    document.getElementById(`ask-creating-ask-${askNum}-field-container`).appendChild(field);
}

function removeField(askNum, fieldNum) {
    document.getElementById(`ask-${askNum}-creating-field-${fieldNum}`).remove();
    const fieldNums = askFieldData.get(askNum);
    askFieldData.set(askNum, fieldNums.filter(num => num !== fieldNum));
}

function removeAsk(askNum) {
    const askElement = document.getElementById(`ask-creating-${askNum}`);
    if (askElement) {
        askElement.remove(); // Удаляем вопрос из DOM
        askFieldData.delete(askNum); // Удаляем вопрос из Map
    }
}


function collectAllAsksAngRenderFourthInput() {
    const asks = [];
    let isValid = true;

    askFieldData.forEach((fields, askNum) => {
        const askTextElement = document.getElementById(`ask-creating-${askNum}-input`);
        const askScoreElement = document.getElementById(`ask-creating-ask-${askNum}-ball`);

        if (!askTextElement || !askScoreElement) {
            console.warn(`Ask or score input for askNum ${askNum} is missing.`);
            isValid = false;
            return;
        }

        const askText = askTextElement.value.trim();
        const askScore = askScoreElement.value.trim();

        if (!askText || !askScore) {
            isValid = false;
            askTextElement.style.border = '1px solid red';
            askScoreElement.style.border = '1px solid red';
            console.warn(`Ask text or score for askNum ${askNum} is blank.`);
            return;
        } else {
            askTextElement.style.border = ''; // Сброс рамки
            askScoreElement.style.border = ''; // Сброс рамки
        }

        let hasCorrectField = false;

        const askFields = fields.map(fieldNum => {
            const fieldContentElement = document.getElementById(`ask-${askNum}-creating-field-${fieldNum}-content`);
            const isCorrectElement = document.getElementById(`ask-${askNum}-creating-field-${fieldNum}-is-correct`);

            if (!fieldContentElement || !isCorrectElement) {
                console.warn(`Field content or checkbox for askNum ${askNum}, fieldNum ${fieldNum} is missing.`);
                isValid = false;
                return null;
            }

            const fieldContent = fieldContentElement.value.trim();
            if (!fieldContent) {
                isValid = false;
                fieldContentElement.style.border = '1px solid red';
                console.warn(`Field content for askNum ${askNum}, fieldNum ${fieldNum} is blank.`);
                return null;
            } else {
                fieldContentElement.style.border = ''; // Сброс рамки
            }

            if (isCorrectElement.checked) {
                hasCorrectField = true;
            }

            return {
                content: fieldContent,
                isCorrect: isCorrectElement.checked,
            };
        }).filter(field => field !== null);

        if (askFields.length === 0) {
            console.warn(`AskNum ${askNum} has no valid fields.`);
            isValid = false;
            return;
        }

        if (!hasCorrectField) {
            alert(`At least one correct answer must be selected for question ${askNum}.`);
            isValid = false;
            return;
        }

        asks.push({
            askNumber: askNum,
            askScore,
            question: askText,
            fields: askFields,
        });
    });

    if (!isValid) {
        alert('Please fill out all required fields.');
        return null;
    }

    asksData = asks;
    renderFourthInput();
}

function submitTestToServer() {
    const TestDTO = {
        title: testCreateData.title,
        description: testCreateData.description,
        category: testCreateData.category,
        maxBall: calculateMaxScore(),
    };

    const testResults = resultData.map(result => ({
        minScore: result.min,
        maxScore: result.max,
        description: result.description,
    }));

    const questions = asksData.map(ask => ({
        number: ask.askNumber,
        text: ask.question,
        score: parseInt(ask.askScore),
        answers: ask.fields.map(field => ({
            text: field.content,
            correct: field.isCorrect,
        })),
    }));

    const data = {
        ...TestDTO,
        testResults,
        questions,
    };

    fetch('/k-form/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    }).then(response => {
        if (response.redirected) {
            window.location.href = response.url;
        }
    });
}

