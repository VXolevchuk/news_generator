<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Woldemar
  Date: 01.05.2021
  Time: 17:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All subjects</title>
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
                            <a class="nav-link active" aria-current="page" href="/">Go to main page</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/findByUserComments">Find by comments</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/findByUserLikes">Find by likes</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/sortByLikes">Sort by likes</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/sortByComments">Sort by comments</a>
                        </li>
                        <li><button type="button" id="delete_subjects" class="btn btn-default navbar-btn">Delete Subjects</button></li>
                        <li><button type="button" id="reset" class="btn btn-default navbar-btn">Delete old subjects</button></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Sources <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <c:forEach items="${sources}" var="source">
                                    <li><a href="/source/${source.id}">${source.name}</a></li>
                                </c:forEach>
                            </ul>
                        </li>
                        <li>
                            <form class="navbar-form" role="search" action="/search/date" method="post">
                                <div class="form-group">
                                    <input type="text" class="form-control" name="month" placeholder="Month">
                                    <input type="text" class="form-control" name="day" placeholder="Day">
                                </div>
                                <button type="submit" class="btn btn-default">Submit</button>
                            </form>
                        </li>
                    </ul>

                    <form class="navbar-form navbar-left" role="search" action="/search" method="post">
                        <div class="form-group">
                            <input type="text" class="form-control" name="pattern" placeholder="Search">
                        </div>
                        <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>

        <table class="table table-striped">
            <c:forEach items="${subjects}" var="subject">
                <tr>
                    <td>${subject.source.name}</td>
                    <td><p><a href="/comment/${subject.id}">${subject.title}</a></p></td>
                    <td>${subject.date}</td>
                    <td>${subject.countComments()}</td>
                    <td>${subject.countLikes()}</td>
                    <c:choose>
                        <c:when test="${admin == true}">
                            <td><input type="checkbox" name="toDelete[]" value="${subject.id}" id="checkbox_${subject.id}"/></td>
                        </c:when>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>

    </div>
    <script>
        $('.dropdown-toggle').dropdown();

        $('#delete_subjects').click(function(){
            var data = { 'toDelete[]' : []};
            $(":checked").each(function() {
                data['toDelete[]'].push($(this).val());
            });
            $.post("/subjects/delete", data, function(data, status) {
                window.location.reload();
            });
        });
    </script>
</body>
</html>
