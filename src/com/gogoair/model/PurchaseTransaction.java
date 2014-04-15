package com.gogoair.model;

public class PurchaseTransaction {
	private String cardNum;
	private byte[] cardNumBytes;
	private String nameOnCard;
	private byte[] nameOnCardBytes;
	private String amount;
	private byte[] amountBytes;

	private byte[] securityContext;
	private String keyVersion;

	public PurchaseTransaction(String cardNum, String nameOnCard, String amount) {
		this.cardNum = cardNum;
		this.nameOnCard = nameOnCard;
		this.amount = amount;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public byte[] getCardNumBytes() {
		return cardNumBytes;
	}

	public void setCardNumBytes(byte[] cardNumBytes) {
		this.cardNumBytes = cardNumBytes;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public byte[] getNameOnCardBytes() {
		return nameOnCardBytes;
	}

	public void setNameOnCardBytes(byte[] nameOnCardBytes) {
		this.nameOnCardBytes = nameOnCardBytes;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public byte[] getAmountBytes() {
		return amountBytes;
	}

	public void setAmountBytes(byte[] amountBytes) {
		this.amountBytes = amountBytes;
	}

	public byte[] getSecurityContext() {
		return securityContext;
	}

	public String getKeyVersion() {
		return keyVersion;
	}

	public void setSecurityContext(byte[] securityContext, String keyVersion) {
		this.securityContext = securityContext;
		this.keyVersion = keyVersion;
	}
}
