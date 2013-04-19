package org.sakaiproject.content.repository.tool.panels;

import java.util.LinkedHashMap;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sakaiproject.content.repository.logic.ProjectLogic;
import org.sakaiproject.content.repository.model.FormMode;
import org.sakaiproject.content.repository.model.LearningObject;
import org.sakaiproject.content.repository.model.TechnicalRequirement;
import org.sakaiproject.content.repository.tool.components.HashMapDropdown;
import org.sakaiproject.content.repository.tool.components.ListEditor;
import org.sakaiproject.content.repository.tool.components.ListItem;
import org.sakaiproject.content.repository.tool.components.RemoveButton;
import org.sakaiproject.content.repository.tool.pages.ContentItemPage;

/**
 * Panel for the technical requirements tab. Allows multiples
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class TabTechReqs extends Panel {

	@SpringBean(name="org.sakaiproject.content.repository.logic.ProjectLogic")
	private ProjectLogic logic;
	
	private final FormMode mode;
	private final int BACK_TAB=1;
	private final ListEditor<TechnicalRequirement> editor;
	private LearningObject lo;
	
	public TabTechReqs(String id, LearningObject data, FormMode formMode) {
		super(id);
		this.mode=formMode;
		this.lo=data;
		
		//add form		
		//add(new DetailsForm("form", new CompoundPropertyModel<LearningObject>(lo)));
		
		Form form = new Form("form")
        {
            @Override
            protected void onSubmit()
            {
            	//LearningObject lo = (LearningObject) this.getDefaultModelObject();
				System.out.println(lo.toString());
            }
        };
        add(form);
	        
        editor = new ListEditor<TechnicalRequirement>("techReqs", new PropertyModel(this, "lo.techReqs"))
        {
            @Override
            protected void onPopulateItem(ListItem<TechnicalRequirement> item)
            {
                item.setModel(new CompoundPropertyModel(item.getModel()));
                item.add(new HashMapDropdown("techReqType", getTechReqOptions()));
                item.add(new TextField("techReqName"));
                item.add(new TextField("techReqMinVersion"));
    			item.add(new TextField("techReqMaxVersion"));
    			item.add(new HashMapDropdown("techReqAndOr", getTechAndOrOptions()));
    			item.add(new TextField("techReqInstallRemarks"));
    			item.add(new TextField("techReqOther"));		

    			item.add(new RemoveButton("remove"));
            }
        };

        //add button
        form.add(new Button("add")
        {
            @Override
            public void onSubmit()
            {
                editor.addItem(new TechnicalRequirement());
            }
        }.setDefaultFormProcessing(false));
       
        //back button
        form.add(new Button("back") {
			@Override
			public void onSubmit() {
				setResponsePage(new ContentItemPage(lo, mode, BACK_TAB));
			}
		}.setDefaultFormProcessing(false));
        
        form.add(editor);
		
	}

	
	/**
	 * DetailsForm form
	 */
	/*
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
	*/
	

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
