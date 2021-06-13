<%--
  Created by IntelliJ IDEA.
  User: Woldemar
  Date: 16.05.2021
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</head>
<body>
    <div class="container">
        <div class="col">
            <c:forEach items="${subjects}" var="subject">

                <div class="card" >
                    <c:choose>
                        <c:when test="${subject.pictureUrl ne null}">
                            <img src="${subject.pictureUrl}"  class="card-img-top" alt="Subject picture" >
                        </c:when>
                    </c:choose>
                    <div class="card-body">
                        <h5 class="card-title">${subject.source}</h5>
                        <a href=${subject.url}>
                            <p class="card-text">${subject.title}</p>
                        </a>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">${subject.date}</li>
                            <li class="list-group-item">${subject.creatorLogin}</li>
                            <li class="list-group-item">A third item</li>
                        </ul>
                        <div class="card-body">
                            <a href="/acceptSubject/${subject.id}" class="card-link">Accept</a>
                            <a href="/declineSubject/${subject.id}" class="card-link">Decline</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
