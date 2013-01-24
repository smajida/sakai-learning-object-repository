package org.sakaiproject.content.repository.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;

import org.sakaiproject.authz.api.SecurityService;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.sakaiproject.content.repository.model.SearchItem;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.search.api.InvalidSearchQueryException;
import org.sakaiproject.search.api.SearchList;
import org.sakaiproject.search.api.SearchService;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.UserDirectoryService;

/**
 * Logic class for our app.
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
@CommonsLog
public class ProjectLogic {

	@Getter @Setter
	private ToolManager toolManager;
	
	@Getter @Setter
	private SessionManager sessionManager;
	
	@Getter @Setter
	private UserDirectoryService userDirectoryService;
	
	@Getter @Setter
	private SecurityService securityService;
	
	@Getter @Setter
	private EventTrackingService eventTrackingService;
	
	@Getter @Setter
	private ServerConfigurationService serverConfigurationService;
	
	@Getter @Setter
	private SiteService siteService;
	
	@Getter @Setter
	private SearchService searchService;
	
	
	/**
	 * init - perform any actions required here for when this bean starts up
	 */
	public void init() {
		log.info("init");
	}


	/**
	 * Get current siteid
	 * @return
	 */
	public String getCurrentSiteId(){
		return toolManager.getCurrentPlacement().getContext();
	}
	
	/**
	 * Get current user id
	 * @return
	 */
	public String getCurrentUserId() {
		return sessionManager.getCurrentSessionUserId();
	}
	
	/**
	 * Get current user display name
	 * @return
	 */
	public String getCurrentUserDisplayName() {
	   return userDirectoryService.getCurrentUser().getDisplayName();
	}
	
	/**
	 * Is the current user a superUser? (anyone in admin realm)
	 * @return
	 */
	public boolean isSuperUser() {
		return securityService.isSuperUser();
	}
	
	/**
	 * Post an event to Sakai
	 * 
	 * @param event			name of event
	 * @param reference		reference
	 * @param modify		true if something changed, false if just access
	 * 
	 */
	public void postEvent(String event,String reference,boolean modify) {
		eventTrackingService.post(eventTrackingService.newEvent(event,reference,modify));
	}
	
	/**
	 * Wrapper for ServerConfigurationService.getString("skin.repo")
	 * @return
	 */
	public String getSkinRepoProperty(){
		return serverConfigurationService.getString("skin.repo");
	}
	
	/**
	 * Gets the tool skin CSS first by checking the tool, otherwise by using the default property.
	 * @param	the location of the skin repo
	 * @return
	 */
	public String getToolSkinCSS(String skinRepo){
		
		String skin = siteService.findTool(sessionManager.getCurrentToolSession().getPlacementId()).getSkin();			
		
		if(skin == null) {
			skin = serverConfigurationService.getString("skin.default");
		}
		
		return skinRepo + "/" + skin + "/tool.css";
	}
	
	/**
	 * Get a configuration parameter as a boolean
	 * 
	 * @param	dflt the default value if the param is not set
	 * @return
	 */
	public boolean getConfigParam(String param, boolean dflt) {
		return serverConfigurationService.getBoolean(param, dflt);
	}
	
	/**
	 * Get a configuration parameter as a String
	 * 
	 * @param	dflt the default value if the param is not set
	 * @return
	 */
	public String getConfigParam(String param, String dflt) {
		return serverConfigurationService.getString(param, dflt);
	}
	
	/**
	 * Perform a search
	 * @param s	The search string
	 * @return
	 */
	public List<SearchItem> performSearch(String s) {
		SearchList l = null;
		try {
			l = searchService.search(s, Collections.singletonList(getCurrentSiteId()), 0, 100);
		} catch (InvalidSearchQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Search results:" + l.size());
		
		//convert search result list into internal object list
		List<SearchItem> items = new ArrayList<SearchItem>();
		
		
		return items;
		
	}
	

}
