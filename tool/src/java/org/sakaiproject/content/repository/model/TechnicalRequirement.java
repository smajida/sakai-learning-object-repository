package org.sakaiproject.content.repository.model;

import java.io.Serializable;

import lombok.Data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Stores an instance of a technical requirement for a Learning Object
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
@Data
@Root(name="techreq")
public class TechnicalRequirement implements Serializable {

	@Element(name="type", required=false)
	private String techReqType;
	
	@Element(name="name", required=false)
	private String techReqName;
	
	@Element(name="minv", required=false)
	private String techReqMinVersion;
	
	@Element(name="maxv", required=false)
	private String techReqMaxVersion;
	
	@Element(name="andor", required=false)
	private String techReqAndOr;
	
	@Element(name="notes", required=false)
	private String techReqInstallRemarks;
	
	@Element(name="other", required=false)
	private String techReqOther;
}
