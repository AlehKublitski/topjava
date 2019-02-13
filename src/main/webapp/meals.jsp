<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 10.02.2019
  Time: 18:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <style type="text/css">
        .tbl {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #000000;
            background-color: #cccccc;
        }

        .tbl th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #000000;
        }

        .tbl td {
            font-family: Arial, sans-serif;
            font-size: 14px;

            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #000000;
        }
    </style>

    <title>Meals</title>
</head>

<body>
<a href="<c:url value='index.html'/>">Вернуться в главное меню</a>
<br/>

<h1 align="center"> Расчет полученных калорий </h1>

<table class="tbl" border=1 align="center">
    <thead>
    <tr>
        <th>Номер</th>
        <th>Дата,время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th width="100">Есть ли превышение</th>
        <th colspan=2>Корректировка</th>
    </tr>
    </thead>
    <tbody>
    <c:set var="format" value="${formatter}"/>
    <c:forEach items="${mealList}" var="meal">
    <tr class="tr" align="center" <c:out value="${meal.isExcess() ? 'style=color:#ff0000' : 'style=color:#00bb00'}"/>>
        <td>${meal.getMealToId()}</td>
        <td>${meal.getDateTime().format(format)}</td>
        <td>${meal.getDescription()}</td>
        <td>${meal.getCalories()}</td>
        <td>${meal.isExcess()}</td>
        <td><a href="meals?action=delete&mealId=<c:out value="${meal.getMealToId()}"/>"
               title="Данная запись будет удалена"> DELETE</a></td>
        <td><a href="meals?action=edit&mealId=<c:out value="${meal.getMealToId()}"/>"
               title="Данная запись будет изменена">UPDATE</a></td>
    </tr>
    </tbody>
    </c:forEach>
</table>
<br/>
<div align="center">
    <button><a href="meals?action=add" title="Добавить новый прием пищи">Добавить прием пищи</a></button>
</div>

</body>
</html>
