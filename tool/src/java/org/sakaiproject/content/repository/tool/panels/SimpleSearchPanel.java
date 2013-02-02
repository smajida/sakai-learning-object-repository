package org.sakaiproject.content.repository.tool.panels;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sakaiproject.content.repository.logic.ProjectLogic;
import org.sakaiproject.content.repository.model.SimpleSearch;
import org.sakaiproject.search.api.SearchResult;

/**
 * Panel for the simple search
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class SimpleSearchPanel extends Panel {

	@SpringBean(name="org.sakaiproject.content.repository.logic.ProjectLogic")
	private ProjectLogic logic;
	
	Panel resultsPanel;
	
	/**
	 * Constructor for new instance
	 * @param id	component id
	 */
	public SimpleSearchPanel(String id) {
		super(id);
		add(new SimpleSearchForm("form", new SimpleSearch()));
		
		//no results so add an empty panel
		resultsPanel = new EmptyPanel("results");
		add(resultsPanel);
	}
	
	/**
	 * Constructor if we have data already
	 * @param id	component id
	 * @param ss	simple search object
	 */
	public SimpleSearchPanel(String id, SimpleSearch ss) {
		super(id);
		add(new SimpleSearchForm("form", ss));
		
		//we have a search string stored so get the results and setup the real panel
		resultsPanel = new SearchResultsPanel("results", logic.performSearch(ss.getSearchString()));
		add(resultsPanel);
	}
	
	/**
	 * Form for a simple search. It is automatically linked up if the form fields match the object fields
	 */
	private class SimpleSearchForm extends Form<SimpleSearch> {

		public SimpleSearchForm(String id, SimpleSearch model) {
			super(id, new CompoundPropertyModel<SimpleSearch>(model));
			add(new TextField<String>("searchString"));
		}
		
		@Override
		protected void onSubmit() {
			
			SimpleSearch s = (SimpleSearch) getDefaultModelObject();
			
			Panel p;
			if(StringUtils.isBlank(s.getSearchString())){
				p = new EmptyPanel("results");
			} else {
				System.out.println("Searched for: " + s.getSearchString());
				p = new SearchResultsPanel("results",logic.performSearch(s.getSearchString()));
			}
			
			resultsPanel.replaceWith(p);
			resultsPanel=p; //keep ref up to date.
		}
	}
	
}
