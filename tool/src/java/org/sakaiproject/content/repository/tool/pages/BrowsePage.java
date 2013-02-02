package org.sakaiproject.content.repository.tool.pages;

import org.sakaiproject.content.repository.tool.panels.BrowsePanel;


/**
 * Browse page
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class BrowsePage extends BasePage {

	
	public BrowsePage() {
		disableLink(browseLink);
		
		add(new BrowsePanel("browsePanel"));
	}
}
