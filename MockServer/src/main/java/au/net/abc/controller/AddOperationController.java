package au.net.abc.controller;

import java.io.File;
import java.io.FileWriter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import au.net.abc.config.MockOperation;
import au.net.abc.utils.MockServerUtils;

@Controller
public class AddOperationController
{
	
	@RequestMapping(value="/addoperation-form")
    public ModelAndView addOperation(@RequestParam("sysId") String sysId, @RequestParam("serviceId") String serviceId) 
    {   
		System.out.println("addoperation-form:sysId" + sysId);
		System.out.println("addoperation-form:serviceId" + serviceId);
		
		MockOperation mockOperation = new MockOperation();
		((ModelMap)mockOperation).addAttribute("sysId", sysId);
		((ModelMap)mockOperation).addAttribute("serviceId", serviceId);
				
		return new ModelAndView("add-operation", "mockoperation", mockOperation);
    }
	
	@RequestMapping(value="/process-addoperation", method=RequestMethod.POST)
    public String processAddService(@RequestParam("sysId") String sysId, @RequestParam("serviceId") String serviceId, @ModelAttribute MockOperation mockOperation) 
    {
		try 
		{
			System.out.println("Writing file: " + MockServerUtils.getConfigDir() + "/" + sysId + "/" + serviceId + "/" + mockOperation.getId() + "-response.xml");
			File file = new File(MockServerUtils.getConfigDir() + "/" + sysId + "/" + serviceId + "/" + mockOperation.getId() + "-response.xml");
			FileWriter writer = new FileWriter(file, false);
			writer.write(mockOperation.getConfig());
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
                
        return "redirect:service?sysId="+sysId+"&serviceId="+serviceId;
        
    }
	

}
