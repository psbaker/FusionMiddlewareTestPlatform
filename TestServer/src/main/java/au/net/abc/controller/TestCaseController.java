package au.net.abc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import au.net.abc.config.Testcase;
import au.net.abc.utils.TestServerUtils;

@Controller
public class TestCaseController
{
	@RequestMapping(value="/addtestcase-form")
    public ModelAndView addTestcase(@RequestParam("domain") String domain, @RequestParam("projectId") String projectId) 
    {   
		Testcase testcase = new Testcase();
		
		testcase.setProjectId(projectId);
		testcase.setDomain(domain);
		
		return new ModelAndView("add-testcase", "testcase-entity", testcase);
    }
	
	@RequestMapping(value="/process-addtestcase", method=RequestMethod.POST)
    public String processAddTestsuite(@ModelAttribute Testcase testcase) 
    {
		try 
		{
			System.out.println("Adding testcase: " + TestServerUtils.getConfigDir() + "/" + testcase.getDomain() + "/" + testcase.getProjectId() + "/" + testcase.getTestcaseId() + ".xml");
			File file = new File(TestServerUtils.getConfigDir() + "/" + testcase.getDomain() + "/" + testcase.getProjectId() + "/" + testcase.getTestcaseId() + ".xml");
			FileWriter writer = new FileWriter(file, false);
			writer.write(testcase.getTestXml());
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
                
        return "redirect:project?domain="+testcase.getDomain()+"&projectId="+testcase.getProjectId();
    }
	
	@RequestMapping(value = "/testcase", method = RequestMethod.GET)
	public String testcase(@RequestParam("domain") String domain, @RequestParam("projectId") String projectId, @RequestParam("testcaseId") String testcaseId, Model m)
	{
		String testXml = getTestXml(domain, projectId, testcaseId);		
				
        Testcase testcase = new Testcase();
        testcase.setTestXml(testXml);
        testcase.setProjectId(projectId);
        testcase.setTestcaseId(testcaseId);
        testcase.setDomain(domain);
        
        m.addAttribute("testcase", testcase);
        m.addAttribute("testcaseId", testcaseId);
                
        return "testcase";        	
	}
	
	@RequestMapping(value = "/run-testcase", method = RequestMethod.GET)
	public @ResponseBody void runTestcase(@RequestParam String domain, @RequestParam String projectId, @RequestParam String testcaseId, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{			
			System.out.println("Run Testcase Called. ");
			System.out.println("Domain: " + domain);
			System.out.println("ProjectId: " + projectId);
			System.out.println("TestcaseId: " + testcaseId);
			
			String testResult = executeTest(domain, projectId, testcaseId);
			
			System.out.println("TestResult: " + testResult);
			
			response.setContentType("application/xml");
			String xml = "<test-result>" + testResult + "</test-result>";
			PrintWriter out = response.getWriter();
			out.write(xml);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/process-testcase", method = RequestMethod.POST)
	public String testcase(@ModelAttribute Testcase testcase, @RequestParam String action, Model m)
	{
		String result = "";
				
		if(action.equals("Save"))
		{
			System.out.println("Saving Testcase: " + testcase.getTestcaseId());
			String location = TestServerUtils.getConfigDir() + "/" + testcase.getDomain() + "/" + testcase.getProjectId() + "/" + testcase.getTestcaseId();
			String xml = testcase.getTestXml();
			saveTest(location, xml);
		    result = "redirect:testcase?domain="+testcase.getDomain()+"&projectId="+testcase.getProjectId()+"&testcaseId="+testcase.getTestcaseId();    
		}
		else if(action.equals("Execute"))
		{
			System.out.println("Executing Testcase: " + testcase.getTestcaseId());			
			String testResult = executeTest(testcase.getDomain(), testcase.getProjectId(), testcase.getTestcaseId());
			m.addAttribute("result", testResult);
			result = "testcase-result";
		}
		return result;
	}
	
	private void saveTest(String fileLocation, String response)
	{
		try
		{
			File file = new File(fileLocation);
			FileWriter writer = new FileWriter(file, false);
			writer.write(response);
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private String executeTest(String domain, String projectId, String testcaseId)
	{
		String result = "Failed";
		try 
		{	
			InputStream inputStream = new FileInputStream(new File(TestServerUtils.getConfigDir() + "/" + domain + "/" + projectId + "/" + testcaseId));			
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document testXmlDocument = documentBuilder.parse(inputStream);
			
			boolean isTestSuite = testXmlDocument.getElementsByTagName("testsuite").getLength() > 0;
			
			System.out.println("IsTestSuite: " + isTestSuite);
			
			if(isTestSuite)
			{
				result = executeTestSuite(testXmlDocument, domain, projectId);				
			}	
			else 
			{			
				result = executeTestCase(testXmlDocument);				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	private String executeTestSuite(Document xmlDoc, String domain, String projectId) throws Exception
	{
		String result = "Failed";
		NodeList testcaseNodes = xmlDoc.getElementsByTagName("testcase");
		
		for (int j = 0; j < testcaseNodes.getLength(); j++)
		{
			String testCaseId = testcaseNodes.item(j).getTextContent();
			
			System.out.println("Execute Test Case: " + domain + " " + projectId + " " + testCaseId);				
			
			InputStream inputStream = new FileInputStream(new File(TestServerUtils.getConfigDir() + "/" + domain + "/" + projectId + "/" + testCaseId + ".xml"));
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document testXmlDocument = documentBuilder.parse(inputStream);
			
			result = executeTestCase(testXmlDocument);
			
			if("Failed".equals(result))
			{
				return result;
				
			}
		}
		return result;		
	}
	
	private String executeTestCase(Document testXmlDocument) throws Exception
	{
		String protocol = testXmlDocument.getElementsByTagName("protocol").item(0).getTextContent();
		
		if("http".equals(protocol))
		{
			return executeHttpTestcase(testXmlDocument);
		}
		else if("ftp".equals(protocol))
		{
			return executeFtpTestcase(testXmlDocument);
		}
		else
		{
			return executeFileTestcase(testXmlDocument);
		}		
	}
	
	private String executeFtpTestcase(Document testXmlDocument) throws Exception
	{
		String result = "Failed";
		
		String drop = testXmlDocument.getElementsByTagName("drop").item(0).getTextContent();
		String validate = testXmlDocument.getElementsByTagName("validate").item(0).getTextContent();

		Map<String, String> fileInNodes = new HashMap<String, String>();
		
		int fileInCount = testXmlDocument.getElementsByTagName("file").getLength();
		
		//put files to copy into map, 'order' is the key
		for(int i=0; i <fileInCount; i++)
		{
			Node fileInNode = testXmlDocument.getElementsByTagName("file").item(i);
		
			String fileIn = fileInNode.getTextContent();
			String order = fileInNode.getAttributes().getNamedItem("order").getNodeValue();
			
			fileInNodes.put(order, fileIn);
		}
		
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
		
        //copy files according to order
        for(int i=1; i <=fileInCount; i++)
		{
			String fileIn = fileInNodes.get(String.valueOf(i));
			String fileInName = fileIn.substring(fileIn.lastIndexOf("/"));		
			
			InputStream input = new FileInputStream(new File(fileIn));
		    ftpClient.storeFile(drop + fileInName, input);			
		}
        
        //disconnect from ftp server
        if(ftpClient.isConnected()) 
        {
        	ftpClient.logout();
        	ftpClient.disconnect();
        }

		Thread.sleep(20000);
		
		File outFile = new File(validate);
		
		if(outFile.exists())
		{
			result = "Passed";
		}	
		
		return result;
	}
	
	private String executeFileTestcase(Document testXmlDocument) throws Exception
	{
		String result = "Failed";
				
		String drop = testXmlDocument.getElementsByTagName("drop").item(0).getTextContent();
		String validate = testXmlDocument.getElementsByTagName("validate").item(0).getTextContent();
		
		Map<String, String> fileInNodes = new HashMap<String, String>();
		
		int fileInCount = testXmlDocument.getElementsByTagName("file").getLength();
		
		//put files to copy into map, 'order' is the key
		for(int i=0; i <fileInCount; i++)
		{
			Node fileInNode = testXmlDocument.getElementsByTagName("file").item(i);
		
			String fileIn = fileInNode.getTextContent();
			String order = fileInNode.getAttributes().getNamedItem("order").getNodeValue();
			
			fileInNodes.put(order, fileIn);
		}
		
		//copy files according to order
		for(int i=1; i <=fileInCount; i++)
		{
			String fileIn = fileInNodes.get(String.valueOf(i));
					
			File srcFile = new File(fileIn);
			File destFile = new File(drop);
			
			FileUtils.copyFileToDirectory(srcFile, destFile);			
		}
		
		Thread.sleep(10000);
		
		File outFile = new File(validate);
		
		if(outFile.exists())
		{
			result = "Passed";
		}	
		
		return result;
	}
	
	private String executeHttpTestcase(Document testXmlDocument) throws Exception
	{
		String requestXml = testXmlDocument.getElementsByTagName("request").item(0).getTextContent();
		String endpoint = testXmlDocument.getElementsByTagName("url").item(0).getTextContent();
		String xpathStr = testXmlDocument.getElementsByTagName("validate-response").item(0).getTextContent();
		
		boolean doValidateFile = testXmlDocument.getElementsByTagName("validate-file").getLength() > 0;
		
		String validateFileStr = "";
		if (doValidateFile)
		{	
			validateFileStr = testXmlDocument.getElementsByTagName("validate-file").item(0).getTextContent();
			
			/* Cleanup files to validate before executing test-case */
			File fileValidate = new File(validateFileStr);		
			if(fileValidate.exists())  
			{
				fileValidate.delete();
			}
		}
		
		boolean doValidateFTP = testXmlDocument.getElementsByTagName("validate-ftp-upload").getLength() > 0;
		
		String validateFTPStr = "";
		if (doValidateFTP)
		{	
			validateFTPStr = testXmlDocument.getElementsByTagName("validate-ftp-upload").item(0).getTextContent();
			
			/* Cleanup files to validate before executing test-case */
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
			
	        //remove files from ftp server
	        ftpClient.deleteFile(validateFTPStr);
	        
	        //disconnect from ftp server
	        if(ftpClient.isConnected()) 
	        {
	        	ftpClient.logout();
	        	ftpClient.disconnect();
	        }
		} 
		
		boolean doValidateSMTP = testXmlDocument.getElementsByTagName("verify-email").getLength() > 0;
		
		String validateSMTPStr = "";
		if (doValidateSMTP)
		{
			validateSMTPStr = testXmlDocument.getElementsByTagName("verify-email").item(0).getTextContent();
			
			/* Cleanup smtp files to validate before executing test-case */
			File smtpValidate = new File(validateSMTPStr);		
			if(smtpValidate.exists())  
			{
				smtpValidate.delete();
			}
		}
		
		HttpClient client = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(endpoint);
		//fixing bugs because Spring framework WS doesnt support text/plain
		httpPost.addHeader("Content-Type", "text/xml");
		
		StringEntity sre = new StringEntity(requestXml);
		httpPost.setEntity(sre);
		
		HttpResponse response = client.execute(httpPost);
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(response.getEntity().getContent());
		
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(xpathStr);
		
		NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
		
		boolean outFileExists = true;
		if (doValidateFile)
		{
			File outFile = new File(validateFileStr);
			outFileExists = outFile.exists();
		}
		
		boolean ftpFileUploaded = true;
		if(doValidateFTP)
		{
			validateFTPStr = testXmlDocument.getElementsByTagName("validate-ftp-upload").item(0).getTextContent();
			
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
			
	        //retrieve file from ftp server
	        InputStream fileStream = ftpClient.retrieveFileStream(validateFTPStr);
	        int replyCode = ftpClient.getReplyCode();
	        if(fileStream == null || replyCode == 550)
	        {
	        	ftpFileUploaded = false;
	        }
	        
	        //disconnect from ftp server
	        if(ftpClient.isConnected()) 
	        {
	        	ftpClient.logout();
	        	ftpClient.disconnect();
	        }
		}
		
		boolean emailDelivered = true;
		if(doValidateSMTP)
		{
			File outFile = new File(validateSMTPStr);
			emailDelivered = outFile.exists();
		}
		
		System.out.println("OutFileExists:"+outFileExists);
		System.out.println("ftpFileUploaded:"+ftpFileUploaded);
		System.out.println("emailDelivered:"+emailDelivered);
				
		return (nl.getLength() > 0) && outFileExists && ftpFileUploaded && emailDelivered ? "Passed" : "Failed";
	}
	
	private String getTestXml(String domain, String projectId, String testcaseId)
	{
		String result = "";
		
		try
		{
			InputStream inputStream = new FileInputStream(new File(TestServerUtils.getConfigDir() + "/" + domain + "/" + projectId + "/" + testcaseId));
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(inputStream);		
					
			result = TestServerUtils.getStringFromDocument(document);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}	
	
}