<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body bgcolor="#dcdcdc">
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2 align="center">Моя еда</h2>
    <form action="meals" method="post">
        <input type="hidden" name="action" value="filter">
        <table align="center" bgcolor="#f0ffff">
            <tr>
                <th width="150" align="left">От даты</th>
                <th width="150" align="left">До даты</th>
                <th width="150">&nbsp;</th>
                <th width="150" align="left">От времени</th>
                <th width="150" align="left">До времени</th>
            </tr>
            <tr>
                <td><input type="date" name="startDate" value="${startDate}"></td>
                <td><input type="date" name="endDate" value="${endDate}"></td>
                <td></td>
                <td><input type="time" name="startTime" value="${startTime}"></td>
                <td><input type="time" name="endTime" value="${endTime}"></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td>
                    <button type="submit" style="color: #FFFFFF; background-color: blue">ОТФИЛЬТРОВАТЬ</button>
                </td>
                <td>
                    <button type="button" style="background-color: red"><a href="meals" style="color: #FFFFFF"/>ОТМЕНА
                    </button>
                </td>
            </tr>
        </table>
    </form>
    <hr/>
    <button type="submit"
            style="margin-left:250px; margin-bottom:10px; width: 120px; height: 35px; vertical-align: center; background-color:#5555FF; font-size: 15px; font-style: italic">
        <a href="meals?action=create" style="color: #FFFFFF">Добавить</a></button>

    <table border="1" cellpadding="8" cellspacing="0" bgcolor="#cccccc" align="center">
        <thead>
        <tr>
            <th width="150">Date</th>
            <th width="150">Description</th>
            <th width="150">Calories</th>
            <th width="150">&nbsp;</th>
            <th width="150">&nbsp;</th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>