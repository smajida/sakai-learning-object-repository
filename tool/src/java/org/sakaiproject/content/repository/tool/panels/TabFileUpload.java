package org.sakaiproject.content.repository.tool.panels;

import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.sakaiproject.content.repository.logic.ProjectLogic;
import org.sakaiproject.content.repository.model.FormMode;
import org.sakaiproject.content.repository.model.LearningObject;
import org.sakaiproject.content.repository.tool.RepositoryApp;
import org.sakaiproject.content.repository.tool.components.HashMapDropdown;
import org.sakaiproject.content.repository.tool.pages.ContentItemPage;

/**
 * Panel for the file upload tab
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class TabFileUpload extends Panel {

	@SpringBean(name="org.sakaiproject.content.repository.logic.ProjectLogic")
	private ProjectLogic logic;
	
	private boolean fileUploaded;
	private FormMode mode;
	
	private final int NEXT_TAB=1;

	public TabFileUpload(String id, LearningObject lo, FormMode mode) {
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
			
			uploadField = new FileUploadField("file");
			uploadField.setOutputMarkupId(true);
			add(uploadField);
			
		}
		
		protected void onSubmit() {
			
			LearningObject lo = (LearningObject) this.getDefaultModelObject();

			//get file that was uploaded
			FileUpload upload = uploadField.getFileUpload();
			
			if (upload == null || upload.getSize() == 0) {
				error(new StringResourceModel("error.no.file.uploaded", this, null).getString());
				return;
			}
			
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
	
	

}
