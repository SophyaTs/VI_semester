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
            <a href="/ROlab15/citynew">Add New City</a>
            &nbsp;&nbsp;&nbsp;
            <a href="/ROlab15/citylist">List All city</a>
             &nbsp;&nbsp;&nbsp;
            <a href="/ROlab15">Back</a>
        </h2>
    </center>
    <div align="center">
        <c:if test="${city != null}">
            <form action="cityupdate" method="post">
        </c:if>
        <c:if test="${city == null}">
            <form action="cityinsert" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    <c:if test="${city != null}">
                        Edit City
                    </c:if>
                    <c:if test="${city == null}">
                        Add New City
                    </c:if>
                </h2>
            </caption>
                <c:if test="${city != null}">
                    <input type="hidden" name="id" value="<c:out value='${city.id}' />" />
                </c:if>           
            <tr>
                <th>Name: </th>
                <td>
                    <input type="text" name="name" size="45"
                            value="<c:out value='${city.name}' />"
                        />
                    <input type="text" name="countryid" size="45"
                            value="<c:out value='${city.countryid}' />"
                        />
                    <input type="text" name="population" size="45"
                            value="<c:out value='${city.population}' />"
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