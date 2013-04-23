package org.sakaiproject.content.repository.logic;

import java.io.StringWriter;

import org.sakaiproject.content.repository.model.TechnicalRequirement;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Helper class for serialising and deserialising objects
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class XMLHelper {

	/**
	 * Serialise obj to xml
	 * @param tr TechnicalRequirement
	 * @return
	 */
	protected static String serialise(TechnicalRequirement tr) {
		
		StringWriter writer = new StringWriter();
		Serializer serializer = new Persister();
		try {
			serializer.write(tr, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writer.toString();
	}
	
	/**
	 * Deserialise xml to object
	 * @param xml
	 * @return
	 */
	protected static TechnicalRequirement deserialise(String xml) {
		
		Serializer serializer = new Persister();

		try {
			TechnicalRequirement tr = serializer.read(TechnicalRequirement.class, xml);
			return tr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
