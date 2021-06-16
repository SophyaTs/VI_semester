package com.knu;

import java.util.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

import javax.jws.WebService;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService
//@HandlerChain(file = "../../../../handler-chain.xml")
public class Server {
	public Server() {
    }

	@WebMethod
	public String getPublicKey(@WebParam(name="key") String key, @WebParam(name="id") Integer id) {
        ClientDao cliendDao = new ClientDao();
        System.out.println("Request!");
        try {
            cliendDao.setKeyByID(id, key);
            return Base64.getEncoder().encodeToString(Application.PUB_KEY.getEncoded());
        } catch (Exception err) {
            return err.toString();
        }
	}
	
	@WebMethod
	public String getMessage(@WebParam(name="id") Integer id, @WebParam(name="arg") String message) {
        ClientDao cliendDao = new ClientDao();
        try {
            String  keyStr = cliendDao.getKeyByID(id);

            byte[] keyBytes = Base64.getDecoder().decode(keyStr.getBytes("utf-8"));
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Key key = keyFactory.generatePublic(spec);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encStr = cipher.doFinal(message.getBytes());
            System.out.println(new String(Base64.getEncoder().encodeToString(encStr)));

            return new String(Base64.getEncoder().encodeToString(encStr));
        } catch (Exception err) {
            return err.toString();
        }
	}
}