<%--
  Created by IntelliJ IDEA.
  User: Woldemar
  Date: 14.04.2021
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
       <title>My News Generator</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
    </head>
<body>
<div class="container">
    <h3><img height="50" width="55" src="<c:url value="/static/AdmiralAkainu.png"/>"/><a href="/">My News Generator</a></h3>

    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Navbar</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/admin">Admin</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/userPage">User page</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/subjects">All subjects</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout">Logout</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/addSubject">Add own subject</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <section>
        <div class="container">
            <h2>Recent news</h2>
            <div class="row">
            <div class="col-8">
                <c:forEach items="${subjects}" var="subject">

                    <div class="card" >
                        <c:choose>
                            <c:when test="${subject.pictureUrl ne null}">
                                <img src="${subject.pictureUrl}"  class="card-img-top" alt="Subject picture" >
                            </c:when>
                        </c:choose>
                    <div class="card-body">
                    <h5 class="card-title">${subject.source.name}</h5>
                    <a href=${subject.url}>
                    <p class="card-text">${subject.title}</p>
                    </a>
                    <a href ="/comments/${subject.id}">
                        <button type="button"  class="btn btn-primary" >Comments - ${subject.countComments()}</button>
                    </a>
                    <a href="/addLike/${subject.id}">
                        <button type="button"  class="btn btn-primary" >Likes - ${subject.countLikes()}</button>
                    </a>
                    </div>
                    </div>
                </c:forEach>
            </div>

                <div class="col-4" >
                    <c:forEach items="${products}" var="product">
                        <div class="card" >
                            <a href=${product.url}>
                                <img src="${product.pictureUrl}" class="card-img-top" alt="Subject picture" >
                            </a>
                        <div class="card-body">
                            <h5 class="card-title">${product.seller.name}</h5>
                            <p class="card-text">${product.name}</p>
                            <a href ="/comment/${product.id}">
                                <button type="button"  class="btn btn-primary" >Comments</button>
                            </a>
                            <a href="" type="button" class="btn btn-primary">
                                Notifications <span class="badge bg-secondary"></span>
                            </a>
                        </div>
                      </div>
                    </c:forEach>
                </div>

            </div>

            </div>

    </section>
</div>

</body>
</html>
