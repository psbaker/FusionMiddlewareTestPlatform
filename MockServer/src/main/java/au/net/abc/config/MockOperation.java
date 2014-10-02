package au.net.abc.config;

import org.springframework.ui.ModelMap;

public class MockOperation extends ModelMap
{
	private String id;
	private String sysId;
	private String serviceId;
	
	private String config;
	
	public String getConfig()
	{
		return config;
	}
	public void setConfig(String config)
	{
		this.config = config;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getSysId()
	{
		return sysId;
	}
	public void setSysId(String sysId)
	{
		this.sysId = sysId;
	}
	public String getServiceId()
	{
		return serviceId;
	}
	public void setServiceId(String serviceId)
	{
		this.serviceId = serviceId;
	}

}
