package com.vs4vijay.entity;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class BotMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String jid;
	
	@Persistent
	private String body;
	
	public BotMessage() {
		super();
	}
	
	public BotMessage(String jid, String body) {
		super();
		this.jid = jid;
		this.body = body;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getJid() {
		return jid;
	}
	
	public void setJid(String jid) {
		this.jid = jid;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "BotMessage [key=" + key + ", jid=" + jid + ", body=" + body
				+ "]";
	}
}
