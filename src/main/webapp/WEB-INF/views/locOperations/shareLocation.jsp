<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: boyar
  Date: 01/03/2023
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Chose with whom you want to share with</h3>

<table class="table">
  <thead>
  <tr>
    <th scope="col">Name</th>
    <th scope="col">Email</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${users}" var="user">
    <tr>
      <th scope="row">${user.name}</th>
      <td>${user.username}</td>
      <td>
        <a href="<c:url value="/shareReadOnly/${user.id}/"/>">Share read only</a>
        <a href="<c:url value="/shareAdmin/${user.id}/"/>">Share admin</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>
