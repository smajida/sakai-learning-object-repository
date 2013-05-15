package org.sakaiproject.content.repository.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Stores an instance of change history for a Learning Object. Used on the change history page
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
@Data
public class ChangeHistory implements Serializable {

	private int version;
	
	private String modifiedByEid;
	
	private String modifiedDate;
	
}
