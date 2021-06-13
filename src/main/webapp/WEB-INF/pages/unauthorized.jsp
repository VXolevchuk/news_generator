<%--
  Created by IntelliJ IDEA.
  User: Woldemar
  Date: 21.04.2021
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>My News Generator</title>
</head>
<body>
<div align="center">
    <h1>Access denied for ${login}!</h1>

    <c:url value="/logout" var="logoutUrl" />
    <p>Click to logout: <a href="${logoutUrl}">LOGOUT</a></p>
</div>
</body>
</html>