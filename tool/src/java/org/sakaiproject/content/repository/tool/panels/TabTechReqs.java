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
 * Panel for the technical requirements tab
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class TabTechReqs extends Panel {

	@SpringBean(name="org.sakaiproject.content.repository.logic.ProjectLogic")
	private ProjectLogic logic;
	
	private FormMode mode;
	private final int BACK_TAB=0;
		
	
	public TabTechReqs(String id, LearningObject lo, FormMode mode) {
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
			
			add(new HashMapDropdown("techReqType", getTechReqOptions()));
			add(new TextField("techReqName"));
			add(new TextField("techReqMinVersion"));
			add(new TextField("techReqMaxVersion"));
			add(new HashMapDropdown("techReqAndOr", getTechAndOrOptions()));
			add(new TextField("techReqInstallRemarks"));
			add(new TextField("techReqOther"));			
			
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
			
		}
		
		
	}
	
	

	private LinkedHashMap<Integer,String> getTechReqOptions() {
		LinkedHashMap<Integer,String> options = new LinkedHashMap<Integer, String>();
		options.put(990, new ResourceModel("options.techreq.1").getObject().toString());
		options.put(991, new ResourceModel("options.techreq.2").getObject().toString());
		return options;
	}
	
	private LinkedHashMap<Integer,String> getTechAndOrOptions() {
		LinkedHashMap<Integer,String> options = new LinkedHashMap<Integer, String>();
		options.put(990, new ResourceModel("options.techandor.1").getObject().toString());
		options.put(991, new ResourceModel("options.techandor.2").getObject().toString());
		return options;
	}
	
	

}
