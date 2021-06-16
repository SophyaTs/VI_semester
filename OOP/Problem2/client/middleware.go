package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
)

func acknowledge(w http.ResponseWriter, req *http.Request) {
	resp, err := http.Post("http://localhost:8080", "text/xml", req.Body)

	if err != nil {
		log.Fatal(err)
	}

	defer resp.Body.Close()

	w.Header().Set("Access-Control-Allow-Origin", "*")
	w.Header().Set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE")
	w.Header().Set("Access-Control-Allow-Headers",
		"Accept, Content-Type, Content-Length, Accept-Encoding, X-CSRF-Token, Authorization")
	w.WriteHeader(http.StatusOK)
	data, _ := ioutil.ReadAll(resp.Body)
	fmt.Fprint(w, string(data))
}

func main() {

	http.HandleFunc("/process", acknowledge)

	http.ListenAndServe(":8082", nil)
}
