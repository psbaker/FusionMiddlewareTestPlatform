package au.net.abc.config;

import org.springframework.ui.ModelMap;

public class Testcase extends ModelMap
{       
    String testXml;
    String testcaseId;
    String projectId;
    
	public String getTestXml()
	{
		return testXml;
	}
	public void setTestXml(String testXml)
	{
		this.testXml = testXml;
	}
	
	public String getTestcaseId()
	{
		return testcaseId;
	}
	
	public void setTestcaseId(String testcaseId)
	{
		this.testcaseId = testcaseId;
	}
	
	public String getProjectId()
	{
		return projectId;
	}
	
	public void setProjectId(String projectId)
	{
		this.projectId = projectId;
	}
}