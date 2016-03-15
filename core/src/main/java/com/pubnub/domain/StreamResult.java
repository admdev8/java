package com.pubnub.domain;


public class StreamResult extends Result {
	StreamData data;

	public StreamData getData() {
		return data;
	}
	public StreamResult() {
		data = new StreamData();
	}
	
	public StreamResult(SubscribeResult result) {
		this.clientRequest = result.clientRequest;
		this.code = result.code;
		this.config = result.config;
		this.connectionId = result.connectionId;
		this.hreq = result.hreq;
		this.operation = result.operation;
		this.pubnub = result.pubnub;
		this.serverResponse = result.serverResponse;
		this.type = result.type;
		data = new StreamData();
		data.message = result.data.message;
		data.timetoken = result.data.timetoken;
	}
	
	public String toString() {
		System.out.println("to string " + data.message);
		String s = super.toString();
		s = s + data + "\n";
		return s;
		
	}
}