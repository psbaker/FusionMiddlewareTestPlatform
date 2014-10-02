package au.net.abc.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import au.net.abc.utils.MockServerUtils;

@Controller
public class BaseController
{
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView loadIndexPage(HttpServletRequest request, HttpServletResponse response)
	{
		String[] directories = new String[0];
		
		try 
		{
			File file = new File(MockServerUtils.getConfigDir());
			directories = file.list(new FilenameFilter() 
			{
			  @Override
			  public boolean accept(File dir, String name) 
			  {
			    return new File(dir, name).isDirectory();
			  }
			});			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
        model.put("systemList", directories);
	
		return new ModelAndView("index", "model", model);
	}	
}