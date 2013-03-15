package com.vs4vijay.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;

@SuppressWarnings("serial")
public class VizBotServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world <br/>");
		resp.getWriter().println("Welcome !!!");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {

		XMPPService xmppService = XMPPServiceFactory.getXMPPService();

		Message message = xmppService.parseMessage(request);

		JID jid = message.getFromJid();
		String body = message.getBody();
		
		System.out.println("from JID: " + jid);
		System.out.println("Body : " + body);

		String reply = "Hey Viz, You have entered : " + body;

		Message replyMessage = new MessageBuilder().withRecipientJids(jid)
				.withBody(reply).build();

		if (xmppService.getPresence(jid).isAvailable()) {
			SendResponse status = xmppService.sendMessage(replyMessage);

			System.out.println("Status : " + status);
		}

	}
}
