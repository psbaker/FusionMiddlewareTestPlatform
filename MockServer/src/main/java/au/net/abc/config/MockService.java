package au.net.abc.config;

import org.springframework.ui.ModelMap;

public class MockService extends ModelMap {
	
	String sysId;
	String id;
	
	public String getSysId()
	{
		return sysId;
	}

	public void setSysId(String sysId)
	{
		this.sysId = sysId;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

}
