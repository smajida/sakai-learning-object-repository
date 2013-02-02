package org.sakaiproject.content.repository.tool.panels;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sakaiproject.content.repository.logic.ProjectLogic;
import org.sakaiproject.content.repository.logic.ProjectUtils;
import org.sakaiproject.content.repository.model.ContentItem;
import org.sakaiproject.content.repository.model.SearchItem;
import org.sakaiproject.content.repository.tool.RepositoryApp;
import org.sakaiproject.content.repository.tool.components.ExternalImage;

/**
 * Panel for browsing
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class BrowsePanel extends Panel{

	@SpringBean(name="org.sakaiproject.content.repository.logic.ProjectLogic")
	private ProjectLogic logic;
			
	public BrowsePanel(String id) {
		super(id);
		
		List<ContentItem> items = logic.getResources();
		
		//display results
		add(new PageableListView<ContentItem>("data", items, RepositoryApp.MAX_ITEMS_PER_PAGE) {			
			protected void populateItem(final ListItem<ContentItem> item) {
				ContentItem ci = (ContentItem)item.getModelObject();
				
				item.add(new ExternalLink("iconLink", ci.getUrl()).add(new ExternalImage("icon", getImageUrl(ci.getMimeType()))));
				item.add(new ExternalLink("titleLink", ci.getUrl()).add(new Label("title", ci.getTitle())));
				item.add(new Label("author", ci.getAuthor()));
				item.add(new Label("modifiedDate", ProjectUtils.toDateStringFromResources(ci.getModifiedDate())));
				item.add(new Label("size", String.valueOf(ci.getSize())));
			
			}
		});
		
	}
	
	/**
	 * Gets the URL to an image for the given content type
	 * @param contentType type of file, eg text/html
	 * @return
	 */
	private String getImageUrl(String contentType){
		
		String imageRef = getString(contentType);
		if(StringUtils.isBlank(imageRef)) {
			imageRef = RepositoryApp.CONTENT_IMAGE_DEFAULT;
		}
		
		return RepositoryApp.CONTENT_IMAGE_BASE_URL + imageRef;
		
	}
	
}
