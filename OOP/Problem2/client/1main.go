package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"strings"
)

func main() {

	resp, err := http.Post("http://localhost:8080", "text/xml",
		strings.NewReader(`<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:knu="http://knu.com/">
	<soapenv:Header/>
	<soapenv:Body>
	   <knu:getPublicKey>
		  <key>MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvoavsirloOCs/Z5s/cEjaUsw5BoiAXOhx0JFIg3yAUBeE1kPPHNSMCDT2Hib+PxNc/uPR+80QdRh1xxnkeUh7yLSQZ9/6eKTZlmxUMQS1CWQXdaMNAUJcqHg9efOlm2/aylnLs9kzygarJA0xwtwOz9DzYLTiyrNj1exsTP5za4Smb2dYIFoTnNZP4/oMFPbWZKdALUeV509nuLivcIrrpo3IYIBOeMuMsJUZZs8JKGZQun2zHx+CRTB+TWMP2XIxiVDs6c8tRT4DhRBOdgkVO8HTWmOWB4XfVEPMk88pSGUcpMoDxXdiV8zCrPe1B/T6jR3IbKTtT6YXxmkYN8ikwIDAQAB</key>
		  <id>1</id>
	   </knu:getPublicKey>
	</soapenv:Body>
 </soapenv:Envelope>`))

	if err != nil {
		log.Fatal(err)
	}

	defer resp.Body.Close()

	body, err := ioutil.ReadAll(resp.Body)

	if err != nil {

		log.Fatal(err)
	}

	fmt.Println(string(body))
}
