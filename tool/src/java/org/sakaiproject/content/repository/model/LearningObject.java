package org.sakaiproject.content.repository.model;

import java.io.Serializable;

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
	
	private int status = 0; //status of the resource in the repository. 0=doesnt exist, 1=exists and ok, 2=errors
	private String statusMessage; //message to accompany status
	
	/**
	 * File properties
	 */
	private String file; //form placeholder
	private long size;
	private String stashedFilePath;
	private String mimetype;
	private String filename;
	
	/**
	 * File meta
	 */
	private String displayName;
	private int copyrightStatus;
	private boolean copyrightAlert;
	
	/**
	 * Access
	 */
	private boolean access;
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
	
	
}

