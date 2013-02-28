package org.sakaiproject.content.repository.tool.panels;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.sakaiproject.content.repository.logic.ProjectLogic;
import org.sakaiproject.content.repository.model.LearningObject;
import org.sakaiproject.content.repository.tool.RepositoryApp;
import org.sakaiproject.content.repository.tool.components.HashMapChoiceRenderer;

/**
 * Panel for the file upload tab
 * 
 * @author Steve Swisnburg (steve.swinsburg@gmail.com)
 *
 */
public class TabFileDetails extends Panel {

	@SpringBean(name="org.sakaiproject.content.repository.logic.ProjectLogic")
	private ProjectLogic logic;
	
	private boolean fileUploaded;
	
	
	public TabFileDetails(String id, LearningObject lo) {
		super(id);
		
		//add form		
		add(new UploadForm("form", new CompoundPropertyModel<LearningObject>(lo)));
		
		
		
		
		
	}

	
	/**
	 * Upload form
	 */
	private class UploadForm extends Form<Void> {

		FileUploadField uploadField;
		
		public UploadForm(String id, IModel lom) {
			super(id, lom);
			
			setMaxSize(Bytes.megabytes(RepositoryApp.MAX_FILE_SIZE_MB));
			setOutputMarkupId(true);
			setMultiPart(true);
			
			//form fields will automatically math up with the underlying model if their id is the same as the attribute
			//if not, set new PropertyModel(lom, "displayName")
			
			uploadField = new FileUploadField("file");
			uploadField.setOutputMarkupId(true);
			add(uploadField);
			
			add(new TextField("displayName"));
			
			add(new CopyrightStatusDropdown("copyrightStatus"));
			
			add(new CheckBox("copyrightAlert"));
			
			RadioGroup<Boolean> access = new RadioGroup<Boolean>("access");
			Radio access1 = new Radio<Boolean>("access1", new Model<Boolean>(Boolean.valueOf(true)));
			Radio access2 = new Radio<Boolean>("access2", new Model<Boolean>(Boolean.valueOf(false)));
			access.add(access1);
			access.add(access2);
			add(access);

			
			add(new Button("continue") {
				@Override
				public void onSubmit() {
					
				}
			});
			
			
		}
		
		protected void onSubmit() {
			
			//get file that was uploaded
			FileUpload upload = uploadField.getFileUpload();
			
			if (upload == null || upload.getSize() == 0) {
				error(new StringResourceModel("error.no.file.uploaded", this, null).getString());
				return;
			}
			
			//TODO set the data into the model so it goes into hte object at the end
			// set the metadata into the learning object
			//lo.setFilename(upload.getClientFileName());
			//lo.setSize(upload.getSize());
			//lo.setMimetype(upload.getContentType());
			
			//add the file
			//logic.addNewLearningObject(lo);
			
			//stash the file so we can deal with it later.
			//do this directly from the uploaded file inputstream
			//TODO
			
			fileUploaded = true;
			
			LearningObject lo = (LearningObject) this.getDefaultModelObject();
			System.out.println(lo.toString());
						
		}
		
		
	}
	
	private class CopyrightStatusDropdown extends DropDownChoice {

		LinkedHashMap<Integer,String> options;
		
		public CopyrightStatusDropdown(String id) {
			super(id);
			
			options = new LinkedHashMap<Integer, String>();
			options.put(990, getString("options.copyright.1"));
			options.put(991, getString("options.copyright.2"));
			
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
