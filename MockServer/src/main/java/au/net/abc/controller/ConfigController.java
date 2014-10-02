package au.net.abc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;

import au.net.abc.config.MockServerConfig;
import au.net.abc.utils.MockServerUtils;

@Controller
public class ConfigController 
{ 
	@RequestMapping(value="/mockserverconfig-form")
    public ModelAndView sysConfigPage() 
    {         
		MockServerConfig sysConfig = new MockServerConfig();
		sysConfig.setConfig(loadSystemConfig());
		
        return new ModelAndView("mockserverconfig-page", "sysconfig-entity", sysConfig);
    }
	
	@RequestMapping(value="/process-mockserverconfig", method=RequestMethod.POST)
    public String processSysconfig(@ModelAttribute MockServerConfig systemConfig) 
    {
		saveSystemConfig(systemConfig.getConfig());
                
        return "redirect:mockserverconfig-form";
    }
   
	private String loadSystemConfig()
	{
		String result = "";
		
		System.out.println("XML Config:" + MockServerUtils.getXmlConfigPath());
		
		try
		{
			InputStream inputStream = new FileInputStream(new File(MockServerUtils.getXmlConfigPath()));
			
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
	
	private void saveSystemConfig(String systemConfig)
	{
		try 
		{
			File file = new File(MockServerUtils.getXmlConfigPath());
			FileWriter writer = new FileWriter(file, false);
			writer.write(systemConfig);
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
