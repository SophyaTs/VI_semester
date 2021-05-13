<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>ROlab15</title>
</head>
<body>
    <center>
        <h1>Cities</h1>
        <h2>
            <a href="/ROlab15/citynew">Add New City</a>
            &nbsp;&nbsp;&nbsp;
            <a href="/ROlab15/citylist">List All Cities</a>
            &nbsp;&nbsp;&nbsp;
            <a href="/ROlab15">Back</a>
        </h2>
    </center>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Cities</h2></caption>
            <tr>
                <th>ID</th>
                <th>Country ID</th>
                <th>Name</th>
                <th>Population</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="city" items="${listCity}">
                <tr>
                    <td><c:out value="${city.id}" /></td>
                    <td><c:out value="${city.countryid}" /></td>
                    <td><c:out value="${city.name}" /></td>
                    <td><c:out value="${city.population}" /></td>
                    <td>
                        <a href="/ROlab15/cityedit?id=<c:out value='${city.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="/ROlab15/citydelete?id=<c:out value='${city.id}' />">Delete</a>                     
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>   
</body>
</html>