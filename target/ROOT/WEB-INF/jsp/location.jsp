<%--
  Created by IntelliJ IDEA.
  User: dmytro
  Date: 19/11/2022
  Time: 12:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet"/>
    <title>Game GUI</title>
</head>
<body>
<H1>Location: ${user.currentLocation}</H1>

<h6>User name: <c:out value="${user.name}"/></h6>

<c:if test="${availableQuests.size() > 0}">
    <h6>Current quests: </h6>
    <ul>
        <c:forEach items="${availableQuests}" var="qestAvailable">
            <li>
                    ${qestAvailable.questText}
            </li>
        </c:forEach>
    </ul>
</c:if>
<br/>
<c:if test="${finishQuests.size() > 0}">
    <h6>Finish quests: </h6>
    <ul>
        <c:forEach items="${finishQuests}" var="qest">
            <li>
                    ${qest.questText}
            </li>
        </c:forEach>
    </ul>
</c:if>
<br/>
<h6>Items in the box: </h6>
<ul>
    <c:forEach items="${user.items}" var="object">
        <li>
            ${object.itemName}
        </li>
    </c:forEach>
</ul>
<hr/>
<br/>
<h6>Go to: </h6>
<ul>
    <c:forEach items="${availableLocation}" var="item">
        <li>
            <form action="${pageContext.request.contextPath}/location" method="post">
                <input type="hidden" name="nextLocation" value="${item.title}">
                <button type="submit">${item.title}</button>
            </form>
        </li>
    </c:forEach>
</ul>
<h6>NPC list:</h6><br>
<ul>
    <c:forEach items="${npc}" var="npcName">
        <li>
            <form action="${pageContext.request.contextPath}/dialog" method="post">
                <input type="hidden" name="npcName" value="${npcName}">
                <button type="submit">${npcName}</button>
            </form>
        </li>
    </c:forEach>
</ul>
<h6>Item List on the Location ${user.currentLocation}:</h6>
<ul>
    <c:forEach items="${items}" var="item">
        <li>
            <form action="${pageContext.request.contextPath}/location" method="post">
                <input type="hidden" name="itemId" value="${item.id}">
                <button type="submit">${item.itemName}</button>
            </form>
        </li>
    </c:forEach>
</ul>
</body>
</html>
