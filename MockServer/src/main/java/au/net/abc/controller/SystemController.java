package au.net.abc.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import au.net.abc.config.MockSystem;
import au.net.abc.utils.MockServerUtils;

@Controller
public class SystemController
{
	@RequestMapping(value = "/edit-system", method = RequestMethod.GET)
	public ModelAndView editSystem(@RequestParam("sysId") String sysId)
	{		
		String[] directories = new String[0];
		
		try 
		{
			File file = new File(MockServerUtils.getConfigDir() + "/" + sysId);
			directories = file.list(new FilenameFilter() {
			  @Override
			  public boolean accept(File dir, String name) {
			    return new File(dir, name).isDirectory();
			  }
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
        model.put("serviceList", directories);
        model.put("sysId", sysId);
	
		return new ModelAndView("edit-system", "model", model);			
	}
	
	@RequestMapping(value="/addsystem-form")
    public ModelAndView addSystem() 
    {   
		return new ModelAndView("add-system", "system-entity", new MockSystem());
    }
	
	@RequestMapping(value="/process-addsystem", method=RequestMethod.POST)
    public String processSysconfig(@ModelAttribute MockSystem mockSystem) 
    {
		try 
		{
			System.out.println("New System Id:" + mockSystem.getId());
			File dir = new File(MockServerUtils.getConfigDir() + "/" + mockSystem.getId());
			boolean success = dir.mkdirs();
			System.out.println("Success: " + success);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
                
        return "redirect:index";
    }

}
