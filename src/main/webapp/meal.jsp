<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 12.02.2019
  Time: 12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Add new Meal or Update</title>
</head>
<body>

<a href="<c:url value='meals'/>">Вернуться в основной каталог</a> <br/>

<h1>Добавить или обновить прием пищи</h1>

<form method="post" action="meals" name="addMeal">
    <table style="color: #0000ff; background-color: #cccccc">
        <tr>
            <td>ID номер :</td>
            <td>Задается автоматически<input type="hidden" readonly="readonly" name="mealId" value="${meal.mealId}"/>
            </td>
        </tr>
        <tr>
            <td>Описание :</td>
            <td><input type="text" name="description" value="${meal.description}"></td>
        </tr>
        <tr>
            <td> Количество калорий :</td>
            <td><input type="number" name="calories" value="${meal.calories}"></td>
        </tr>
        <tr>
            <td> Дата и время :</td>
            <td><input type="datetime-local" name="date" value="${meal.dateTime}"></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>

    </table>
</form>
</body>
</html>
