package au.net.abc.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import au.net.abc.config.MockService;
import au.net.abc.utils.MockServerUtils;

@Controller
public class ServiceController
{
	@RequestMapping(value = "/service", method = RequestMethod.GET)
	public ModelAndView displayService(@RequestParam("sysId") String sysId, @RequestParam("serviceId") String serviceId)
	{	
		File file = null;
		try 
		{
			file = new File(MockServerUtils.getConfigDir() + "/" + sysId + "/" + serviceId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
        model.put("operationList", file.list());
        model.put("sysId", sysId);
        model.put("serviceId", serviceId);
	
		return new ModelAndView("service", "model", model);		
		
	}
	
	@RequestMapping(value="/addservice-form")
    public ModelAndView addService(@RequestParam("sysId") String sysId) 
    {   
		System.out.println("addservice-form:sysId" + sysId);
		
		MockService mockService = new MockService();
		((ModelMap)mockService).addAttribute("sysId", sysId);
		
		return new ModelAndView("add-service", "mockservice", mockService);
    }
	
	@RequestMapping(value="/process-addservice", method=RequestMethod.POST)
    public String processAddService(@RequestParam("sysId") String sysId, @ModelAttribute MockService mockService) 
    {
		try 
		{
			File dir = new File(MockServerUtils.getConfigDir() + "/" + sysId + "/" + mockService.getId());
			dir.mkdirs();			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
                
        return "redirect:edit-system?sysId="+sysId;
    }

}
