package org.sakaiproject.content.repository.tool.panels;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
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
 * Panel for the file upload tab
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class TabFileDetails extends Panel {

	@SpringBean(name="org.sakaiproject.content.repository.logic.ProjectLogic")
	private ProjectLogic logic;
	
	private boolean fileUploaded;
	private FormMode mode;
	
	private final int NEXT_TAB=1;

	
	
	public TabFileDetails(String id, LearningObject lo, FormMode mode) {
		super(id);
		this.mode=mode;
		
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
			//if not, set new PropertyModel(lom, "someOtherName")
			
			uploadField = new FileUploadField("file");
			uploadField.setOutputMarkupId(true);
			add(uploadField);
			
			add(new TextField("displayName"));
			
			add(new HashMapDropdown("copyrightStatus", getCopyrightOptions()));
			
			add(new CheckBox("copyrightAlert"));
			
			RadioGroup<Boolean> access = new RadioGroup<Boolean>("access");
			Radio access1 = new Radio<Boolean>("access1", new Model<Boolean>(Boolean.valueOf(true)));
			Radio access2 = new Radio<Boolean>("access2", new Model<Boolean>(Boolean.valueOf(false)));
			access.add(access1);
			access.add(access2);
			add(access);

			add(new TextField("dateFrom"));
			add(new TextField("dateTo"));
			
			add(new HashMapDropdown("fileStatus", getFileStatusOptions()));

			
			add(new TextField("publisher"));

			
		}
		
		protected void onSubmit() {
			
			//get file that was uploaded
			FileUpload upload = uploadField.getFileUpload();
			
			if (upload == null || upload.getSize() == 0) {
				error(new StringResourceModel("error.no.file.uploaded", this, null).getString());
				return;
			}
			
			LearningObject lo = (LearningObject) this.getDefaultModelObject();
			
			// set the file data into the learning object
			lo.setFilename(upload.getClientFileName());
			lo.setSize(upload.getSize());
			lo.setMimetype(upload.getContentType());
			
			//stash the file so we can deal with it later.
			String stashedFilePath = logic.stashFile(upload.getBytes());
			if(StringUtils.isBlank(stashedFilePath)) {
				error(new StringResourceModel("error.couldnt.stash.file", this, null).getString());
				return;
			}
			lo.setStashedFilePath(stashedFilePath);
			
			System.out.println(lo.toString());
			
			setResponsePage(new ContentItemPage(lo, mode, NEXT_TAB));
						
		}
		
		
	}
	
	
	private LinkedHashMap<Integer,String> getCopyrightOptions() {
		LinkedHashMap<Integer,String> options = new LinkedHashMap<Integer, String>();
		options.put(990, new ResourceModel("options.copyright.1").getObject().toString());
		options.put(991, new ResourceModel("options.copyright.2").getObject().toString());
		return options;
	}
	
	
	private LinkedHashMap<Integer,String> getFileStatusOptions() {
		LinkedHashMap<Integer,String> options = new LinkedHashMap<Integer, String>();
		options.put(990, new ResourceModel("options.filestatus.1").getObject().toString());
		options.put(991, new ResourceModel("options.filestatus.2").getObject().toString());
		return options;
	}


}
