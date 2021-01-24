<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Login</title>
</head>
<body>
<form method="get">
    <input type="email" name="login" placeholder="login">
    <input type="password" name="password" placeholder="password">
    <input type="submit">
</form>
<a href="${pageContext.request.contextPath}/registration">
    <button>Регистрация</button>
</a>
</body>
</html>
