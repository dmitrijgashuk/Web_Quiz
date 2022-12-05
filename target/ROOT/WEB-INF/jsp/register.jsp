<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Casper
  Date: 21.10.2022
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register Form</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet"/>
</head>
<body>
<form  action="${pageContext.request.contextPath}/authorization" method="post" >

    <div>
        <label>Укажите имя игрока:</label>
        <div ><input type="text" name="name"/></div>
    </div>

    <div>
        <button type="submit">Начать игру!</button>
    </div>
    <c:out value="${errorMessage}"/>
</form>
</body>
</html>
