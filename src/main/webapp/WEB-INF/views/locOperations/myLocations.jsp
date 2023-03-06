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
      <td><a href="<c:url value="/showFriends/${location.id}/"/>">Show friends</a></td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<h3>Shared locations from friends</h3>
<table class="table">
  <thead>
  <tr>
    <th scope="col">Name</th>
    <th scope="col">Address</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${readOnlyLocations}" var="readOnlyLocation">
    <tr>
      <td>${readOnlyLocation.name}</td>
      <td>${readOnlyLocation.address}</td>
      <td>(Read only access)</td>
    </tr>
  </c:forEach>
  <c:forEach items="${adminLocations}" var="adminLocation">
    <tr>
      <td>${adminLocation.name}</td>
      <td>${adminLocation.address}</td>
      <td>(Admin access)</td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<br>
<a href="/shareLocation">Share location</a></p>
</body>
</html>
