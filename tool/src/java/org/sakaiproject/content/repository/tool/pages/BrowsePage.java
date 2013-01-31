package org.sakaiproject.content.repository.tool.pages;


/**
 * Browse page
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class BrowsePage extends BasePage {

	
	public BrowsePage() {
		disableLink(browseLink);
		
		logic.getResources();
	}
}
