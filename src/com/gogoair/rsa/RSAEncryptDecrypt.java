package com.gogoair.rsa;

import java.security.Key;
import java.security.PublicKey;
import java.security.cert.Certificate;

import javax.crypto.Cipher;

import com.gogoair.aes.AESEncryptDecrypt;
import com.gogoair.aes.SymmetricParameters;
import com.gogoair.model.PurchaseTransaction;

public class RSAEncryptDecrypt {

	public static void main(String[] args) throws Exception {
		String cardNum = "4242424242424242";
		String nameOnCard = "Bob Smith";
		String amount = "19.95";

		PurchaseTransaction pt = new PurchaseTransaction(cardNum, nameOnCard,
				amount);

		// Wallet performs the encryption steps...
		encrypt(pt);

		// ATS performs decription steps...
		decrypt(pt);

		System.out.println("cardNum: " + pt.getCardNum());
		System.out.println("nameOnCard: " + pt.getNameOnCard());
		System.out.println("amount: " + pt.getAmount());
	}

	public static void encrypt(PurchaseTransaction pt) throws Exception {
		SymmetricParameters sParms = AESEncryptDecrypt.encrypt(pt);
		encryptKeys(pt, sParms);
	}

	public static void decrypt(PurchaseTransaction pt) throws Exception {
		SymmetricParameters sParms = decryptKeys(pt);
		AESEncryptDecrypt.decrypt(pt, sParms);
	}

	public static void encryptKeys(PurchaseTransaction pt,
			SymmetricParameters sParms) throws Exception {
		// Concatenate the symmetric key fields and encrypt
		// with public key.
		byte[] transformation = sParms.getTransformation().getBytes("UTF-8");
		byte[] plainContext = new byte[32 + transformation.length];
		System.arraycopy(sParms.getKey(), 0, plainContext, 0, 16);
		System.arraycopy(sParms.getIV(), 0, plainContext, 16, 16);
		System.arraycopy(transformation, 0, plainContext, 32,
				transformation.length);

		String pubStorePath = "ts.jks";
		String storeType = "JKS";
		String password = "changeit";
		String trans = "RSA";
		AsymmetricParameters aParms = new AsymmetricParameters(pubStorePath,
				storeType, password, trans);

		byte[] securityContext = encrypt(aParms, plainContext);

		// Figure out how to move this
		// magic string higher in the call stack.
		pt.setSecurityContext(securityContext, "atsv1");
	}

	public static SymmetricParameters decryptKeys(PurchaseTransaction pt)
			throws Exception {
		String privStorePath = "ks.jks";
		String storeType = "JKS";
		String password = "changeit";
		String trans = "RSA";
		AsymmetricParameters aParms = new AsymmetricParameters(privStorePath,
				storeType, password, trans);

		byte[] securityContext = decryptBytes(aParms, pt.getSecurityContext());
		int transLength = securityContext.length - 32;
		byte[] key = new byte[16];
		byte[] iv = new byte[16];
		byte[] transBytes = new byte[transLength];

		System.arraycopy(securityContext, 0, key, 0, 16);
		System.arraycopy(securityContext, 16, iv, 0, 16);
		System.arraycopy(securityContext, 32, transBytes, 0, transLength);
		String transformation = new String(transBytes);

		return new SymmetricParameters(key, iv, transformation);
	}

	public static void encryptDecrypt(String args[]) throws Exception {
		String plainText = "What the hell?";

		String pubStorePath = "ts.jks";
		String privStorePath = "ks.jks";
		String storeType = "JKS";
		String password = "changeit";
		String trans = "RSA";
		AsymmetricParameters parms = new AsymmetricParameters(pubStorePath,
				storeType, password, trans);

		byte[] cipherText = encrypt(parms, plainText);

		parms = new AsymmetricParameters(privStorePath, storeType, password,
				trans);
		String plainText2 = decrypt(parms, cipherText);
		System.out.println("plainText: " + plainText + ", plainText2: "
				+ plainText2);
	}

	public static byte[] encrypt(AsymmetricParameters parms, byte[] plainText)
			throws Exception {
		Certificate atsCert = parms.getCertificate("atsv1");
		PublicKey pubKey = atsCert.getPublicKey();
		Cipher cipher = Cipher.getInstance(parms.getTransformation());
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return cipher.doFinal(plainText);
	}

	public static byte[] encrypt(AsymmetricParameters parms, String plainText)
			throws Exception {
		Certificate atsCert = parms.getCertificate("atsv1");
		PublicKey pubKey = atsCert.getPublicKey();
		Cipher cipher = Cipher.getInstance(parms.getTransformation());
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return cipher.doFinal(plainText.getBytes());
	}

	private static byte[] decryptBytes(AsymmetricParameters parms,
			byte[] cipherText) throws Exception {
		Key privKey = parms.getKey("atsv1");
		Cipher cipher = Cipher.getInstance(parms.getTransformation());
		cipher.init(Cipher.DECRYPT_MODE, privKey);
		return cipher.doFinal(cipherText);
	}

	private static String decrypt(AsymmetricParameters parms, byte[] cipherText)
			throws Exception {
		Key privKey = parms.getKey("atsv1");
		Cipher cipher = Cipher.getInstance(parms.getTransformation());
		cipher.init(Cipher.DECRYPT_MODE, privKey);
		return new String(cipher.doFinal(cipherText));
	}

}
