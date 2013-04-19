package org.sakaiproject.content.repository.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Stores an instance of a technical requirement for a Learning Object
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
@Data
public class TechnicalRequirement implements Serializable {

	private String techReqType;
	private String techReqName;
	private String techReqMinVersion;
	private String techReqMaxVersion;
	private String techReqAndOr;
	private String techReqInstallRemarks;
	private String techReqOther;
}
