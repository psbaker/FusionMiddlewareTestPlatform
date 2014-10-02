package au.net.abc.utils;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class TestServerUtils
{	
	public static String getConfigDir()
	{		
		return System.getProperty("user.home") + "/testserver";		
	}
	
	//method to convert Document to String
	public static String getStringFromDocument(Document doc)
	{
		String result = "";
	    try
	    {
	    	DOMSource domSource = new DOMSource(doc);
	    	StringWriter writer = new StringWriter();
	    	StreamResult streamResult = new StreamResult(writer);
	    	TransformerFactory tf = TransformerFactory.newInstance();
	    	Transformer transformer = tf.newTransformer();
	    	transformer.setOutputProperty("omit-xml-declaration", "yes");
	    	transformer.transform(domSource, streamResult);
	    	result = writer.toString();
	    }
		catch(TransformerException e)
		{
			e.printStackTrace();
		}
	    return result;
	}

}
