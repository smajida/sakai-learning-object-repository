package org.sakaiproject.content.repository.logic;

import java.io.StringWriter;

import org.sakaiproject.content.repository.model.LearningObject;
import org.sakaiproject.content.repository.model.TechnicalRequirement;
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
	 * @param tr TechnicalRequirement
	 * @return
	 */
	protected static String serialiseTechReq(TechnicalRequirement tr) {
		
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
	protected static TechnicalRequirementList deserialiseTechReqs(String xml) {
		
		Serializer serializer = new Persister();

		try {
			TechnicalRequirementList trl = serializer.read(TechnicalRequirementList.class, xml);
			return trl;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Serialise obj to xml
	 * @param lo LearningObject
	 * @return
	 */
	protected static String serialiseLearningObject(LearningObject lo) {
		
		StringWriter writer = new StringWriter();
		Serializer serializer = new Persister();
		try {
			serializer.write(lo, writer);
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
	protected static LearningObject deserialiseLearningObject(String xml) {
		
		Serializer serializer = new Persister();

		try {
			LearningObject lo = serializer.read(LearningObject.class, xml);
			return lo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
