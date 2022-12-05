<%--
  Created by IntelliJ IDEA.
  User: dmytro
  Date: 23/11/2022
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet"/>
    <title>Dialog with ${currentNps.npsName}</title>
</head>
<body>

<ul>
    <li>
        <h6>Npc: ${currentNpc.npcName} says -</h6>
    </li>
</ul>

<h6>${dialog.text}</h6>
<br/>
<h6>NPC answer list:</h6><br>

<ul>
    <c:forEach items="${dialog.answerList}" var="answer">
        <form action="${pageContext.request.contextPath}/dialog" method="post">
            <li>
                <c:choose>
                    <c:when test="${answer.nextDialogId == null && answer.questId == null}">
                        <input type="hidden" name="andDialog" value="exit">
                        <button type="submit">${answer.text}</button>
                    </c:when>
                    <c:when test="${answer.nextDialogId == null && answer.questId != null}">
                        <input type="hidden" name="andDialog" value="exit">
                        <input type="hidden" name="questId" value="${answer.questId}">
                        <button type="submit">${answer.text}</button>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="dialogId" value="${answer.nextDialogId}">
                        <button type="submit">${answer.text}</button>
                    </c:otherwise>
                </c:choose>
            </li>
        </form>
    </c:forEach>
</ul>

</body>
</html>
