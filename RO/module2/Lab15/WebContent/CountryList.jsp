<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>ROlab15</title>
</head>
<body>
    <center>
        <h1>Countries</h1>
        <h2>
            <a href="/ROlab15/countrynew">Add New Country</a>
            &nbsp;&nbsp;&nbsp;
            <a href="/ROlab15/countrylist">List All Countries</a>
             &nbsp;&nbsp;&nbsp;
            <a href="/ROlab15">Back</a>
        </h2>
    </center>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Countries</h2></caption>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="country" items="${listCountry}">
                <tr>
                    <td><c:out value="${country.id}" /></td>
                    <td><c:out value="${country.name}" /></td>
                    <td>
                        <a href="/ROlab15/countryedit?id=<c:out value='${country.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="/ROlab15/countrydelete?id=<c:out value='${country.id}' />">Delete</a>                     
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>   
</body>
</html>