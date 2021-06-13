<%--
  Created by IntelliJ IDEA.
  User: Woldemar
  Date: 21.04.2021
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>My News Generator</title>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous"></script>
</head>
<body>
<div align="center">
    <h1>Admin page</h1>

    <p>Click to go back: <a href="/">back</a></p>

    <c:url value="/logout" var="logoutUrl" />
    <p>Click to logout: <a href="${logoutUrl}">LOGOUT</a></p>

    <button type="button" id="delete_user">Delete</button>

    <table border="1">
        <c:forEach items="${users}" var="user">
            <tr>
                <td><input type="checkbox" name="toDelete" value="${user.id}" id="check_${user.id}"></td>
                <td><c:out value="${user.login}"/></td>
                <td><c:out value="${user.role}"/></td>
                <td><c:out value="${user.blocked}"/></td>
                <td><a href ="/setModer/${user.login}">
                <button type="button" >Set Moder</button>
                </a>
                </td>
                <td><a href ="/block/${user.login}">
                    <button type="button" >Block User</button>
                </a>
                </td>
                <td><a href ="/unblock/${user.login}">
                    <button type="button" >Unblock User</button>
                </a>
                </td>

            </tr>
        </c:forEach>
    </table>
    <p>Click to see all comments: <a href="/allComments">Comments</a></p>
    <p>Click to see custom subjects: <a href="/customSubjects">Custom Subjects</a></p>

</div>

<script>
    $('#delete_user').click(function(){
        var data = { 'toDelete' : []};
        $(":checked").each(function() {
            data['toDelete'].push($(this).val());
        });
        $.post("/delete", data, function(data, status) {
            window.location.reload();
        });
    });
</script>

</body>
</html>