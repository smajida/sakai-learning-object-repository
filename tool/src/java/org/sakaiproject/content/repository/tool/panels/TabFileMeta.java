package org.sakaiproject.content.repository.tool.panels;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sakaiproject.content.repository.logic.ProjectLogic;
import org.sakaiproject.content.repository.logic.ProjectUtils;
import org.sakaiproject.content.repository.model.FormMode;
import org.sakaiproject.content.repository.model.LearningObject;
import org.sakaiproject.content.repository.tool.components.HashMapDropdown;
import org.sakaiproject.content.repository.tool.pages.ContentItemPage;

/**
 * Panel for the file metadata tab
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class TabFileMeta extends Panel {

	@SpringBean(name="org.sakaiproject.content.repository.logic.ProjectLogic")
	private ProjectLogic logic;
	
	private FormMode mode;
	
	private final int NEXT_TAB=2;

	
	public TabFileMeta(String id, LearningObject lo, FormMode mode) {
		super(id);
		this.mode=mode;
		
		//add form		
		add(new DetailsForm("form", new CompoundPropertyModel<LearningObject>(lo)));
		
	}

	
	/**
	 * Upload form
	 */
	private class DetailsForm extends Form<Void> {

		
		public DetailsForm(String id, IModel lom) {
			super(id, lom);
			
			add(new TextField("displayName"));
			
			add(new HashMapDropdown("copyrightStatus", ProjectUtils.getLabelledDropdownMap("dropdown.copyright")));
			
			add(new CheckBox("copyrightAlert"));
			
			/*
			RadioGroup<String> access = new RadioGroup<String>("access");
			Radio access1 = new Radio<String>("access1", new Model<String>("site"));
			Radio access2 = new Radio<String>("access2", new Model<String>("public"));
			access.add(access1);
			access.add(access2);
			add(access);

			add(new TextField("dateFrom"));
			add(new TextField("dateTo"));
			*/
			
			add(new HashMapDropdown("fileStatus", ProjectUtils.getLabelledDropdownMap("dropdown.status")));

			
			add(new TextField("publisher"));

			
		}
		
		protected void onSubmit() {
			
			LearningObject lo = (LearningObject) this.getDefaultModelObject();
			System.out.println(lo.toString());
			
			setResponsePage(new ContentItemPage(lo, mode, NEXT_TAB));
						
		}
		
		
	}
	

}
