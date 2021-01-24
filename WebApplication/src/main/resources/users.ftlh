<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" href="${contextPath}/css/style.css">
</head>
<body>
<table class="center">
    <tr>
        <th>First name</th>
        <th>Last name</th>
        <th>Age</th>
    </tr>
    <#list users as listUser>
        <tr>
            <td>${listUser.firstName}</td>
            <td>${listUser.lastName}</td>
            <td>
                <#if listUser.age?? && listUser.age gt 0 >
                    ${listUser.age}
                <#else>
                    Не знаем(
                </#if>
            </td>
        </tr>
    </#list>
</table>


</body>
</html>