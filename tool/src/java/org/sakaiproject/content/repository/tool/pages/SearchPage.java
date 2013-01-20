package org.sakaiproject.content.repository.tool.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.ResourceModel;
import org.sakaiproject.content.repository.tool.panels.SimpleSearchPanel;

/**
 * Search page
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class SearchPage extends BasePage {

	private boolean advancedMode=false;
	
	public SearchPage() {
		disableLink(searchLink);
		
		if(advancedMode) {
			//add advanced panel
			
		} else {
			//add simple panel
			add(new SimpleSearchPanel("searchPanel"));

		}
		
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
			
			//switch panels to load in the appropriate search form

			if(advancedMode) {
				//load in simple mode panel
			}
			
			System.out.println("Not yet implemented");
			
		}
		
		
		
	}
	
}
