package org.sakaiproject.content.repository.tool.panels;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
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
import org.sakaiproject.content.repository.tool.pages.SearchPage;

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
		
		//stub out at least one techreq if there are none, so the page has a form to begin with
		if(lo.getTechReqs().getTechReqs().isEmpty()) {
			//add a blank one so the page is stubbed
			lo.getTechReqs().getTechReqs().add(new TechnicalRequirement());
		}
		

		Form form = new Form("form") {
			@Override
			protected void onSubmit() {

				if(mode == FormMode.ADD) {
					boolean result = logic.addNewLearningObject(lo);
					if(result) {
						info(new StringResourceModel("success.lo.created", this, null).getString());
						setResponsePage(new BrowsePage());
					} else {
						error(new StringResourceModel("error.failed.add", this, null).getString());
					}
				}
				
				if(mode == FormMode.EDIT) {
					boolean result = logic.updateLearningObject(lo);
					if(result) {
						info(new StringResourceModel("success.lo.updated", this, null).getString());
						setResponsePage(new BrowsePage());
					} else {
						error(new StringResourceModel("error.failed.update", this, null).getString());
					}
				}
				
				if(mode == FormMode.SEARCH) {
					System.out.println("Performing advanced search");
					
					String searchString = logic.getAdvancedSearchString(lo);
					
					setResponsePage(new SearchPage(searchString));
				}
				
				
			}
		};
		add(form);
		
		Label instructions = new Label("instructions", new ResourceModel("instruction.lo.techreqs.1"));
		instructions.setVisible(mode != FormMode.SEARCH);
		form.add(instructions);

		editor = new ListEditor<TechnicalRequirement>("techReqs", new PropertyModel(this, "lo.techReqs.techReqs")) {
			@Override
			protected void onPopulateItem(ListItem<TechnicalRequirement> item) {
				item.setModel(new CompoundPropertyModel(item.getModel()));
				item.add(new HashMapDropdown("techReqType", ProjectUtils.getLabelledDropdownMap("dropdown.tech_req_type")));
				item.add(new TextField("techReqName"));
				item.add(new TextField("techReqMinVersion"));
				item.add(new TextField("techReqMaxVersion"));
				item.add(new HashMapDropdown("techReqAndOr", ProjectUtils.getLabelledDropdownMap("dropdown.andor")));
				item.add(new TextArea("techReqInstallRemarks"));
				item.add(new TextArea("techReqOther"));		

				RemoveButton r = new RemoveButton("remove");
				r.setVisible(mode != FormMode.SEARCH);
				item.add(r);
			}
		};

		//add button
		form.add(new Button("add") {
			@Override
			public void onSubmit() {
				editor.addItem(new TechnicalRequirement());
			}
			
			@Override
			public boolean isVisible() {
				//do not display for searching
				return (mode != FormMode.SEARCH);
			}
			
			
		}.setDefaultFormProcessing(false));

		form.add(editor);

	}


}
