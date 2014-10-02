package au.net.abc.utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SmtpClientTest
{
	public SmtpClientTest() 
	{
		String to = "paultest@abc.com";
		String from = "osb@abc.com";
		String host = "WS049487";		
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", "2525");
		
		Session session = Session.getDefaultInstance(properties);

		try 
		{
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("This is the Subject Line!");

			message.setText("This is actual message");

			Transport.send(message);
			System.out.println("Sent message successfully....");
		}
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new SmtpClientTest();
	}

}
