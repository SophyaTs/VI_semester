package Lab2;

import java.util.*;

public class RC4 {
	private Character[] key;
	private Character[] permutations;
	private static final Random rand = new Random();
	
	// generates n-byte key
	public RC4(int n) {
		if (n < 1 || n > 256) {
			throw new IllegalArgumentException("Illegal key length");
		}
		key = new Character[n];
		for(int i = 0; i < n; ++i) {
			key[i] = (char) rand.nextInt();
		}
	}
	
	public String encipher(String str) {
		return convert(str);
	}
	
	public String decipher(String str) {
		return convert(str);
	}
	
	private String convert(String str) {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException("Str shouldn't be empty or null");
		}
		
		var keystream = getKeystream(str.length());
		var cstr = new StringBuilder();
		
		for(int i = 0; i < str.length(); ++i) {
			char c  = (char) (str.charAt(i) ^ keystream[i]);
			cstr.append(c);
		}
		return cstr.toString();
	}
	
	private void KSA() {
		permutations = new Character[256];
		for (int i = 0; i < 256; ++i) {
			permutations[i] = (char) i;
		}

		int i = 0, j = 0;
		for (i = 0; i < 256; ++i) {
			j = (j + permutations[i] + key[i % key.length]) % 256;
			swap(permutations,i,j);
		}
		
	}

	private Character[] getKeystream(int length) {
		KSA();		
		var keystream = new Character[length];
		int i = 0, j = 0;
		for(int k = 0; k < length; ++k) {
			i = (i + 1) % 256;
			j = (j + permutations[i]) % 256;
			swap(permutations, i, j);
			var t = (permutations[i] + permutations[j]) % 256;
			keystream[k] = permutations[t];
		}
		return keystream;
	}
	
	public static final <T> void swap(T[] a, int i, int j) {
		T t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

}
