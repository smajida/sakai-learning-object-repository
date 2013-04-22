package org.sakaiproject.content.repository.logic;

import lombok.Setter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.entity.api.ResourceProperties;

/**
 * Helper for dealing with ContentResource objects
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class ContentResourceHelper {

	@Setter
	private ContentResource resource;
	
	/**
	 * Get the title property for the resource
	 * @return
	 */
	protected String getTitle() {
		return resource.getProperties().getProperty(ResourceProperties.PROP_DISPLAY_NAME);
	}
	
	/**
	 * Get the creator property for the resource
	 * @return
	 */
	protected String getCreator() {
		return resource.getProperties().getProperty(ResourceProperties.PROP_CREATOR);
	}
	
	/**
	 * Get the modified date property for the resource
	 * @return
	 */
	protected String getModifiedDate() {
		return resource.getProperties().getProperty(ResourceProperties.PROP_MODIFIED_DATE);
	}
	
	/**
	 * Get the revision property for the resource
	 * @return
	 */
	protected int getRevision() {
		return NumberUtils.toInt(resource.getProperties().getProperty(ResourceProperties.PROP_MODIFIED_DATE), 0);
	}
	
	/**
	 * Check if the given content resource item matches the supplied filter
	 * 
	 * <br />
	 * Checks the following fields:
	 * <ul>
	 * 	<li>title</li>
	 * </ul>
	 * 
	 * @param resource	ContentResource item
	 * @param filter	Filter string
	 * @return
	 */
	protected boolean resourceMatchesFilter(String filter){
		
		if(StringUtils.containsIgnoreCase(getTitle(), filter)) {
			return true;
		}
		
		return false;
	}
	
	
}
