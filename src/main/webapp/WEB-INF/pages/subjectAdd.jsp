<%--
  Created by IntelliJ IDEA.
  User: Woldemar
  Date: 15.05.2021
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form role="form" class="form-horizontal" action="/subject/add" method="post">
        <h3>New custom subject</h3>
        <input class="form-control form-group" type="text" name="sourceName" placeholder="SourceName">
        <input class="form-control form-group" type="text" name="sourceUrl" placeholder="Source Url">
        <input class="form-control form-group" type="text" name="titleSelector" placeholder="Title Selector">
        <input class="form-control form-group" type="text" name="dateSelector" placeholder="Date Selector">
        <input class="form-control form-group" type="text" name="pictureUrlSelector" placeholder="Picture Selector">
        <input type="submit" class="btn btn-primary" value="Add">
    </form>
</div>
</body>
</html>
