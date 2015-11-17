package au.net.abc.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	
	@RequestMapping(value = "/delete-operation", method = RequestMethod.GET)
	public @ResponseBody void deleteOperation(@RequestParam("sysId") String sysId, @RequestParam("serviceId") String serviceId, @RequestParam("operationId") String operationId, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			File file = new File(MockServerUtils.getConfigDir() + "/" + sysId + "/" + serviceId + "/" + operationId);
			
			String text = "";
			response.setContentType("text/html");

			if(file.exists())
			{
				file.delete();
				text = "Operation '" + operationId + "' has been successfully deleted.";				
			}
			else 
			{
				text = "Cannot delete operation '" + operationId + "'. File does not exist.";
			}
			
			PrintWriter out = response.getWriter();
			out.write(text);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private String loadOperation(String sysId, String serviceId, String operationId)
	{
		String result = "";
		
		try
		{				
			result = FileUtils.readFileToString(new File(MockServerUtils.getConfigDir() + "/" + sysId + "/" + serviceId + "/" + operationId));
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
