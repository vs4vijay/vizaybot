package com.vs4vijay.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.vs4vijay.entity.BotMessage;

@SuppressWarnings("serial")
public class VizBotServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");

		PrintWriter out = resp.getWriter();
		out.println("Welcome !!! <br/>");
		out.println("Add vizaybot@appspot.com to your GTalk.");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		XMPPService xmppService = XMPPServiceFactory.getXMPPService();
		Message message = xmppService.parseMessage(request);

		JID jid = message.getFromJid();
		String body = message.getBody();

		System.out.println("from JID: " + jid.getId());
		System.out.println("Body : " + body);

		BotMessage botMessage = new BotMessage(jid.getId(), body);
		this.save(botMessage);

		String sender = jid.getId().split("@")[0];
		String reply = "Hey *" + sender + "*, You have entered : " + body
				+ " B-)";

		Message replyMessage = new MessageBuilder().withRecipientJids(jid)
				.withBody(reply).build();

		if (xmppService.getPresence(jid).isAvailable()) {
			SendResponse status = xmppService.sendMessage(replyMessage);
			System.out.println("Status : " + status);
		}

	}

	public Boolean save(BotMessage botMessage) {
		PersistenceManagerFactory pmf = new JDOHelper()
				.getPersistenceManagerFactory("transactions-optional");
		PersistenceManager persistenceManager = pmf.getPersistenceManager();
		persistenceManager.makePersistent(botMessage);
		return Boolean.TRUE;
	}
}
