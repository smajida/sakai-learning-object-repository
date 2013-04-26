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
			
			add(new HashMapDropdown("copyrightStatus", getCopyrightOptions()));
			
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
			
			add(new HashMapDropdown("fileStatus", getFileStatusOptions()));

			
			add(new TextField("publisher"));

			
		}
		
		protected void onSubmit() {
			
			LearningObject lo = (LearningObject) this.getDefaultModelObject();
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
