package org.sakaiproject.content.repository.model;

import java.io.InputStream;
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
	private long size;
	private String stashedFilePath;
	private String mimetype;
	private String filename;
	
	/**
	 * File meta
	 */
	private String displayName;
	
	
	
	
}

