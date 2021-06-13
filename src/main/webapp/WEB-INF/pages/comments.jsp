<%--
  Created by IntelliJ IDEA.
  User: Woldemar
  Date: 22.04.2021
  Time: 18:15
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
    <a class="nav-link active" aria-current="page" href="/">Back</a>
    <div class="container">
        <div class="card" >
                <c:choose>
                    <c:when test="${subject.pictureUrl ne null}">
                        <img src="${subject.pictureUrl}"  class="card-img-top" alt="Subject picture" >
                    </c:when>
                </c:choose>
                <a class="card-body">
                    <a href=${subject.url}>
                    <h5 class="card-title">${subject.source.name}</h5>
                    </a>
                    <p class="card-text">${subject.title}</p>
                </a>
        </div>
        <form role="form" class="form-horizontal" action="/addComment/${subjectId}" method="post">
            <input class="form-control form-group" type="text" name="text" placeholder="text">
            <input type="submit" class="btn btn-primary" value="AddComment">

        </form>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">User</th>
            <th scope="col">Text</th>
            <th scope="col">Date</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <c:forEach items="${comments}" var="comment">
              <td>${comment.customUser.login}</td>
              <td>${comment.text}</td>
              <td>${comment.date}</td>
              <c:choose>
                  <c:when test="${moder == true}">
                      <td><a href ="/report/${comment.customUser.login}">
                          <button type="button"  class="btn btn-primary" >ReportUser</button>
                      </a>
                      </td>
                  </c:when>
                  <c:when test="${admin == true}">
                      <td><a href ="/report/${comment.customUser.login}/${comment.id}/${subjectId}">
                          <button type="button"  class="btn btn-primary" >ReportUser</button>
                      </a>
                      </td>
                  </c:when>
              </c:choose>
              <c:choose>
                  <c:when test="${moder == true}">
                      <td><a href ="/comment/pseudoDelete/${comment.id}"/${subjectId}>
                          <button type="button"  class="btn btn-primary" >DeleteComment</button>
                      </a>
                      </td>
                  </c:when>
                  <c:when test="${admin == true}">
                      <td><a href ="/comment/pseudoDelete/${comment.id}/${subjectId}">
                          <button type="button"  class="btn btn-primary" >DeleteComment</button>
                      </a>
                      </td>
                  </c:when>
                  <c:when test="${userName eq comment.customUser.login}">
                      <td><a href ="/comment/pseudoDelete/${comment.id}/${subjectId}">
                          <button type="button"  class="btn btn-primary" >DeleteComment</button>
                      </a>
                      </td>
                  </c:when>
              </c:choose>

        </tr>
          </c:forEach>

        </tbody>
    </table>
    </div>
</body>
</html>
