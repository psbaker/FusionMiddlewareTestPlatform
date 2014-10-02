package au.net.abc.utils;

import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

public class FTPServerTest
{	
	String TEST_USER_FTP_ROOT = "/";
	String TEST_USERNAME = "testuser";
	String TEST_PASSWORD = "pass";
	
	public FTPServerTest()
	{	
		runMockFtpServer();		
	}
	
	public static void main(String[] args)
	{
		new FTPServerTest();
	}
	
	private void runMockFtpServer()
	{
		FakeFtpServer ftpServer = new FakeFtpServer();
		
		System.out.println("Starting FTP Server...");
		  
		ftpServer.setServerControlPort(9981);
		ftpServer.addUserAccount(new UserAccount("ONLINE\\esbftpwcmstest", "358FtPwcmsTe5t", "/"));
		
		FileSystem fileSystem = new UnixFakeFileSystem();
		DirectoryEntry directory = new DirectoryEntry("/externalftp/project/visionbytes_news");
		fileSystem.add(directory);
		
		DirectoryEntry directory2 = new DirectoryEntry("/externalftp/project/visionbytes_news/completed");
		fileSystem.add(directory2);
		
		DirectoryEntry directory3 = new DirectoryEntry("/externalftp/project/visionbytes_news/errors");
		fileSystem.add(directory3);
		  
		FileEntry fileEntry = new FileEntry("/externalftp/project/visionbytes_news/test.xml.sem");
		fileEntry.setOwner("testuser");
		fileEntry.setGroup("group");
		fileSystem.add(fileEntry);
		
		FileEntry xmlFileEntry = new FileEntry("/externalftp/project/visionbytes_news/test.xml");
		xmlFileEntry.setOwner("ONLINE\\esbftpwcmstest");
		xmlFileEntry.setGroup("group");
		xmlFileEntry.setContents("<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<job-metadata><slug>VIDEO - TEST_imageDuplicate</slug><broadcast-time>28/06/2013 16:00</broadcast-time><transcript>VIDEOVIDEOVIDEOVIDEOVIDEOVIDEOVIDEOVIDEOVIDEOVIDEOTEST_imageDuplicateTEST_imageDuplicate.TEST_imageDuplicateTEST_imageDuplicateTEST_imageDuplicate.TEST_imageDuplicate.TEST_imageDuplicateTEST_imageDuplicateTEST_imageDuplicateTEST_imageDuplicate.TEST_imageDuplicate.TEST_imageDuplicateTEST_imageDuplicateTEST_imageDuplicateTEST_imageDuplicate</transcript>" +
				"<headline>Headline: TEST_imageDuplicate</headline><description>Description: TEST_imageDuplicate</description><sys-storage-area-name>Test Import</sys-storage-area-name><DestinationCMS>CoreMedia</DestinationCMS>" +
				"<keywords>Entertainment</keywords><subjects>Subjects:Arts and Entertainment:Film (Movies);</subjects><primary-subject>Subjects:Arts and Entertainment:Film (Movies);</primary-subject>" +
				"<locations>Locations:United States;</locations><primary-location>Locations:United States;</primary-location><reporter>Scott Willis</reporter><genre>Current</genre>" +
				"<copyright>ABC</copyright><autopublish>True</autopublish><media>Video</media></job-metadata>");
		fileSystem.add(xmlFileEntry);
		  
		ftpServer.setFileSystem(fileSystem);
		ftpServer.setSystemName("UNIX");
		  
		ftpServer.start();
		System.out.println("Started FTP Server");
	}
}
