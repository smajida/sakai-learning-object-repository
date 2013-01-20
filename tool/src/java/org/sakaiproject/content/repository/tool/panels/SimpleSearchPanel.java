package org.sakaiproject.content.repository.tool.panels;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.sakaiproject.content.repository.model.SimpleSearch;

/**
 * Panel for the simple search
 * 
 * @author Stevbe Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class SimpleSearchPanel extends Panel {

	/**
	 * Constructor for new instance
	 * @param id	component id
	 */
	public SimpleSearchPanel(String id) {
		super(id);
		add(new SimpleSearchForm("form", new SimpleSearch()));
	}
	
	/**
	 * Consutrctor if we have data already
	 * @param id	component id
	 * @param ss	simple search object
	 */
	public SimpleSearchPanel(String id, SimpleSearch ss) {
		super(id);
		add(new SimpleSearchForm("form", ss));
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
			System.out.println("Searched for: " + s.getSearchString());
		}
	}
	
}
