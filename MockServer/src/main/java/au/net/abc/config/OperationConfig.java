package au.net.abc.config;

import org.springframework.ui.ModelMap;

public class OperationConfig extends ModelMap
{
	String config;

	public String getConfig()
	{
		return config;
	}

	public void setConfig(String config)
	{
		this.config = config;
	}
	
	

}
