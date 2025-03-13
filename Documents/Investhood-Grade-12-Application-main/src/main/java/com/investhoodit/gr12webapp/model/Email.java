package com.investhoodit.gr12webapp.model;

public class Email {
	private String messageFrom;
	private String subject;
	private String body;
	private String recipient;

	public Email() {

	}

	public Email(String messageFrom, String subject, String body, String recipient) {
		super();
		this.messageFrom = messageFrom;
		this.subject = subject;
		this.body = body;
		this.recipient = recipient;
	}

	public String getMessageFrom() {
		return messageFrom;
	}

	public void setMessageFrom(String messageFrom) {
		this.messageFrom = messageFrom;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

}
