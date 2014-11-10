package au.net.abc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;

import au.net.abc.config.OperationConfig;
import au.net.abc.utils.MockServerUtils;

@Controller
public class OperationController
{
	
	@RequestMapping(value = "/operationconfig-form", method = RequestMethod.GET)
	public ModelAndView displayService(@RequestParam("sysId") String sysId, @RequestParam("serviceId") String serviceId, @RequestParam("operationId") String operationId)
	{			
		String operation = loadOperation(sysId, serviceId, operationId);
		
		OperationConfig operationConfig = new OperationConfig();
		operationConfig.setConfig(operation);		
		
		operationConfig.addAttribute("sysId", sysId);
		((ModelMap)operationConfig).addAttribute("serviceId", serviceId);
		((ModelMap)operationConfig).addAttribute("operationId", operationId);		
		
		return new ModelAndView("operationconfig-page", "operationconfig", operationConfig);	
		
	}
	
	@RequestMapping(value="/process-operationconfig", method=RequestMethod.POST)
    public String processSysconfig(@ModelAttribute OperationConfig operationConfig, @RequestParam("sysId") String sysId, @RequestParam("serviceId") String serviceId, @RequestParam("operationId") String operationId) 
    {
		String location = MockServerUtils.getConfigDir() + "/" + sysId + "/" + serviceId + "/" + operationId;
		saveOperation(location, operationConfig.getConfig());
                
        return "redirect:operationconfig-form?sysId="+sysId+"&serviceId="+serviceId+"&operationId="+operationId;
    }
	
	private String loadOperation(String sysId, String serviceId, String operationId)
	{
		String result = "";
		
		try
		{	
			
			/* InputStream is = new FileInputStream(new File(MockServerUtils.getConfigDir() + "/" + sysId + "/" + serviceId + "/" + operationId));  
			Reader reader = new InputStreamReader(is, "UTF-16"); 
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource source = new InputSource(reader);  
			Document document = builder.parse(source); */  
			
			
			/*String xml = new Scanner(new File(MockServerUtils.getConfigDir() + "/" + sysId + "/" + serviceId + "/" + operationId)).useDelimiter("\\Z").next();
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xml)));  */
			
			/* OutputFormat format = new OutputFormat(document);

			if (document.getXmlEncoding() != null) {
			  format.setEncoding(document.getXmlEncoding());
			}

			format.setLineWidth(100);
			format.setIndenting(true);
			format.setIndent(5);
			Writer out = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.serialize(document);
			result = out.toString();
			
			return result; */
			
			
			InputStream inputStream = new FileInputStream(new File(MockServerUtils.getConfigDir() + "/" + sysId + "/" + serviceId + "/" + operationId));		
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(inputStream);		
					
			result = MockServerUtils.getStringFromDocument(document); 

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;

	}
	
	private void saveOperation(String fileLocation, String response)
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
	

}
