<h1>Your profile is ${userName}</h1>
<form action="post" method="${contextPath}/users?action=delete&user_id=${userId}">
    <input type="hidden" name="_csrf_token" value="${csrfToken}">
    <input type="submit" value="Delete">
</form>