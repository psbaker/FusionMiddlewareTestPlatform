package au.net.abc.controller;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestcaseController
{
	@Test
	public void testTestCaseController()
	{
		TestCaseController controller = new TestCaseController();
				
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		
		controller.runTestcase("BMS - EPG","ScheduleEPGRequest_ABC1_ABC2_ABC3_ABC4_Internet.xml", request, response);
		
		assertEquals(response.getStatus(), 200);		
		
	}

}
