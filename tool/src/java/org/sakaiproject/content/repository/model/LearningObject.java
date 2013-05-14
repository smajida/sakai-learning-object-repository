package org.sakaiproject.content.repository.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import lombok.Data;
import lombok.ToString;

/**
 * Data model for when uploading/editing a learning object
 * Contains actual data and bean properties for the transport of this object through its lifecycle
 * This object is also serialised to that we can maintain history. Note that not all fields are serialised, only the ones we want to keep.
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
@Data
@ToString
@Root(name="learning_object")
public class LearningObject implements Serializable {

	/**
	 * ID of this resource, matches the content resource ID in CHS. Not serialised.
	 */
	private String resourceId;
	
	/**
	 * Version property allows us to maintain history
	 */
	@Element(name="version")
	private int version;
	
	/**
	 * Store some more info about different versions
	 */
	@Element(name="change_history_list")
	private ChangeHistoryList changeHistoryList = new ChangeHistoryList();
	
	/**
	 * File properties
	 */
	private String file; //form placeholder, NEVER used otherwise, not serialised
	private String stashedFilePath; //only used for initial upload, NEVER used afterwards, not serialised
	private String filename; //used for initial upload, but also used for view purposes, not serialised as it comes from the ContentResource
	
	@Element(name="size")
	private long size;
	
	@Element(name="mimetype")
	private String mimetype;
	
	
	/**
	 * File meta
	 */
	@Element(name="display_name", required=false)
	private String displayName;
	
	@Element(name="copyright_status", required=false)
	private String copyrightStatus;
	
	@Element(name="copyright_custom_text", required=false)
	private String copyrightCustomText;
	
	@Element(name="copyright_alert", required=false)
	private boolean copyrightAlert;
	
	/**
	 * Access
	 */
	
	@Element(name="access", required=false)
	private String access;
	
	@Element(name="date_from", required=false)
	private String dateFrom;
	
	@Element(name="date_to", required=false)
	private String dateTo;
	
	@Element(name="file_status", required=false)
	private String fileStatus;
	
	@Element(name="publisher", required=false)
	private String publisher;
	
	/**
	 * LO details
	 */
	
	@Element(name="description", required=false)
	private String description;
	
	@Element(name="resource_type", required=false)
	private String resourceType;
	
	@Element(name="environment", required=false)
	private String environment;
	
	@Element(name="intended_audience", required=false)
	private String intendedAudience;
	
	@Element(name="audience_education", required=false)
	private String audienceEducation;
	
	@Element(name="engagement", required=false)
	private String engagement;
	
	@Element(name="interactivity", required=false)
	private String interactivity;
	
	@Element(name="difficulty", required=false)
	private String difficulty;
	
	@Element(name="assumed_knowledge", required=false)
	private String assumedKnowledge;
	
	@Element(name="learning_time", required=false)
	private String learningTime;
	
	@Element(name="keywords", required=false)
	private String keywords;
	
	@Element(name="outcomes", required=false)
	private String outcomes;
	
	
	/**
	 * Technical requirements
	 */
	@Element(name="techreqs", required=false)
	private TechnicalRequirementList techReqs = new TechnicalRequirementList();
	
	
}

