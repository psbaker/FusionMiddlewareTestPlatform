package au.net.abc.utils;

import org.mocksmtpserver.Server;

public class MockSmtpServerUtils
{
	Server smtpServer;	

	public void init() throws Exception
	{
		initMockSmtpServer();
	}

	public void cleanup() throws Exception
	{
		cleanupMockSmtpServer();
	}

	private void initMockSmtpServer()
	{
		try
		{
			smtpServer = new Server();
			
			smtpServer.setHost("localhost");
			smtpServer.setHost("2525");
					  
			smtpServer.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void cleanupMockSmtpServer()
	{
		try
		{
			this.smtpServer.stop();			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
