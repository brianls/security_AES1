package com.gogoair.aes;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SymmetricParameters {
	private byte[] key;
	private byte[] iv;
	private String transformation;
	private Cipher cipher;

	public SymmetricParameters(byte[] key, byte[] iv, String transformation)
			throws Exception {
		this.key = key;
		this.iv = iv;
		this.transformation = transformation;
		cipher = Cipher.getInstance(transformation);
	}

	public SymmetricParameters() throws Exception {
		SecureRandom sr = new SecureRandom();
		key = new byte[16];
		iv = new byte[16];
		transformation = "AES/CBC/PKCS5Padding";
		sr.nextBytes(key);
		sr.nextBytes(iv);

		cipher = Cipher.getInstance(transformation);
	}

	public byte[] getKey() {
		return key;
	}

	public byte[] getIV() {
		return iv;
	}

	public String getTransformation() {
		return transformation;
	}

	public Cipher getCipher(int cipherMode) throws Exception {
		cipher.init(cipherMode, new SecretKeySpec(key, "AES"),
				new IvParameterSpec(iv));

		return cipher;
	}
}
