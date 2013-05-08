package org.sakaiproject.content.repository.tool.panels;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
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
 * Panel for the learning object details tab
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class TabLearningObjectDetails extends Panel {

	@SpringBean(name="org.sakaiproject.content.repository.logic.ProjectLogic")
	private ProjectLogic logic;
	private FormMode mode;
	
		
	
	public TabLearningObjectDetails(String id, LearningObject lo, FormMode mode) {
		super(id);
		this.mode=mode;
		
		//add form		
		add(new DetailsForm("form", new CompoundPropertyModel<LearningObject>(lo)));
		
		
		
		
		
	}

	
	/**
	 * DetailsForm form
	 */
	private class DetailsForm extends Form<Void> {

		
		public DetailsForm(String id, IModel lom) {
			super(id, lom);
			
			//form fields will automatically math up with the underlying model if their id is the same as the attribute
			//if not, set new PropertyModel(lom, "someOtherName")
			
			add(new TextArea("description"));
			
			
			add(new HashMapDropdown("resourceType", ProjectUtils.getLabelledDropdownMap("dropdown.learning_resource_type")));
			add(new HashMapDropdown("environment", ProjectUtils.getLabelledDropdownMap("dropdown.environment")));
			add(new HashMapDropdown("intendedAudience", ProjectUtils.getLabelledDropdownMap("dropdown.context_level")));
			add(new HashMapDropdown("audienceEducation", ProjectUtils.getLabelledDropdownMap("dropdown.audience_education")));
			add(new HashMapDropdown("engagement", ProjectUtils.getLabelledDropdownMap("dropdown.engagement")));
			add(new HashMapDropdown("interactivity", ProjectUtils.getLabelledDropdownMap("dropdown.interactivity_level")));
			add(new HashMapDropdown("difficulty", ProjectUtils.getLabelledDropdownMap("dropdown.difficulty")));
			add(new TextField("learningTime"));
			add(new TextArea("assumedKnowledge"));
			add(new TextArea("keywords"));
			add(new TextArea("outcomes"));

			
		}
		
		protected void onSubmit() {
			LearningObject lo = (LearningObject) this.getDefaultModelObject();
			System.out.println(lo.toString());
			
			setResponsePage(new ContentItemPage(lo, mode, getNextTab()));
		}
		
		/** Determine what the next tab is, based on the mode **/
		private int getNextTab() {
			if(mode == FormMode.ADD) {
				return 3;
			}
			return 2;
		}
		
	}
	

	

}
