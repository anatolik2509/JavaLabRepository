<%@tag description="basic layout" pageEncoding="UTF-16" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="title" required="true" %>
<html>
    <head>
        <title>${title}</title>
        <t:head/>
        <t:bgImage url="/res/bg.png" fragment_class="body"/>
    </head>
    <body class="body">
        <t:header/>
        <jsp:doBody/>
    </body>
</html>