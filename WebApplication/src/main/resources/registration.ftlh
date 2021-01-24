<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="${contextPath}/css/style.css">
</head>
<body>
<form class="login-form" method="post">
    <input type="hidden" name="_csrf_token" value="${csrfToken}">
    <input type="email" name="login" placeholder="login">
    <input type="password" name="password" placeholder="password">
    <input type="password" name="password_repeat" placeholder="repeat password">
    <input type="submit">
</form>
</body>
</html>