package org.sakaiproject.content.repository.tool.panels;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.sakaiproject.content.repository.logic.ProjectLogic;
import org.sakaiproject.content.repository.model.FormMode;
import org.sakaiproject.content.repository.model.LearningObject;
import org.sakaiproject.content.repository.tool.RepositoryApp;
import org.sakaiproject.content.repository.tool.components.HashMapChoiceRenderer;
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
	
	private final int BACK_TAB=0;
	private final int NEXT_TAB=2;
		
	
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
			
			
			
			add(new HashMapDropdown("resourceType", getResourceTypeOptions()));
			add(new HashMapDropdown("environment", getEnvironmentOptions()));
			add(new HashMapDropdown("intendedAudience", getIntendedAudienceOptions()));
			add(new HashMapDropdown("audienceEducation", getAudienceEducationOptions()));
			add(new HashMapDropdown("engagement", getEngagementOptions()));
			add(new HashMapDropdown("interactivity", getInteractivityOptions()));
			add(new HashMapDropdown("difficulty", getDifficultyOptions()));
			add(new HashMapDropdown("learningTime", getLearningTimeOptions()));
			add(new TextArea("assumedKnowledge"));
			add(new TextArea("keywords"));
			add(new TextArea("outcomes"));

			add(new Button("back") {
				@Override
				public void onSubmit() {
					LearningObject lo = (LearningObject) this.getDefaultModelObject();
					System.out.println(lo.toString());
					setResponsePage(new ContentItemPage(lo, mode, BACK_TAB));
				}
			});
			
			
			
		}
		
		protected void onSubmit() {
			LearningObject lo = (LearningObject) this.getDefaultModelObject();
			System.out.println(lo.toString());
			
			setResponsePage(new ContentItemPage(lo, mode, NEXT_TAB));
		}
		
		
	}
	
	

	private LinkedHashMap<Integer,String> getResourceTypeOptions() {
		LinkedHashMap<Integer,String> options = new LinkedHashMap<Integer, String>();
		options.put(990, new ResourceModel("options.resourcetype.1").getObject().toString());
		options.put(991, new ResourceModel("options.resourcetype.2").getObject().toString());
		return options;
	}
	
	private LinkedHashMap<Integer,String> getEnvironmentOptions() {
		LinkedHashMap<Integer,String> options = new LinkedHashMap<Integer, String>();
		options.put(990, new ResourceModel("options.environment.1").getObject().toString());
		options.put(991, new ResourceModel("options.environment.2").getObject().toString());
		return options;
	}
	
	private LinkedHashMap<Integer,String> getIntendedAudienceOptions() {
		LinkedHashMap<Integer,String> options = new LinkedHashMap<Integer, String>();
		options.put(990, new ResourceModel("options.intendedaudience.1").getObject().toString());
		options.put(991, new ResourceModel("options.intendedaudience.2").getObject().toString());
		return options;
	}
	
	private LinkedHashMap<Integer,String> getAudienceEducationOptions() {
		LinkedHashMap<Integer,String> options = new LinkedHashMap<Integer, String>();
		options.put(990, new ResourceModel("options.audienceeducation.1").getObject().toString());
		options.put(991, new ResourceModel("options.audienceeducation.2").getObject().toString());
		return options;
	}
	
	private LinkedHashMap<Integer,String> getEngagementOptions() {
		LinkedHashMap<Integer,String> options = new LinkedHashMap<Integer, String>();
		options.put(990, new ResourceModel("options.engagement.1").getObject().toString());
		options.put(991, new ResourceModel("options.engagement.2").getObject().toString());
		return options;
	}
	
	private LinkedHashMap<Integer,String> getInteractivityOptions() {
		LinkedHashMap<Integer,String> options = new LinkedHashMap<Integer, String>();
		options.put(990, new ResourceModel("options.interactivity.1").getObject().toString());
		options.put(991, new ResourceModel("options.interactivity.2").getObject().toString());
		return options;
	}
	
	private LinkedHashMap<Integer,String> getDifficultyOptions() {
		LinkedHashMap<Integer,String> options = new LinkedHashMap<Integer, String>();
		options.put(990, new ResourceModel("options.difficulty.1").getObject().toString());
		options.put(991, new ResourceModel("options.difficulty.2").getObject().toString());
		return options;
	}
	
	private LinkedHashMap<Integer,String> getLearningTimeOptions() {
		LinkedHashMap<Integer,String> options = new LinkedHashMap<Integer, String>();
		options.put(990, new ResourceModel("options.learningtime.1").getObject().toString());
		options.put(991, new ResourceModel("options.learningtime.2").getObject().toString());
		return options;
	}
	
	

}
