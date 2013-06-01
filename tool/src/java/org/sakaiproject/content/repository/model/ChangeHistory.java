package org.sakaiproject.content.repository.model;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import lombok.Data;

/**
 * Stores an instance of change history for a Learning Object. Used on the change history page.
 * 
 * Note we store the eid as it is more readable, in case the user no longer exists and we are left with just their uuid.
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
@Data
@Root(name="history")
public class ChangeHistory implements Serializable {

	@Element(name="version", required=false)
	private int version;
	
	@Element(name="eid", required=false)
	private String modifiedByEid;
	
	@Element(name="date", required=false)
	private String modifiedDate;
	
}
