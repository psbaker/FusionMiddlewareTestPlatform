package au.net.abc.utils;

import org.mocksmtpserver.Server;

public class SMTPServerTest
{
	public SMTPServerTest()
	{
		runMockSmtpServer();
	}
	
	private void runMockSmtpServer()
	{
		try
		{
			Server smtpServer = new Server();
			
			smtpServer.setHost("WS049487");
			smtpServer.setPort(2525);
					  
			smtpServer.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		new SMTPServerTest();
	}

}
