package net.ion.radon.impl.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import net.ion.framework.db.RepositoryException;
import net.ion.framework.util.StringUtil;

public class CipherUtil {

	public enum Algorithm {
		DES {
			public Key generateKey(byte[] keyData) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException{
				KeySpec keySpec = new DESKeySpec(keyData);
				SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(toString());
				SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
				return secretKey;
			}
		}, DESede{
			public Key generateKey(byte[] keyData) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException{
				KeySpec keySpec = new DESedeKeySpec(keyData);
				SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(toString());
				SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
				return secretKey;
			}
		}, TripleDES{
			public Key generateKey(byte[] keyData) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
				KeySpec keySpec = new DESedeKeySpec(keyData);
				SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(toString());
				SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
				return secretKey;
			}
		}, AES {
			public Key generateKey(byte[] keyData) {
				SecretKeySpec keySpec = new SecretKeySpec(keyData, toString());
				return keySpec;
			}
		} ;
		
		public abstract Key generateKey(byte[] keyData) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException;
	}

	public static Key generateKey(String algorithm) throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		SecretKey key = keyGenerator.generateKey();
		return key;
	}

	public static Key generateKey(Algorithm algorithm, String keyData) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, UnsupportedEncodingException {
		return generateKey(algorithm, StringUtil.leftPad(keyData, 16, '#').getBytes("UTF-8")) ;
	}
	
	public static Key generateKey(Algorithm algorithm, byte[] keyData) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
		return algorithm.generateKey(keyData) ;
	}
	
	public static byte[] encrypt(String value, String ckey) throws InvalidKeyException {
		try {
			byte[] passwordBytes = ckey.getBytes("UTF-8");
			Cipher cipher = initMode(Cipher.ENCRYPT_MODE, passwordBytes);
			return cipher.doFinal(value.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new InvalidKeyException(e.getMessage()) ;
		} catch (NoSuchAlgorithmException e) {
			throw new InvalidKeyException(e.getMessage()) ;
		} catch (InvalidKeySpecException e) {
			throw new InvalidKeyException(e.getMessage()) ;
		} catch (NoSuchPaddingException e) {
			throw new InvalidKeyException(e.getMessage()) ;
		} catch (IllegalBlockSizeException e) {
			throw new InvalidKeyException(e.getMessage()) ;
		} catch (BadPaddingException e) {
			throw new InvalidKeyException(e.getMessage()) ;
		}
	}

	public static String decrypt(byte[] encrypted, String ckey) throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException{
		byte[] passwordBytes = ckey.getBytes("UTF-8");
		Cipher cipher = initMode(Cipher.DECRYPT_MODE, passwordBytes);
		
		byte[] decrypt = cipher.doFinal(encrypted);
		return new String(decrypt, "UTF-8");
	}
	

	
	public static byte[] recursiveEncrypt(String password) {
		try {
			byte[] passwordBytes = password.getBytes("UTF-8");
			Cipher cipher = initMode(Cipher.ENCRYPT_MODE, passwordBytes);
			
			return cipher.doFinal(passwordBytes);
		} catch (UnsupportedEncodingException e) {
			throw RepositoryException.throwIt(e) ;
		} catch (InvalidKeyException e) {
			throw RepositoryException.throwIt(e) ;
		} catch (NoSuchAlgorithmException e) {
			throw RepositoryException.throwIt(e) ;
		} catch (InvalidKeySpecException e) {
			throw RepositoryException.throwIt(e) ;
		} catch (NoSuchPaddingException e) {
			throw RepositoryException.throwIt(e) ;
		} catch (IllegalBlockSizeException e) {
			throw RepositoryException.throwIt(e) ;
		} catch (BadPaddingException e) {
			throw RepositoryException.throwIt(e) ;
		}
		
	}
	
	public static String recursiveDecrypt(String password, byte[] encrypted) throws RepositoryException{
		try {
			byte[] passwordBytes = password.getBytes("UTF-8");
			Cipher cipher = initMode(Cipher.DECRYPT_MODE, passwordBytes);
			
			byte[] decrypt = cipher.doFinal(encrypted);
			return new String(decrypt, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw RepositoryException.throwIt(e) ;
		} catch (InvalidKeyException e) {
			throw RepositoryException.throwIt(e) ;
		} catch (NoSuchAlgorithmException e) {
			throw RepositoryException.throwIt(e) ;
		} catch (InvalidKeySpecException e) {
			throw RepositoryException.throwIt(e) ;
		} catch (NoSuchPaddingException e) {
			throw RepositoryException.throwIt(e) ;
		} catch (IllegalBlockSizeException e) {
			throw RepositoryException.throwIt(e) ;
		} catch (BadPaddingException e) {
			throw RepositoryException.throwIt(e) ;
		}
	}

	
	private static Cipher initMode(int mode, byte[] passwordBytes) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException {
		int len = passwordBytes.length;
		byte[] keyBytes = new byte[16];
		
		if (len >= 16) {
			System.arraycopy(passwordBytes, 0, keyBytes, 0, 16);
		} else {
			System.arraycopy(passwordBytes, 0, keyBytes, 0, len);
			for (int i = 0; i < (16 - len); i++) {
				keyBytes[len + i] = passwordBytes[i % len];
			}
		}
		
		Key key = CipherUtil.generateKey(CipherUtil.Algorithm.AES, keyBytes);
		String transformation = "AES/ECB/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(transformation);
		
		cipher.init(mode, key);
		return cipher;
	}

	public static boolean isMatch(String password, byte[] encrypted) throws RepositoryException{
		byte[] encrypt = recursiveEncrypt(password) ;
		
		return Arrays.equals(encrypt, encrypted) ;
	}

}
