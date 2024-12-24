<%@tag description="Default Layout Tag" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="title" required="false" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>${title}</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Alumni+Sans&amp;display=swap">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/nav.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/styles.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/create.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tests.css" type="text/css">
</head>
<body>
<jsp:include page="/WEB-INF/view/parts/nav.jsp"/>
<div class="container">
    <jsp:doBody/>
</div>
</body>
</html>
