package au.net.abc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import au.net.abc.utils.MockServerUtils;

@Controller
public class RSSFeedController
{	
	@RequestMapping(value = "/rssFeed/**", method = RequestMethod.POST)
	public @ResponseBody void doRSSFeed(HttpServletRequest request, HttpServletResponse response)
	{
		try 
		{			
			String requestUri = request.getRequestURI();
		
			System.out.println("URI: " + requestUri);
		
			requestUri = requestUri.substring("/MockServerWebApp".length());
			
			String mockRssPayload = MockServerUtils.getConfigDir() + requestUri;
		
			InputStream inputStream2 = new FileInputStream(new File(mockRssPayload));
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(inputStream2);		
		
			response.setContentType("text/xml");
			response.setStatus(200);
			String xml = MockServerUtils.getStringFromDocument(document);
			PrintWriter out = response.getWriter();
			out.write(xml);
		}
		catch(Exception e ) 
		{
			e.printStackTrace();
		}
	}	
}
