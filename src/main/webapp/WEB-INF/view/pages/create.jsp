<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="CREATE">
    <div class="create-page-container" id="create-page-container">
        <div id="first-input-container">
            <div class="create-page-input-container">
                <p>TITLE</p>
                <label for="test-title"></label>
                <input type="text" id="test-title" name="test-title">
            </div>

            <div class="create-page-input-category-container">
                <p>CATEGORY</p>
                <div class="radio-button">
                    <input type="radio" id="test-category-educate" name="test-category" value="educate">
                    <label for="test-category-educate">EDUCATE</label>
                </div>
                <div class="radio-button">
                    <input type="radio" id="test-category-psychology" name="test-category" value="psychology">
                    <label for="test-category-psychology">PSYCHOLOGY</label>
                </div>
                <div class="radio-button">
                    <input type="radio" id="test-category-career" name="test-category" value="career">
                    <label for="test-category-career">CAREER</label>
                </div>
                <div class="radio-button">
                    <input type="radio" id="test-category-fun" name="test-category" value="fun">
                    <label for="test-category-fun">FUN</label>
                </div>
                <div class="radio-button">
                    <input type="radio" id="test-category-other" name="test-category" value="other">
                    <label for="test-category-other">OTHER</label>
                </div>
            </div>

            <div class="create-page-input-container">
                <p>NUMBER OF QUESTIONS</p>
                <label for="test-number-of-questions"></label>
                <input type="number" id="test-number-of-questions" name="test-number-of-questions">
            </div>

            <div class="create-page-submit-container">
                <button type="submit" class="continue-button" onclick="renderSecondInput()">CONTINUE</button>
            </div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/static/js/testInputs.js"></script>
</t:mainLayout>
