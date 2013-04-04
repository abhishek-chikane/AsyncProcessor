/**
 * 
 */
package com.cch.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author pthorat
 */
public class EmailNotification
{
	/**
	 * @param recipients
	 * @param message
	 * @param subject
	 */
	public static void sendEmail(String fromMailId, String toMailIds, String ccMailIds,
	        String message, String subject) throws Exception
	{
		boolean debug = false;
		// Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.server.url");

		// create some properties and get the default Session
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(debug);

		// create a message
		Message msg = new MimeMessage(session);

		try
		{// set the from address
			InternetAddress addressFrom = new InternetAddress(fromMailId);
			msg.setFrom(addressFrom);
			// Set To address
			InternetAddress[] addressTo = InternetAddress.parse(toMailIds);
			msg.setRecipients(Message.RecipientType.TO, addressTo);
			// Set CC addresses
			InternetAddress[] addressCc = InternetAddress.parse(ccMailIds);
			msg.setRecipients(Message.RecipientType.CC, addressCc);
			// Setting the Subject and Content Type
			msg.setSubject(subject);
			msg.setContent(message, "text/html");
			Transport.send(msg);
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			throw e;
		}
	}

}