package au.net.abc.controller;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

import au.net.abc.utils.MockServerUtils;

public class MockResponseControllerTest
{
	@Test
	public void testBaseControllerOnAirGetEpg()
	{
		MockResponseController controller = new MockResponseController();
		
		String testPayload = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"> " +
						"<soap:Header xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"/>"  +
							"<soapenv:Body>"  +
						      "<urn:ePGReq xmlns:urn=\"urn:abcaus.onair.sintecmedia.com\">"  +
						         "<Token>VMxaHgTzSBv9xzkzs+xf8XHgky+SShqgc2tpcsQ/h1G+ZyTT5AXam5EpoRqD+A==</Token>"  +
						         "<ChannelID>9676</ChannelID>"  +
						         "<DayTypeID>1307001</DayTypeID>"  +
						         "<FromDate>2013-02-01</FromDate>"  +
						         "<ToDate>2013-02-01</ToDate>"  +
						         "<FullSchedule>true</FullSchedule>"  +
						      "</urn:ePGReq>"  +
						   "</soapenv:Body>"  +
						"</soapenv:Envelope>";
		
		String testConfig = "<mockserver-config>" +
							"<system>" +
								"<system-id>onAir</system-id>" +
								"<system-uri>/wss/services</system-uri>" +
								"<service>" +
									"<service-id>getEPGService</service-id>" +
									"<service-uri>/getEPGService</service-uri>" +
									"<operation>" +
										"<operation-id>EPGRequest</operation-id>" +
										"<token>ePGReq</token>" +
									"</operation>" +
								"</service>" +
							"</system>" +
						"</mockserver-config>";
		
		InputStream inputStream = new ByteArrayInputStream(testConfig.getBytes());
				
		String response = controller.getMockResponse("/wss/services/getEPGService", testPayload, inputStream).getPayload();
		
		assertEquals(response, MockServerUtils.getConfigDir() + "/onAir/getEPGService/EPGRequest-response.xml");
		
		//Assert.assertThat(response, Matchers.containsString("EPGResponse"));		
	}

}
