package au.net.abc.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.w3c.dom.Document;

import au.net.abc.utils.TestCaseConstants.FTPDetails;

public class TestServerUtils
{	
	public static String getBmsConfigDir()
	{		
		return getConfigDir() + "/bms";
	}
	
	public static String getWcmsConfigDir()
	{		
		return getConfigDir() + "/wcms";
	}
	
	public static String getGenericConfigDir()
	{		
		return getConfigDir() + "/generic";
	}
	
	public static String getTestDataConfigDir()
	{		
		return System.getProperty("user.dir") + "/../../configs/data";
	}

	
	public static String getConfigDir()
	{		
		return System.getProperty("user.dir") + "/../../configs/testserver";
	}
	
	//method to convert Document to String
	public static String getStringFromDocument(Document doc)
	{
		String result = "";
	    try
	    {
	    	DOMSource domSource = new DOMSource(doc);
	    	StringWriter writer = new StringWriter();
	    	StreamResult streamResult = new StreamResult(writer);
	    	TransformerFactory tf = TransformerFactory.newInstance();
	    	Transformer transformer = tf.newTransformer();
	    	transformer.setOutputProperty("omit-xml-declaration", "yes");
	    	transformer.transform(domSource, streamResult);
	    	result = writer.toString();
	    }
		catch(TransformerException e)
		{
			e.printStackTrace();
		}
	    return result;
	}
	
	
	public static FTPClient buildFTPClient() throws Exception {
		FTPClient ftpClient = new FTPClient();
		ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
	    int reply;
	    ftpClient.connect(FTPDetails.FTP_HOST, FTPDetails.FTP_PORT);
	    reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
        	ftpClient.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftpClient.login(FTPDetails.FTP_USER, FTPDetails.FTP_PASSWORD);
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        return ftpClient;
	}
}
