<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Sign Up</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Alumni+Sans&amp;display=swap">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/auth.css" type="text/css">
</head>
<body>

<a class="navbar-brand" href="${pageContext.request.contextPath}">
    <img src="${pageContext.request.contextPath}/static/img/k-form-logotype.png"
         style="position: absolute;top:5px;left:5px;width: 110px;height: 40px;" alt="K-FORM">
</a>

<div class="form-container">
    <h2>SIGN UP</h2>
    <form action="${pageContext.request.contextPath}/signUp" method="POST">

        <div class="form-group">
            <label for="first-name">FIRST NAME</label>
            <input type="text" id="first-name" name="first-name" required>
        </div>

        <div class="form-group">
            <label for="last-name">LAST NAME</label>
            <input type="text" id="last-name" name="last-name" required>
        </div>

        <div class="form-group">
            <label for="email">EMAIL</label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="password">PASSWORD</label>
            <input type="password" id="password" name="password" required>
        </div>

        <div class="form-group">
            <input type="submit" value="SUBMIT">
        </div>

        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

    </form>
    <div class="links">
        <p>Already have an account? <a href="${pageContext.request.contextPath}/signIn">Sign In</a></p>
    </div>
</div>

</body>
</html>
