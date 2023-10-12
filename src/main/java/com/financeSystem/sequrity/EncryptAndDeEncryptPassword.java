package com.financeSystem.sequrity;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptAndDeEncryptPassword {
//	private static final String UNICODE_FORMAT ="UTF8";
//	public static final String DESEDE_ENCRYPTION_SCHEME ="DESede";
//	private KeySpec ks;
//	private SecretKeyFactory skf;
//	private Cipher cipher;
//	byte[] arrayBytes;
//	private String myEncryptionKey;
//	private String myEncryptionScheme;
//	SecretKey key;
//	
//	public EncryptAndDeEncryptPassword() throws Exception{
//		
//	}  
	private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public String encryptMessage(byte[] message, byte[] keyBytes) throws InvalidKeyException, NoSuchPaddingException,
        NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedMessage = cipher.doFinal(message);
            return new String(encryptedMessage);
        }

    public String decryptMessage(byte[] encryptedMessage, byte[] keyBytes) throws NoSuchPaddingException,
        NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] clearMessage = cipher.doFinal(encryptedMessage);
            return new String(clearMessage);
        }
}
