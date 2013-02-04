package org.sakaiproject.content.repository.tool.pages;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.sakaiproject.content.repository.model.SimpleSearch;
import org.sakaiproject.content.repository.tool.panels.BrowsePanel;


/**
 * Browse page. Also features a filter form
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class BrowsePage extends BasePage {

	private String searchString;
	
	/**
	 * Default constructor
	 */
	public BrowsePage() {
		this(null);
	}
	
	/**
	 * Constructor with a search term
	 * @param searchString
	 */
	public BrowsePage(String searchString) {
		this.searchString = searchString;
		disableLink(browseLink);
		add(new FilterForm("form"));
		add(new BrowsePanel("browsePanel", searchString));
	}
	
	/**
	 * Form for filtering the results
	 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
	 *
	 */
	private class FilterForm extends Form<SimpleSearch> {
		
		public FilterForm(String id){
			super(id);
			
			SimpleSearch searchModel = new SimpleSearch();
			
			//if we already have a term, set it into the model
			if(StringUtils.isNotBlank(searchString)){
				searchModel.setSearchString(searchString);
			}
			setDefaultModel(new CompoundPropertyModel<SimpleSearch>(searchModel));
			
			add(new TextField<String>("searchString"));
			
			//reset link
			add(new Link("reset") {
                @Override
                public void onClick() {
                    setResponsePage(new BrowsePage());
                }
                
                @Override
                public boolean isVisible() {
                	//only show if there is a search term
                	if(StringUtils.isNotBlank(searchString)){
                		return true;
                	}
                	return false;
                }
                
            });
			
		}
		
		public void onSubmit(){
			SimpleSearch model = getModelObject();
			String searchString = model.getSearchString();
			
			if(StringUtils.isNotBlank(searchString)) {
				setResponsePage(new BrowsePage(searchString));
			}
			
		}
		
	}
}
