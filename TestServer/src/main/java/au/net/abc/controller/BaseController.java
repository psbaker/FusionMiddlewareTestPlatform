package au.net.abc.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import au.net.abc.utils.TestServerUtils;

@Controller
public class BaseController
{
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView loadIndexPage(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> model = new HashMap<String, Object>();
		
        model.put("bmsProjectList", getSubDirectories(TestServerUtils.getBmsConfigDir()));        
        model.put("wcmsProjectList", getSubDirectories(TestServerUtils.getWcmsConfigDir()));
        model.put("genericProjectList", getSubDirectories(TestServerUtils.getGenericConfigDir()));
	
		return new ModelAndView("index", "model", model);
	}	
	
	private String[] getSubDirectories(final String directory)
	{
		String[] result = new String[0];
		
		try 
		{
			File file = new File(directory);
			result = file.list(new FilenameFilter() 
			{
			  @Override
			  public boolean accept(File dir, String name) 
			  {
				  if(directory.equals(TestServerUtils.getConfigDir()))
				  {
					  boolean res = new File(dir, name).isDirectory() && !name.equals("Scheduled Jobs");
					  return res;
				  }
				  else 
				  {
					  boolean res = new File(dir, name).isDirectory();
					  return res;
				  }
			  }
			});			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Arrays.sort(result);
		
		return result;		
	}
}