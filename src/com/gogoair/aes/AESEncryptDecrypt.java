package com.gogoair.aes;

import javax.crypto.Cipher;

import com.gogoair.model.PurchaseTransaction;

public class AESEncryptDecrypt {

	public static void main(String[] args) throws Exception {
		String plainText = "What the hell?";
		SymmetricParameters parms = new SymmetricParameters();
		byte[] cipherText = encrypt(parms, plainText);
		String plainText2 = decrypt(parms, cipherText);
		System.out.println("plainText=" + plainText + ", plainText2="
				+ plainText2);
	}

	public static SymmetricParameters encrypt(PurchaseTransaction pt)
			throws Exception {
		SymmetricParameters parms = new SymmetricParameters();
		Cipher cipher = parms.getCipher(Cipher.ENCRYPT_MODE);
		pt.setCardNumBytes(cipher.doFinal(pt.getCardNum().getBytes("UTF-8")));
		pt.setNameOnCardBytes(cipher.doFinal(pt.getNameOnCard().getBytes(
				"UTF-8")));
		pt.setAmountBytes(cipher.doFinal(pt.getAmount().getBytes("UTF-8")));

		return parms;
	}

	public static void decrypt(PurchaseTransaction pt, SymmetricParameters parms)
			throws Exception {
		Cipher cipher = parms.getCipher(Cipher.DECRYPT_MODE);
		pt.setCardNum(new String(cipher.doFinal(pt.getCardNumBytes())));
		pt.setNameOnCard(new String(cipher.doFinal(pt.getNameOnCardBytes())));
		pt.setAmount(new String(cipher.doFinal(pt.getAmountBytes())));
	}

	public static byte[] encrypt(SymmetricParameters parms, String plainText)
			throws Exception {
		Cipher cipher = parms.getCipher(Cipher.ENCRYPT_MODE);
		return cipher.doFinal(plainText.getBytes("UTF-8"));
	}

	public static String decrypt(SymmetricParameters parms, byte[] cipherText)
			throws Exception {
		Cipher cipher = parms.getCipher(Cipher.DECRYPT_MODE);
		byte[] plainTextBytes = cipher.doFinal(cipherText);
		return new String(plainTextBytes);
	}

}
