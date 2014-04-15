package com.gogoair.rsa;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;

public class AsymmetricParameters {
	private KeyStore keyStore;
	private char[] password;
	private String transformation;

	public AsymmetricParameters(String keyStorePath, String storeType,
			String password, String transformation) throws Exception {
		this.password = password.toCharArray();
		keyStore = KeyStore.getInstance(storeType);
		keyStore.load(new FileInputStream(keyStorePath), this.password);
		this.transformation = transformation;
	}

	public Certificate getCertificate(String alias) throws Exception {
		return keyStore.getCertificate(alias);
	}

	public Key getKey(String alias) throws Exception {
		return keyStore.getKey(alias, password);
	}

	public String getTransformation() {
		return transformation;
	}
}
