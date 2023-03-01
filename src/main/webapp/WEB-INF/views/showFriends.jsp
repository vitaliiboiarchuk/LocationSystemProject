<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: boyar
  Date: 01/03/2023
  Time: 19:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Friends on location</h3>
<table class="table">
  <thead>
  <tr>
    <th scope="col">Name</th>
    <th scope="col">Email</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${readOnlyUsers}" var="user">
    <tr>
      <td>${user.name}</td>
      <td>${user.username}</td>
      <td>(Read only access)</td>
      <td><a href="<c:url value="/changeAccess/${location.id}/${user.id}/"/>">Change access</a></td>
    </tr>
  </c:forEach>
  <c:forEach items="${adminUsers}" var="user">
    <tr>
      <td>${user.name}</td>
      <td>${user.username}</td>
      <td>(Admin access)</td>
      <td><a href="<c:url value="/changeAccess/${location.id}/${user.id}/"/>">Change access</a></td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>
