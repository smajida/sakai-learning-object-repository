package org.sakaiproject.content.repository.tool.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.sakaiproject.content.repository.model.ChangeHistory;

/**
 * Item history page
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class HistoryPage extends BasePage {

	
	public HistoryPage( String resourceId) {
		
		final List<ChangeHistory> items = logic.getChangeHistory(resourceId);
		
		//display results
		ListView list = new ListView<ChangeHistory>("data", items) {			
			
			protected void populateItem(final ListItem<ChangeHistory> item) {
				final ChangeHistory ch = (ChangeHistory)item.getModelObject();
				
				item.add(new Label("version", String.valueOf(ch.getVersion())));
				item.add(new Label("date", ch.getModifiedDate()));
				
				//format user display name
				StringBuilder sb = new StringBuilder();
				
				String displayName = logic.getUserDisplayNameByEid(ch.getModifiedByEid());
				if(!StringUtils.equals(displayName, ch.getModifiedByEid())) {
					sb.append(displayName);
				}
				sb.append(" (");
				sb.append(ch.getModifiedByEid());
				sb.append(")");
				
				item.add(new Label("name", sb.toString()));
			}
			
		};
		add(list);
		
		
	}
	
}
