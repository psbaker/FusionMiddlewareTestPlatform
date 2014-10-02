package au.net.abc.utils;

import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

public class MockFtpServerUtils
{
	FakeFtpServer ftpServer;	

	public void init() throws Exception
	{
		initFakeFtpServer();
	}

	public void cleanup() throws Exception
	{
		cleanupFakeFtpServer();
	}

	private void initFakeFtpServer()
	{
		ftpServer = new FakeFtpServer();
		  
		ftpServer.setServerControlPort(9981);
		ftpServer.addUserAccount(new UserAccount("ONLINE\\esbftpwcmstest", "358FtPwcmsTe5t", "/"));
		
		FileSystem fileSystem = new UnixFakeFileSystem();
		DirectoryEntry directory = new DirectoryEntry("/externalftp/project/visionbytes_news");
		fileSystem.add(directory);
		
		DirectoryEntry directory2 = new DirectoryEntry("/externalftp/project/visionbytes_news/completed");
		fileSystem.add(directory2);
		
		DirectoryEntry directory3 = new DirectoryEntry("/externalftp/project/visionbytes_news/errors");
		fileSystem.add(directory3);
		  
		ftpServer.setFileSystem(fileSystem);
		ftpServer.setSystemName("UNIX");
		  
		ftpServer.start();
	}

	private void cleanupFakeFtpServer()
	{
		this.ftpServer.stop();
	}

}
