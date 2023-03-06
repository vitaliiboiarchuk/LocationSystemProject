<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: boyar
  Date: 01/03/2023
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<c:if test="${notAvailable}">
    <h3>User already have all your locations</h3>
</c:if>
<c:if test="${available}">
    <form:form method="post" modelAttribute="user" action="/shareReadOnly">

        <form:hidden path="id"/>
        <form:hidden path="enabled"/>
        <form:hidden path="name"/>
        <form:hidden path="username"/>
        <form:hidden path="password"/>
        <form:hidden path="roles"/>

        <h3>Choose location that you want to share</h3>

        <form:checkboxes path="readOnlyLocations" items="${locations}"
                         itemValue="id" itemLabel="name"/>

        <input type="submit" value="Submit"></input>

    </form:form>
</c:if>
</body>
</html>
