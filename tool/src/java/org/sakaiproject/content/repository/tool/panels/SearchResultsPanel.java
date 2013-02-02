package org.sakaiproject.content.repository.tool.panels;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.sakaiproject.content.repository.model.SearchItem;
import org.sakaiproject.content.repository.tool.RepositoryApp;

/**
 * This panel displays
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class SearchResultsPanel extends Panel {

	public SearchResultsPanel(String id, List<SearchItem> results) {
		super(id);
		
		//if 0 or many, or if 1.
		if(results.size() == 0 || results.size() > 1){
			add(new Label("count", new StringResourceModel("search.results.num.many", null, null, new Object[]{results.size()})));
		} else {
			add(new Label("count", new ResourceModel("search.results.num.1")));
		} 
				
		//display results
		add(new PageableListView<SearchItem>("data", results, RepositoryApp.MAX_SEARCH_ITEMS_PER_PAGE) {			
			protected void populateItem(final ListItem<SearchItem> item) {
				SearchItem si = (SearchItem)item.getModelObject();
				
				item.add(new ExternalLink("link", si.getUrl()).add(new Label("title", si.getTitle())));
				item.add(new Label("location", si.getUrl()));
				item.add(new Label("preview", si.getPreview()));
				
			}
		});
		
		
	}
	
}
