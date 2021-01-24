<%@tag description="basic layout" pageEncoding="UTF-16" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="feedType" required="true" %>
<jsp:useBean id="now" class="java.util.Date" scope="page"/>
<script>
    updated = ${now.time};
    scrollController("${pageContext.request.contextPath}" + "/feed?feedType=" + "${feedType}")
</script>