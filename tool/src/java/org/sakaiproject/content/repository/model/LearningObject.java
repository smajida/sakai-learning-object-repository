package org.sakaiproject.content.repository.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * Data model for when uploading/editing a learning object
 * Contains actual data and bean properties for the transport of this object through its lifecycle
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
@Data
@ToString
public class LearningObject implements Serializable {

	/**
	 * ID of this resource, matches the content resource ID in CHS
	 */
	private String resourceId;
	
	/**
	 * File properties
	 */
	private String file; //form placeholder, NEVER used otherwise
	private long size;
	private String stashedFilePath; //only used for initial upload, NEVER used afterwards
	private String mimetype;
	private String filename; //only used for initial upload, NEVER used afterwards
	
	/**
	 * File meta
	 */
	private String displayName;
	private int copyrightStatus;
	private String copyrightCustomText;
	private boolean copyrightAlert;
	
	/**
	 * Access
	 */
	private String access;
	private String dateFrom;
	private String dateTo;
	private int fileStatus;
	private String publisher;
	
	/**
	 * LO details
	 */
	private String description;
	private String resourceType;
	private String environment;
	private String intendedAudience;
	private String audienceEducation;
	private String engagement;
	private String interactivity;
	private String difficulty;
	private String assumedKnowledge;
	private String learningTime;
	private String keywords;
	private String outcomes;
	private int version;
	
	/**
	 * Technical requirements
	 */
	public List<TechnicalRequirement> techReqs = new ArrayList<TechnicalRequirement>();
	
	
}

