<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: boyar
  Date: 01/03/2023
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Added locations</h3>
<table class="table">
  <thead>
  <tr>
    <th scope="col">Name</th>
    <th scope="col">Address</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${locations}" var="location">
    <tr>
      <td>${location.name}</td>
      <td>${location.address}</td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>
