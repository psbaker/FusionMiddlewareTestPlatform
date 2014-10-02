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
public class MockResponseController
{	
	@RequestMapping(value = "/systems/**", method = RequestMethod.POST)
	public @ResponseBody void doMockResponse(@RequestBody String requestPayload, HttpServletRequest request, HttpServletResponse response)
	{
		try 
		{			
			String requestUri = request.getRequestURI();
		
			System.out.println("URI: " + requestUri);
		
			System.out.println("Request Payload: " + requestPayload);
		
			InputStream inputStream = new FileInputStream(new File(MockServerUtils.getXmlConfigPath()));
		
			requestUri = requestUri.substring("/MockServerWebApp/systems".length());
			
			MockResponse mockResponse = getMockResponse(requestUri, requestPayload, inputStream);
			String mockResponsePayload = mockResponse.getPayload();
			String mockReponseHttpCode = mockResponse.getHttpCode();
		
			InputStream inputStream2 = new FileInputStream(new File(mockResponsePayload));
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(inputStream2);		
		
			response.setContentType("text/xml");
			response.setStatus(Integer.valueOf(mockReponseHttpCode));
			String xml = MockServerUtils.getStringFromDocument(document);
			PrintWriter out = response.getWriter();
			out.write(xml);
		}
		catch(Exception e ) 
		{
			e.printStackTrace();
		}
	}	
	
	public MockResponse getMockResponse(String requestUri, String requestPayload, InputStream inputStream)
	{
		String responsePayload = MockServerUtils.getConfigDir() + "/default-request-response.xml";
		String httpCode = "200";
		
		try 
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(inputStream);
			document.getDocumentElement().normalize();

			System.out.println("Root element: " + document.getDocumentElement().getNodeName());

			NodeList systemNodeList = document.getElementsByTagName("system");

			for (int i = 0; i < systemNodeList.getLength(); i++) 
			{
				Node systemNode = systemNodeList.item(i);

				if (systemNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element systemElement = (Element) systemNode;

					String systemId = systemElement.getElementsByTagName("system-id").item(0).getTextContent();
					String systemUri = systemElement.getElementsByTagName("system-uri").item(0).getTextContent();

					System.out.println("System Id: " + systemId);
					System.out.println("System URI: " + systemUri);

					if (requestUri.startsWith(systemUri)) 
					{
						System.out.println("Found System Match: " + systemId);

						NodeList serviceNodeList = systemElement.getElementsByTagName("service");

						for (int j = 0; j < serviceNodeList.getLength(); j++)
						{
							Node serviceNode = serviceNodeList.item(j);

							if (serviceNode.getNodeType() == Node.ELEMENT_NODE) 
							{
								Element serviceElement = (Element) serviceNode;
								
								String serviceId = serviceElement.getElementsByTagName("service-id").item(0).getTextContent();
								String serviceUri = serviceElement.getElementsByTagName("service-uri").item(0).getTextContent();
								
								System.out.println("Service Id:" + serviceId);
								System.out.println("Service URI:" + serviceUri);
								
								System.out.println("Request URI: " + requestUri);								
								
								if (requestUri.endsWith(serviceUri))
								{
									System.out.println("Found Service Match: " + serviceId);
									
									NodeList operationNodeList = serviceElement.getElementsByTagName("operation");
									
									for (int k = 0; k < operationNodeList.getLength(); k++)
									{
										Node operationNode = operationNodeList.item(k);
										
										if (operationNode.getNodeType() == Node.ELEMENT_NODE) 
										{
											Element operationElement = (Element) operationNode;
											
											String operationId = operationElement.getElementsByTagName("operation-id").item(0).getTextContent();
											String token = operationElement.getElementsByTagName("token").item(0).getTextContent();
																						
											NodeList nodeList = operationElement.getElementsByTagName("http-code");
											if(nodeList.getLength() > 0) {
												httpCode = nodeList.item(0).getTextContent();
											}											
											
											Pattern p = Pattern.compile(token, Pattern.CASE_INSENSITIVE+Pattern.LITERAL);
											Matcher m = p.matcher(requestPayload);
											
											if(m.find())
											{
												System.out.println("Found Operation Match: " + operationId);
												
												responsePayload = MockServerUtils.getConfigDir() + "/" + systemId + "/" + serviceId + "/" + operationId + "-response.xml";
												
												break;
											}											
										}										
									}											
								}
							}
						}
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return new MockResponse(responsePayload, httpCode);
	}
	
	public class MockResponse
	{
		String payload;
		String httpCode;
		
		MockResponse(String payload, String httpCode)
		{
			this.payload = payload;
			this.httpCode = httpCode;
		}

		public String getPayload()
		{
			return payload;
		}

		public String getHttpCode()
		{
			return httpCode;
		}
	}
}
