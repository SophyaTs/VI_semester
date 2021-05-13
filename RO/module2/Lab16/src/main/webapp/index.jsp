<!DOCTYPE html>
<%@ page isELIgnored="false" %>
        <html>

        <head>
                <title>RO Lab 16</title>
        </head>

        <body>
                <div style="padding-left:50px;font-family:monospace;">
                        CRUD Operations</br></br>
                        <a href="${pageContext.request.contextPath}/insertCountry.jsp">Insert Country</a><br />
                        <a href="${pageContext.request.contextPath}/rest/country/read">Get Country details</a><br />
                        <a href="${pageContext.request.contextPath}/updateCountry.jsp">Update Country</a><br />
                        <a href="${pageContext.request.contextPath}/deleteCountry.jsp">Delete Country</a><br />
                        <a href="${pageContext.request.contextPath}/insertCity.jsp">Insert City</a><br />
                        <a href="${pageContext.request.contextPath}/rest/city/read">Get City details</a><br />
                        <a href="${pageContext.request.contextPath}/updateCity.jsp">Update City</a><br />
                        <a href="${pageContext.request.contextPath}/deleteCity.jsp">Delete City</a><br />
                </div>
        </body>

        </html>