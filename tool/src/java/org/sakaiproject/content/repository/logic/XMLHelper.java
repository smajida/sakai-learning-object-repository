package org.sakaiproject.content.repository.logic;

import java.io.StringWriter;

import org.sakaiproject.content.repository.model.ChangeHistory;
import org.sakaiproject.content.repository.model.LearningObject;
import org.sakaiproject.content.repository.model.TechnicalRequirementList;
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
	 * @param o Object
	 * @return
	 */
	protected static String serialiseObject(Object o) {
		
		StringWriter writer = new StringWriter();
		Serializer serializer = new Persister();
		try {
			serializer.write(o, writer);
			
			System.out.println("Serialised object of class: " + o.getClass() + " to XML:\n" + writer.toString());

			return writer.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Deserialise xml to object
	 * @param xml
	 * @return
	 */
	protected static TechnicalRequirementList deserialiseTechReqs(String xml) {
		
		System.out.println("deserialiseTechReqs:\n" + xml);
		
		Serializer serializer = new Persister();

		try {
			TechnicalRequirementList trl = serializer.read(TechnicalRequirementList.class, xml);
			return trl;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new TechnicalRequirementList(); //dont return null, it blows up ;)
	}
	
	
	/**
	 * Deserialise xml to object
	 * @param xml
	 * @return
	 */
	protected static LearningObject deserialiseLearningObject(String xml) {
		
		System.out.println("deserialiseLearningObject:\n" + xml);
		
		Serializer serializer = new Persister();

		try {
			LearningObject lo = serializer.read(LearningObject.class, xml);
			return lo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Deserialise xml to object
	 * @param xml
	 * @return
	 */
	protected static ChangeHistory deserialiseChangeHistory(String xml) {
		
		System.out.println("deserialiseChangeHistory:\n" + xml);

		
		Serializer serializer = new Persister();

		try {
			ChangeHistory ch = serializer.read(ChangeHistory.class, xml);
			return ch;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
