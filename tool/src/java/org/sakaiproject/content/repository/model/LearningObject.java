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

	private String id;
	
	/**
	 * File properties
	 */
	private String file; //form placeholder
	private long size;
	private String stashedFilePath; //only used for initial upload
	private String mimetype;
	private String filename;
	
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

