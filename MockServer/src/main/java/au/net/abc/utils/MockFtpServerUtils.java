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
		ftpServer.addUserAccount(new UserAccount("user1", "password", "/"));
		
		FileSystem fileSystem = new UnixFakeFileSystem();
		
		DirectoryEntry directory = new DirectoryEntry("/externalftp/project/visionbytes_news");
		fileSystem.add(directory);
		
		DirectoryEntry directory2 = new DirectoryEntry("/externalftp/project/visionbytes_news/completed");
		fileSystem.add(directory2);
		
		DirectoryEntry directory3 = new DirectoryEntry("/externalftp/project/visionbytes_news/errors");
		fileSystem.add(directory3);
		
		DirectoryEntry directory4 = new DirectoryEntry("/onair/epg/online");
		fileSystem.add(directory4);
		
		DirectoryEntry directory5 = new DirectoryEntry("/onair/epg/fta");
		fileSystem.add(directory5);
		
		DirectoryEntry directory6 = new DirectoryEntry("/onair/epg/internet");
		fileSystem.add(directory6);
		
		DirectoryEntry directory7 = new DirectoryEntry("/onair/epg/mobile");
		fileSystem.add(directory7);
		
		DirectoryEntry directory8 = new DirectoryEntry("/onair/epg/ebroadcast");
		fileSystem.add(directory8);
		
		DirectoryEntry directory9 = new DirectoryEntry("/onair/epg/external");
		fileSystem.add(directory9);
		
		DirectoryEntry directory10 = new DirectoryEntry("/Out/AsRun/ABC");
		fileSystem.add(directory10);
		
		DirectoryEntry directory11 = new DirectoryEntry("/Out/AsRunDR/ABC");
		fileSystem.add(directory11);
		
		DirectoryEntry directory12 = new DirectoryEntry("/Ingest");
		fileSystem.add(directory12);
		
		DirectoryEntry directory13 = new DirectoryEntry("/www/iview/api/onair");
		fileSystem.add(directory13);
		
		DirectoryEntry directory14 = new DirectoryEntry("/www/xmlcontent/tv/guide/OnAirIviewInput");
		fileSystem.add(directory14);

		//Audience Contact application
		DirectoryEntry directory15 = new DirectoryEntry("/AudienceContact");
		fileSystem.add(directory15);

		//Momentum application
		DirectoryEntry directory16 = new DirectoryEntry("/to_abc");
		fileSystem.add(directory16);

		DirectoryEntry directory17 = new DirectoryEntry("/from_abc");
		fileSystem.add(directory17);
		
		//Harvester application
		DirectoryEntry directory18 = new DirectoryEntry("/mediahub/ABCNews24Ticker/RollingFeeds");
		fileSystem.add(directory18);
		
		DirectoryEntry directory19 = new DirectoryEntry("/mediahub/ABCNews24Ticker/BreakingNews");
		fileSystem.add(directory19);
		
		DirectoryEntry directory20 = new DirectoryEntry("/divitech/sundial1a");
		fileSystem.add(directory20);
		
		DirectoryEntry directory21 = new DirectoryEntry("/divitech/sundial1b");
		fileSystem.add(directory21);
		
		DirectoryEntry directory22 = new DirectoryEntry("/divitech/sundialdr");
		fileSystem.add(directory22);
		
		DirectoryEntry directory23 = new DirectoryEntry("/divitech/divitechdr");
		fileSystem.add(directory23);
		
		ftpServer.setFileSystem(fileSystem);
		ftpServer.setSystemName("UNIX");
		  
		ftpServer.start();
	}

	private void cleanupFakeFtpServer()
	{
		this.ftpServer.stop();
	}

}
