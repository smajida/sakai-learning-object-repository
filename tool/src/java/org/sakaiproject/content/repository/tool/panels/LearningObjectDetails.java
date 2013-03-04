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

/**
 * Panel for the learning object details tab
 * 
 * @author Steve Swisnburg (steve.swinsburg@gmail.com)
 *
 */
public class LearningObjectDetails extends Panel {

	@SpringBean(name="org.sakaiproject.content.repository.logic.ProjectLogic")
	private ProjectLogic logic;
	
	private FormMode mode;
		
	
	public LearningObjectDetails(String id, LearningObject lo, FormMode mode) {
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
			add(new CopyrightStatusDropdown("resourceType"));
			add(new CopyrightStatusDropdown("environment"));
			add(new CopyrightStatusDropdown("intendedAudience"));
			add(new CopyrightStatusDropdown("audienceEducation"));
			add(new CopyrightStatusDropdown("engagement"));
			add(new CopyrightStatusDropdown("interactivity"));
			add(new CopyrightStatusDropdown("difficulty"));
			add(new CopyrightStatusDropdown("learningTime"));
			add(new TextArea("assumedKnowledge"));
			add(new TextArea("keywords"));
			add(new TextArea("outcomes"));

			add(new Button("back") {
				@Override
				public void onSubmit() {
					
				}
			});
			
			
			
		}
		
		protected void onSubmit() {
			
			
			LearningObject lo = (LearningObject) this.getDefaultModelObject();
			System.out.println(lo.toString());
						
		}
		
		
	}
	
	private class CopyrightStatusDropdown extends DropDownChoice {

		LinkedHashMap<Integer,String> options;
		
		public CopyrightStatusDropdown(String id) {
			super(id);
			
			options = new LinkedHashMap<Integer, String>();
			options.put(990, new ResourceModel("options.copyright.1").getObject().toString());
			options.put(991, new ResourceModel("options.copyright.2").getObject().toString());
			
			//model that wraps our options
			IModel optionsModel = new Model() {
				public ArrayList<Integer> getObject() {
					 return new ArrayList(options.keySet());
				} 
			};
			
			setChoices(optionsModel);
			setChoiceRenderer(new HashMapChoiceRenderer(options));
		}

	}
	
	private class FileStatusDropdown extends DropDownChoice {

		LinkedHashMap<Integer,String> options;
		
		public FileStatusDropdown(String id) {
			super(id);
			
			options = new LinkedHashMap<Integer, String>();
			options.put(990, new ResourceModel("options.filestatus.1").getObject().toString());
			options.put(991, new ResourceModel("options.filestatus.2").getObject().toString());
			
			//model that wraps our options
			IModel optionsModel = new Model() {
				public ArrayList<Integer> getObject() {
					 return new ArrayList(options.keySet());
				} 
			};
			
			setChoices(optionsModel);
			setChoiceRenderer(new HashMapChoiceRenderer(options));
		}

	}


}
