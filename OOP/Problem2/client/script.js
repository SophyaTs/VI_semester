let pog;

function sendRequest() {
    $.ajax({
        type: "POST",
        crossOrigin: true,
        url: "http://localhost:8082/process",
        data: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:knu="http://knu.com/"><soapenv:Header/><soapenv:Body><knu:getPublicKey><key>` + $("#key").val() + `</key><id>1</id></knu:getPublicKey></soapenv:Body></soapenv:Envelope>`,
        async: false,
    });

    $.ajax({
        type: "POST",
        crossOrigin: true,
        url: "http://localhost:8082/process",
        data: `<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:knu="http://knu.com/"><soapenv:Header/><soapenv:Body><knu:getMessage><id>1</id><arg>` + $("#message").val() + `</arg></knu:getMessage></soapenv:Body></soapenv:Envelope>`,
        async: false,
        success: function (data) {
            $(function () {
                $('#result').val(data.getElementsByTagName("return")[0].textContent);
            });
        }   
    });
}