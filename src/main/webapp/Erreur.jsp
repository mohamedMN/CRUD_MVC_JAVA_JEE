<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Error</title>
    <style>
        /* Center the content */
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .error {
            text-align: center;
        }
    </style>
</head>
<body>
<div class="error">
    <h1>Error</h1>
    <c:if test="${not empty exception}">
        <h2>${exception.message}<br/></h2>
    </c:if>
</div>
</body>
</html>
