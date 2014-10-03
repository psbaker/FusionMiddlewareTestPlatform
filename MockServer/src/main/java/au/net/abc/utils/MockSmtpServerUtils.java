package au.net.abc.utils;

import java.net.InetAddress;

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
			
			String machineName = InetAddress.getLocalHost().getHostName();
			System.out.println("Machine Name: " + machineName);
			smtpServer.setHost(machineName);
			smtpServer.setPort(2525);
					  
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
