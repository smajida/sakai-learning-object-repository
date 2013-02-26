package org.sakaiproject.content.repository.tool.panels;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.sakaiproject.content.repository.logic.ProjectLogic;
import org.sakaiproject.content.repository.model.LearningObject;
import org.sakaiproject.content.repository.tool.RepositoryApp;
import org.sakaiproject.content.repository.tool.pages.ContentItemPage;

/**
 * Panel for the file upload tab
 * 
 * @author Steve Swisnburg (steve.swinsburg@gmail.com)
 *
 */
public class TabFileDetails extends Panel {

	@SpringBean(name="org.sakaiproject.content.repository.logic.ProjectLogic")
	private ProjectLogic logic;
	
	private LearningObject lo;
	private boolean fileUploaded;
	
	
	public TabFileDetails(String id, LearningObject obj) {
		super(id);
		this.lo = obj;
		
		//upload form, visible when no file is uploaded
		add(new UploadForm("form"));
		
		
		
		
		
	}

	
	/**
	 * Upload form
	 */
	private class UploadForm extends Form<Void> {

		FileUploadField uploadField;
		
		public UploadForm(String id) {
			super(id);
						
			setMaxSize(Bytes.megabytes(RepositoryApp.MAX_FILE_SIZE_MB));
			setOutputMarkupId(true);
			setMultiPart(true);
			
			uploadField = new FileUploadField("file");
			uploadField.setOutputMarkupId(true);
			add(uploadField);
			
			add(new TextField("displayName"));
			
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
			
			// set the metadata into the learning object
			lo.setFilename(upload.getClientFileName());
			lo.setSize(upload.getSize());
			lo.setMimetype(upload.getContentType());
			
			//add the file
			//logic.addNewLearningObject(lo);
			
			//stash the file so we can deal with it later.
			//do this directly from the uploaded file inputstream
			//TODO
			
			fileUploaded = true;
			
		}
		
		
	}


}
