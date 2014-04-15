package com.gogoair.model;

public class SecurityContext {
	private byte[] aesKey;
	private byte[] iv;
	private String transformation;

	public SecurityContext(byte[] aesKey, byte[] iv, String transformation)
			throws Exception {
		this.aesKey = aesKey;
		this.iv = iv;
		this.transformation = transformation;
	}
}
