package org.sakaiproject.content.repository.tool.pages;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sakaiproject.content.repository.logic.ProjectLogic;


/**
 * This is our base page for our Sakai app. It sets up the containing markup and top navigation.
 * All top level pages should extend from this page so as to keep the same navigation. The content for those pages will
 * be rendered in the main area below the top nav.
 * 
 * <p>It also allows us to setup the API injection and any other common methods, which are then made available in the other pages.
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class BasePage extends WebPage implements IHeaderContributor {

	private static final Logger log = Logger.getLogger(BasePage.class); 
	
	@SpringBean(name="org.sakaiproject.content.repository.logic.ProjectLogic")
	protected ProjectLogic logic;
	
	Link<Void> browseLink;
	Link<Void> addLink;
	Link<Void> searchLink;
	
	FeedbackPanel feedbackPanel;
	
	public BasePage() {
		
		log.debug("BasePage()");
		
		browseLink = new Link<Void>("browseLink") {
			private static final long serialVersionUID = 1L;
			public void onClick() {
				setResponsePage(new BrowsePage());
			}
		};
		add(browseLink);
		
		addLink = new Link<Void>("addLink") {
			private static final long serialVersionUID = 1L;
			public void onClick() {
				setResponsePage(new ContentItemPage());
			}
		};
		add(addLink);
		
		
		searchLink = new Link<Void>("searchLink") {
			private static final long serialVersionUID = 1L;
			public void onClick() {
				setResponsePage(new SearchPage());
			}
		};
		add(searchLink);
		
		
		// Add a FeedbackPanel for displaying our messages
    feedbackPanel = new FeedbackPanel("feedback"){
    	
    	@Override
    	protected Component newMessageDisplayComponent(final String id, final FeedbackMessage message) {
    		final Component newMessageDisplayComponent = super.newMessageDisplayComponent(id, message);

    		if(message.getLevel() == FeedbackMessage.ERROR ||
    			message.getLevel() == FeedbackMessage.DEBUG ||
    			message.getLevel() == FeedbackMessage.FATAL ||
    			message.getLevel() == FeedbackMessage.WARNING){
    			add(new SimpleAttributeModifier("class", "alertMessage"));
    		} else if(message.getLevel() == FeedbackMessage.INFO){
    			add(new SimpleAttributeModifier("class", "success"));        			
    		} 

    		return newMessageDisplayComponent;
    	}
    };
    add(feedbackPanel);
    
    //add message about resources tool
    add(new Label("resourcesConflict", new ResourceModel("error.resources.tool.exists")) {
    	
    	@Override
    	public boolean isVisible() {
    		return logic.doesResourcesToolExist();
    	}
    	
    });
		
	}
	
	/**
	 * Helper to clear the feedbackpanel display.
	 * @param f	FeedBackPanel
	 */
	public void clearFeedback(FeedbackPanel f) {
		if(!f.hasFeedbackMessage()) {
			f.add(new SimpleAttributeModifier("class", ""));
		}
		f.setVisible(false);
	}
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * This block adds the required wrapper markup to style it like a Sakai tool. 
	 * Add to this any additional CSS or JS references that you need.
	 * 
	 */
	public void renderHead(IHeaderResponse response) {
		
		//get the Sakai skin header fragment from the request attribute
		HttpServletRequest request = getWebRequestCycle().getWebRequest().getHttpServletRequest();
		response.renderString((String)request.getAttribute("sakai.html.head"));
		response.renderOnLoadJavascript("setMainFrameHeight( window.name )");
		
		//Tool additions (at end so we can override if required)
		response.renderString("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		response.renderCSSReference("css/repository.css");
		//response.renderJavascriptReference("js/my_tool_javascript.js");
		
	}
	
	
	/** 
	 * Helper to disable a link. Add the Sakai class 'current'.
	 */
	protected void disableLink(Link<Void> l) {
		l.add(new SimpleAttributeModifier("class", "current"));
		//l.setRenderBodyOnly(true);
		l.setEnabled(false);
	}
	
	
	
}
