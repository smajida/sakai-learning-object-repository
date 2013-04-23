package org.sakaiproject.content.repository.tool.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;
import org.sakaiproject.content.repository.model.FormMode;
import org.sakaiproject.content.repository.model.LearningObject;
import org.sakaiproject.content.repository.tool.panels.TabFileDetails;
import org.sakaiproject.content.repository.tool.panels.TabLearningObjectDetails;
import org.sakaiproject.content.repository.tool.panels.TabTechReqs;

/**
 * ContentItemPage page, sets up the tabs, able to switch modes based on what we want it to do
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class ContentItemPage extends BasePage {

	private FormMode mode;
	private int selectedTab = 0; //first tab, default
	
	/**
	 * Default constructor if we launch the page normally. We will be adding a new content item
	 */
	public ContentItemPage() {
		mode = FormMode.ADD;
		LearningObject lo = new LearningObject();
		doRender(lo);
	}
	
	/**
	 * Constructor if we are using this page for editing.
	 * @param mode 			FormMode.EDIT
	 * @param resourceId 	id of the resource in CHS that we want to edit
	 */
	public ContentItemPage(FormMode mode, String resourceId) {
		this.mode=mode;
		
		//TODO set the properid so we can get the correct learning object here
		LearningObject lo = new LearningObject();
		
		doRender(lo);
	}
	
	/**
	 * Consutrctor used for switching between tabs
	 * @param lo			Learning Object that is already loaded/in progress.
	 * @param mode			FormMode
	 * @param selectedTab	int for what tab we want
	 */
	public ContentItemPage(LearningObject lo, FormMode mode, int selectedTab) {
		this.mode=mode;
		this.selectedTab=selectedTab;
		
		doRender(lo);
	}
	
	
	private void doRender(final LearningObject lo) {
		disableLink(addLink);
		
		addPageTitle();
		
		// list of tabs
		List<ITab> tabs=new ArrayList<ITab>();
		
		//only show file upload tab if we are adding a file
		if(mode == FormMode.ADD) {
			tabs.add(new AbstractTab(new ResourceModel("tab.title.file")) {
				public Panel getPanel(String panelId) {
					return new TabFileDetails(panelId, lo, mode);
				}
			});
		}
		
		
		tabs.add(new AbstractTab(new ResourceModel("tab.title.lo")) {
			public Panel getPanel(String panelId) {
				return new TabLearningObjectDetails(panelId, lo, mode);
			}
		});
		
		tabs.add(new AbstractTab(new ResourceModel("tab.title.tech")) {
			public Panel getPanel(String panelId) {
				return new TabTechReqs(panelId, lo, mode);
			}
		});
		
		
		//only show if editing or viewing
		if(mode == FormMode.EDIT) {
			tabs.add(new AbstractTab(new ResourceModel("tab.title.history")) {
				public Panel getPanel(String panelId) {
					return new EmptyPanel(panelId);
				}
			});
		}
		
 

		TabbedPanel tabbedPanel = new TabbedPanel("tabs", tabs);
		tabbedPanel.setSelectedTab(selectedTab);
		
		
		add(tabbedPanel);
		
	}
	
	/**
	 * Helper to add the title
	 */
	private void addPageTitle() {
		
		Label title = new Label("pageTitle");
		
		if(mode == FormMode.ADD) {
			title.setDefaultModel(new ResourceModel("contentitem.page.title.add"));
		}
		if(mode == FormMode.EDIT) {
			title.setDefaultModel(new ResourceModel("contentitem.page.title.edit"));
		}
		if(mode == FormMode.SEARCH) {
			title.setDefaultModel(new ResourceModel("contentitem.page.title.search"));
		}
		
		add(title);
		
	}
	
}
