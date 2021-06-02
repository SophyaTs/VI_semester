/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Lab2;

import org.junit.Test;
import org.junit.Assert;

public class RC4Tests {
    @Test (expected = IllegalArgumentException.class)
    public void throwNulllStr() {
    	var rc4 = new RC4(5);
    	rc4.encipher(null);   	
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void throwEmptyStr() {
    	var rc4 = new RC4(5);
    	String s = "";
    	rc4.encipher(s);   	
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void throwTooSmallN() {
    	var rc4 = new RC4(0);  	
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void throwTooBigN() {
    	var rc4 = new RC4(257);  	
    }
    
    @Test
    public void correctEnciphering() {
    	var rc4 = new RC4(45);
    	String msg = "Hello world it's meeeeeeeeeeee";
		String cmsg = rc4.encipher(msg);
		String dcmsg = rc4.decipher(cmsg);
    	Assert.assertEquals(msg, dcmsg);
    }
}