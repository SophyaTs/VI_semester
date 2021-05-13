Dockerfile and .war should be in the same directory
To build image (update .war for exmaple):
```sudo docker build -t rolab16 .```
To run docker container:
```sudo docker run -p 80:8080 --add-host=dbhost:<host-ip> rolab16```