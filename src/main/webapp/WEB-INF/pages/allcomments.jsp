<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Woldemar
  Date: 03.05.2021
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All comments</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul id="groupList" class="nav navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/admin">Back</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/viewPseudoDeleted">PseudoDeleted</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/findByUserLikes">Find by likes</a>
                    </li>
                    <li><button type="button" id="delete_comments" class="btn btn-default navbar-btn">Delete Comments</button></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Find by User <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <c:forEach items="${users}" var="user">
                                <li><a href="/commentsBy/${user.login}">${user.login}</a></li>
                            </c:forEach>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Find by Subject <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <c:forEach items="${subjects}" var="subject">
                                <li><a href="/allComments/${subject.id}">${subject.title}</a></li>
                            </c:forEach>
                        </ul>
                    </li>
                    <li>
                        <form class="navbar-form" role="search" action="/searchComments/date" method="post">
                            <div class="form-group">
                                <input type="text" class="form-control" name="month" placeholder="Month">
                                <input type="text" class="form-control" name="day" placeholder="Day">
                            </div>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

    <table class="table table-striped">
        <c:forEach items="${comments}" var="comment">
            <tr>
                <td>${comment.customUser.login}</td>
                <td>${comment.subject.title}</td>
                <td>${comment.text}</td>
                <td>${comment.date}</td>
                <c:choose>
                <c:when test="${comment.pseudoDeleted == true}">
                    <td>Pseudo deleted</td>
                </c:when>
                </c:choose>
                <td><input type="checkbox" name="toDelete[]" value="${comment.id}" id="checkbox_${comment.id}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
    <script>
        $('.dropdown-toggle').dropdown();

        $('#delete_comments').click(function(){
            var data = { 'toDelete[]' : []};
            $(":checked").each(function() {
                data['toDelete[]'].push($(this).val());
            });
            $.post("/comments/delete", data, function(data, status) {
                window.location.reload();
            });
        });
    </script>
</body>
</html>
