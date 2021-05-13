<!DOCTYPE html>
<html>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="js/rest.js"></script>
<%@ page contentType="text/html; charset=UTF-8" %>

<head>
    <title>Insert City</title>
</head>

<body>
    <div style="padding-left: 50px; font-family: monospace;">
        <h2>Insert Country</h2>
        <form id="insertCityForm">
            <div style="width: 100px; text-align: left;">
                <div style="padding: 10px;">
                    Name: <input name="name" id="insCtName"/>                    
                </div>
                <div style="padding: 10px;">
                    Country id: <input name="countryid" id="insCtCountryid"/>
                </div>
                <div style="padding: 10px;">
                    Population: <input name="population" id="insCtPopulation"/>
                </div>
                <div style="padding: 20px; text-align: center">
                    <input type="submit" value="Submit" />
                </div>
            </div>
        </form>
    </div>
</body>

</html>