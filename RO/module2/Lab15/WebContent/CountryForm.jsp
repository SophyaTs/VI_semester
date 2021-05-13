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
        <c:if test="${country != null}">
            <form action="countryupdate" method="post">
        </c:if>
        <c:if test="${country == null}">
            <form action="countryinsert" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    <c:if test="${country != null}">
                        Edit Country
                    </c:if>
                    <c:if test="${country == null}">
                        Add New Country
                    </c:if>
                </h2>
            </caption>
                <c:if test="${country != null}">
                    <input type="hidden" name="id" value="<c:out value='${country.id}' />" />
                </c:if>           
            <tr>
                <th>Name: </th>
                <td>
                    <input type="text" name="name" size="45"
                            value="<c:out value='${country.name}' />"
                        />
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save" />
                </td>
            </tr>
        </table>
        </form>
    </div>   
</body>
</html>