package au.net.abc.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import au.net.abc.config.Testsuite;
import au.net.abc.utils.TestServerUtils;

@Controller
public class ProjectController
{	
	@RequestMapping(value = "/project", method = RequestMethod.GET)
	public ModelAndView displayProject(@RequestParam("domain") String domain, @RequestParam("projectId") String projectId)
	{	
		File file = null;
		String[] requestFiles = new String[0];
		
		try 
		{
			file = new File(TestServerUtils.getConfigDir() + "/" + domain + "/" + projectId);
			requestFiles = file.list(new FilenameFilter() {
				  @Override
				  public boolean accept(File dir, String name) {
				    return name.endsWith(".xml");
				  }
				});
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Arrays.sort(requestFiles);
		
		Map<String, Object> model = new HashMap<String, Object>();
        model.put("testcases", requestFiles);
        model.put("domain", domain);
        model.put("projectId", projectId);
	
		return new ModelAndView("project", "model", model);	
	}
	
	@RequestMapping(value="/addtestsuite-form")
    public ModelAndView addTestsuite() 
    {   
		return new ModelAndView("add-testsuite", "testsuite-entity", new Testsuite());
    }
	
	@RequestMapping(value="/process-addtestsuite", method=RequestMethod.POST)
    public String processAddTestsuite(@ModelAttribute Testsuite testsuite) 
    {
		try 
		{
			System.out.println("New Testsuite Domain:" + testsuite.getDomain());
			System.out.println("New Testsuite Id:" + testsuite.getId());
			File dir = new File(TestServerUtils.getConfigDir() + "/" + testsuite.getDomain() + "/" + testsuite.getId());
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
