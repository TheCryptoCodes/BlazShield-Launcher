package de.potionmc.launcher.commandinterface;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class HashHandler {
  private String myEncryptionKey = "wSf@G1)_VFC*40HngJN{XjTX6GI~ES?s=r(^[ZY|ikAQBBTaquAmUYK7QKt#F.2zoW}H,VLPxLCM5cDvRpeN!MOh+Z8K5)CAU~GH6x7J_G]{,UHbTNwf}zCO^DBXkv%hmI(IPy=sX-WqE.aZLoON94YVReEjJQWFrAQ80&S1Pn2BlF*tcuVSd?DKT#3M+@;!RLYiMZpg|[IRE;W9Od-%UlD]by3&PJ";
  
  private String myEncryptionScheme = "DESede";
  
  byte[] arrayBytes = this.myEncryptionKey.getBytes("UTF8");
  
  private KeySpec ks = new DESedeKeySpec(this.arrayBytes);
  
  private SecretKeyFactory skf = SecretKeyFactory.getInstance(this.myEncryptionScheme);
  
  private Cipher cipher = Cipher.getInstance(this.myEncryptionScheme);
  
  SecretKey key = this.skf.generateSecret(this.ks);
  
  private static final String UNICODE_FORMAT = "UTF8";
  
  public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";

  public HashHandler() throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
  }

  public String encrypt(String unencryptedString) {
    String encryptedString = null;
    try {
      this.cipher.init(1, this.key);
      byte[] plainText = unencryptedString.getBytes("UTF8");
      byte[] encryptedText = this.cipher.doFinal(plainText);
      encryptedString = new String(Base64.getEncoder().encode(encryptedText));
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return encryptedString;
  }
  
  public String decrypt(String encryptedString) {
    String decryptedText = null;
    try {
      this.cipher.init(2, this.key);
      byte[] encryptedText = Base64.getDecoder().decode(encryptedString.getBytes());
      byte[] plainText = this.cipher.doFinal(encryptedText);
      decryptedText = new String(plainText);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return decryptedText;
  }
  
  public String createPassword(int length) {
    String allowedChars = "0123456789abcdefghijklmnopqrstuvwABCDEFGHIJKLMNOP!";
    SecureRandom random = new SecureRandom();
    StringBuilder pass = new StringBuilder(length);
    for (int i = 0; i < length; i++)
      pass.append("0123456789abcdefghijklmnopqrstuvwABCDEFGHIJKLMNOP!".charAt(random.nextInt("0123456789abcdefghijklmnopqrstuvwABCDEFGHIJKLMNOP!".length())));
    return pass.toString();
  }
}
