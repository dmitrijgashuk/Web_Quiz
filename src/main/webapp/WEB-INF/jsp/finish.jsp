<%--
  Created by IntelliJ IDEA.
  User: dmytro
  Date: 04/12/2022
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet"/>
    <title>Finish game</title>
</head>
<body>
<h2>Поздравляем!! ${user.name} вы прошли квест!</h2>
<h2>Игру пройдено ${user.gameCount} раз!</h2>
<form  action="${pageContext.request.contextPath}/index.jsp" method="post" >
    <div>
        <button type="submit">Начать игру заново!</button>
    </div>
</form>
</body>
</html>
