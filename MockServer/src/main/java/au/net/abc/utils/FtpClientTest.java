package au.net.abc.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpClientTest
{
	public FtpClientTest()
	{
		try
		{
			FTPClient ftpClient = new FTPClient();
			ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		    int reply;
		    ftpClient.connect("localhost", 9981);
		    reply = ftpClient.getReplyCode();
	        if (!FTPReply.isPositiveCompletion(reply)) {
	        	ftpClient.disconnect();
	            throw new Exception("Exception in connecting to FTP Server");
	        }
	        ftpClient.login("user1", "password");
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	        ftpClient.enterLocalPassiveMode();       
			
			String fileIn1 = "/home/bakerp2y/TEST/VisionBytesFtp/007VIDEO.VIDEO-TEST_imageDuplicate.xml";
			String fileIn1Name = fileIn1.substring(fileIn1.lastIndexOf("/"));
			InputStream input1 = new FileInputStream(new File(fileIn1));
		    ftpClient.storeFile("/externalftp/project/visionbytes_news" + fileIn1Name, input1);			
		    
		    String fileIn2 = "/home/bakerp2y/TEST/VisionBytesFtp/TEST_imageDuplicate_300x300.jpg";						
			InputStream input2 = new FileInputStream(new File(fileIn2));
		    ftpClient.storeFile("/externalftp/project/visionbytes_news/TEST_imageDuplicate_300x300.jpg", input2);
		    
		    String fileIn3 = "/home/bakerp2y/TEST/VisionBytesFtp/007VIDEO.VIDEO-TEST_imageDuplicate.xml.sem";						
			InputStream input3 = new FileInputStream(new File(fileIn3));
		    ftpClient.storeFile("/externalftp/project/visionbytes_news/007VIDEO.VIDEO-TEST_imageDuplicate.xml.sem", input3);
	        
	        //disconnect from ftp server
	        if(ftpClient.isConnected()) 
	        {
	        	ftpClient.logout();
	        	ftpClient.disconnect();
	        }
	        
			/*FTPClient ftpClient = new FTPClient();	 
			
			ftpClient.connect("localhost", 2221);
	        boolean loggedIn = ftpClient.login("bakerp2y", "pass");
	        System.out.println(loggedIn);
	        FTPFile[] files = ftpClient.listFiles("/home/bakerp2y");
	        
	        for(FTPFile file : files)
	        {
	        	System.out.println("File: " + file);	        	
	        }*/
	        
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args)
	{
		new FtpClientTest();
	}
	
	
	
	private void ftpClient1()
	{
		FTPClient ftpClient = new FTPClient();	 
		
		try
		{
			ftpClient.connect("localhost", 9981);
	        ftpClient.login("user1", "password");
	        
	        
	        /*ftpClient.connect("pubftp.abc.net.au", 21);
	        ftpClient.login("wallace", "Public8Correct");*/
	        
	        
	        //ftpClient.enterLocalPassiveMode();
	        
	       // ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
	        
	        FTPFile[] files = ftpClient.listFiles("ExternalFTP/project/visionbytes_news");
	        
	        for(FTPFile file : files)
	        {
	        	System.out.println("File: " + file);	        	
	        }
	        
	        
	        
	        InputStream in = ftpClient.retrieveFileStream("ExternalFTP/project/visionbytes_news/test.sem");
	        BufferedInputStream inbf = new BufferedInputStream(in);
	        byte buffer[] = new byte[1024];
	        int readCount;
	        byte result[] = null;
	        int length = 0;

	        while( (readCount = inbf.read(buffer)) > 0) {
	              int preLength = length;
	              length += readCount;
	              byte temp[] = new byte[result.length];
	              result = new byte[length];
	              System.arraycopy(temp,0,result,0,temp.length); 
	              System.arraycopy(buffer,0,result,preLength,readCount); 
	        }
	        
	        
	        
	        /*ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        boolean success = ftpClient.retrieveFile(filename, outputStream);
	        ftpClient.disconnect();

	        if (!success) {
	            throw new IOException("Retrieve file failed: " + filename);
	        }
	        
	        String fileContents = outputStream.toString();			
	        
	        System.out.println("File: " + fileContents);
*/	        
					
	        /*ftpClient.connect("127.0.0.1", 9981);
	        ftpClient.login("joe", "password");
	        
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	        
	        String remoteFile  = "/ExternalFTP/project/visionbytes_news/File2.xml";
	        
	        File fileUpload = new File("/home/bakerp2y/TEST/7PMb_MurderDiveCore_2805.xml");
	        InputStream inputStream = new FileInputStream(fileUpload);
	        
	        boolean done = ftpClient.storeFile(remoteFile, inputStream);
	        inputStream.close();
	        
	        System.out.println("Upload success: " + done);
	        
	        FTPFile[] files = ftpClient.listFiles();
	        
	        for(FTPFile file : files)
	        {
	        	System.out.println("File: " + file.getName());
	        	
	        }
	        
	        ftpClient.deleteFile("c:\\File2.xml");*/
	        
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				ftpClient.logout();
				ftpClient.disconnect();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

}
