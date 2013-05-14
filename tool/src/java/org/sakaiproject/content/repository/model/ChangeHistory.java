package org.sakaiproject.content.repository.model;

import java.io.Serializable;

import lombok.Data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Stores an instance of change history for a Learning Object
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
@Data
@Root(name="change_history")
public class ChangeHistory implements Serializable {

	@Element(name="version")
	private int version;
	
	@Element(name="modified_by_eid", required=false)
	private String modifiedByEid;
	
	@Element(name="modified_by_display", required=false)
	private String modifiedByDisplayName;
	
	@Element(name="modified_date", required=false)
	private String modifiedDate;
	
}
