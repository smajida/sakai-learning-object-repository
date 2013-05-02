package org.sakaiproject.content.repository.tool.panels;

import java.util.LinkedHashMap;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
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
	
	private final int BACK_TAB=1;
	private final int NEXT_TAB=3;
		
	
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
			add(new HashMapDropdown("environment", ProjectUtils.getLabelledDropdownMap("dropdown.learning_resource_type")));
			add(new HashMapDropdown("intendedAudience", ProjectUtils.getLabelledDropdownMap("dropdown.learning_resource_type")));
			add(new HashMapDropdown("audienceEducation", ProjectUtils.getLabelledDropdownMap("dropdown.learning_resource_type")));
			add(new HashMapDropdown("engagement", ProjectUtils.getLabelledDropdownMap("dropdown.learning_resource_type")));
			add(new HashMapDropdown("interactivity", ProjectUtils.getLabelledDropdownMap("dropdown.learning_resource_type")));
			add(new HashMapDropdown("difficulty", ProjectUtils.getLabelledDropdownMap("dropdown.learning_resource_type")));
			add(new HashMapDropdown("learningTime", ProjectUtils.getLabelledDropdownMap("dropdown.learning_resource_type")));
			add(new TextArea("assumedKnowledge"));
			add(new TextArea("keywords"));
			add(new TextArea("outcomes"));

			
		}
		
		protected void onSubmit() {
			LearningObject lo = (LearningObject) this.getDefaultModelObject();
			System.out.println(lo.toString());
			
			setResponsePage(new ContentItemPage(lo, mode, NEXT_TAB));
		}
		
		
	}
	

	

}
