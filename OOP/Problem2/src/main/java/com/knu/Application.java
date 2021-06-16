package com.knu;

import java.io.IOException;
import java.security.*;

import javax.xml.ws.Endpoint;

public class Application {
    static Key PUB_KEY;
    static Key PRV_KEY;
    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        PUB_KEY = kp.getPublic();
        PRV_KEY = kp.getPrivate();
        
        String url = "http://localhost:8080/";

        Endpoint endpoint = Endpoint.publish(url, new Server());
    }
}