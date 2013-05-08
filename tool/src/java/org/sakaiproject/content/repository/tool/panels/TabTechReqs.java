package org.sakaiproject.content.repository.tool.panels;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sakaiproject.content.repository.logic.ProjectLogic;
import org.sakaiproject.content.repository.logic.ProjectUtils;
import org.sakaiproject.content.repository.model.FormMode;
import org.sakaiproject.content.repository.model.LearningObject;
import org.sakaiproject.content.repository.model.TechnicalRequirement;
import org.sakaiproject.content.repository.tool.components.HashMapDropdown;
import org.sakaiproject.content.repository.tool.components.ListEditor;
import org.sakaiproject.content.repository.tool.components.ListItem;
import org.sakaiproject.content.repository.tool.components.RemoveButton;
import org.sakaiproject.content.repository.tool.pages.BrowsePage;

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
				boolean result = logic.addNewLearningObject(lo);
				if(result) {
					info(new StringResourceModel("success.lo.created", this, null).getString());
					setResponsePage(new BrowsePage());
				} else {
					error(new StringResourceModel("error.failed.add", this, null).getString());
				}
            }
        };
        add(form);
	        
        editor = new ListEditor<TechnicalRequirement>("techReqs", new PropertyModel(this, "lo.techReqs.techReqs"))
        {
            @Override
            protected void onPopulateItem(ListItem<TechnicalRequirement> item)
            {
                item.setModel(new CompoundPropertyModel(item.getModel()));
                item.add(new HashMapDropdown("techReqType", ProjectUtils.getLabelledDropdownMap("dropdown.tech_req_type")));
                item.add(new TextField("techReqName"));
                item.add(new TextField("techReqMinVersion"));
    			item.add(new TextField("techReqMaxVersion"));
    			item.add(new HashMapDropdown("techReqAndOr", ProjectUtils.getLabelledDropdownMap("dropdown.andor")));
    			item.add(new TextArea("techReqInstallRemarks"));
    			item.add(new TextArea("techReqOther"));		

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
        
        form.add(editor);
		
	}
	

}
