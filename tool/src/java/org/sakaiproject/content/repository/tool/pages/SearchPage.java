package org.sakaiproject.content.repository.tool.pages;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.ResourceModel;
import org.sakaiproject.content.repository.model.FormMode;
import org.sakaiproject.content.repository.model.SimpleSearch;
import org.sakaiproject.content.repository.tool.panels.SimpleSearchPanel;

/**
 * Search page. This could be enhanced to use tabs
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class SearchPage extends BasePage {

	private boolean advancedMode=false;
	
	private String searchString = null;
	
	/**
	 * Default constructor when performing a new search.
	 */
	public SearchPage() {
		doRender();
	}
	
	public SearchPage(String searchString) {
		this.searchString = searchString;
		doRender();
	}
	
	public void doRender() {
		disableLink(searchLink);
		
		SimpleSearch ss = new SimpleSearch();
		
		if(StringUtils.isNotBlank(searchString)){
			//for now just create a SimpelSearch obj and pass in the text
			ss.setSearchString(searchString);
		}
		
		add(new SimpleSearchPanel("searchPanel", ss));
		
		add(new SwitchModeLink("modeLink"));
	}
	
	
	/**
	 * Link for switching mode
	 */
	private class SwitchModeLink extends Link<Void> {

		public SwitchModeLink(String id) {
			super(id);
			
			//set label depending on context
			if(advancedMode) {
				add(new Label("modeLabel", new ResourceModel("search.mode.text.simple")));
			} else {
				add(new Label("modeLabel", new ResourceModel("search.mode.text.advanced")));
			}
		}

		@Override
		public void onClick() {
			
			
			//load in the advanced search form
			//if(advancedMode) {
				setResponsePage(new ContentItemPage(FormMode.SEARCH));
			//}
			
			
		}
		
		
		
	}
	
}
