package org.sakaiproject.content.repository.logic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.sakaiproject.authz.api.SecurityAdvisor;
import org.sakaiproject.authz.api.SecurityService;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.sakaiproject.content.api.ContentHostingService;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.content.api.ContentResourceEdit;
import org.sakaiproject.content.repository.model.ContentItem;
import org.sakaiproject.content.repository.model.LearningObject;
import org.sakaiproject.content.repository.model.SearchItem;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.NotificationService;
import org.sakaiproject.search.api.InvalidSearchQueryException;
import org.sakaiproject.search.api.SearchList;
import org.sakaiproject.search.api.SearchResult;
import org.sakaiproject.search.api.SearchService;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.user.api.UserNotDefinedException;

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
	
	@Getter @Setter
	private ContentHostingService contentHostingService;
	
	
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
		
		List<SearchItem> items = new ArrayList<SearchItem>();
		
		Iterator iter = l.iterator();
		while(iter.hasNext()){
			SearchResult r = (SearchResult) iter.next();
			
			SearchItem si = new SearchItem();
			si.setTitle(r.getTitle());
			si.setUrl(r.getUrl());
			
			try {
				si.setPreview(r.getSearchResult());
			} catch (Exception e){
				//we dont care, it just wont be set
			}
			
			try {
				System.out.println("terms:" + Arrays.asList(r.getTerms().getTerms()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			items.add(si);
			
		}
		
		return items;
		
	}
	
	/**
	 * Get a list of all resources in the current site.
	 * 
	 * Transforms from the Sakai ContentResource to an internal ContentItem
	 * 
	 * @param filter optional filter string for post processing of the list
	 * 
	 * @return
	 */
	public List<ContentItem> getResources(String filter) {
		
		List<ContentItem> items = new ArrayList<ContentItem>();
		
		String currentSiteCollectionId = contentHostingService.getSiteCollection(getCurrentSiteId());
		log.debug("currentSiteCollectionId: " + currentSiteCollectionId);
		
		List<ContentResource> resources = contentHostingService.getAllResources(currentSiteCollectionId);
				
		ContentResourceHelper helper = new ContentResourceHelper();
		
		for(ContentResource resource: resources) {
			
			//setup the helper for this resource item
			helper.setResource(resource);
			
			//first check it matches any filter, if set, otherwise skip.
			if(StringUtils.isNotBlank(filter)) {
				if (!helper.resourceMatchesFilter(filter)) {
					continue;
				}
			}
			
			ContentItem item = new ContentItem();
			item.setSize(resource.getContentLength());
			item.setUrl(resource.getUrl());
			item.setMimeType(resource.getContentType());
			item.setTitle(helper.getTitle());
			item.setAuthor(helper.getCreator());
			item.setModifiedDate(helper.getModifiedDate());
			
			System.out.println(item.toString());
						
			items.add(item);
		}
		
		
		return items;
	}

	/**
	 * Get the display name of a user
	 * 
	 * @param uuid 
	 * @return
	 */
	public String getUserDisplayName(String uuid) {
		try {
			return userDirectoryService.getUser(uuid).getDisplayName();
		} catch (UserNotDefinedException e){
			return uuid;
		}
	}
	
	/**
	 * Get a learning object
	 * @param id - id of the object in CHS
	 * @return
	 */
	public LearningObject getLearningObject(String id) {
		
		//find a LO by its id
		return new LearningObject();
				
	}
	
	/**
	 * Add a new learning object to the repository. This should be called when all data is ready to be added.
	 * 
	 * @param lo
	 * @return LearningObject with ID field populated
	 */
	public LearningObject addNewLearningObject(LearningObject lo) {
				
		ContentResourceEdit resource = null;
		int status = 0;
		
		String currentSiteCollectionId = contentHostingService.getSiteCollection(getCurrentSiteId());
		String baseName = FilenameUtils.getBaseName(lo.getFilename());
		String extension = FilenameUtils.getExtension(lo.getFilename());
		
		SecurityAdvisor advisor = enableSecurityAdvisor();
		
		try {
								
				resource = contentHostingService.addResource(currentSiteCollectionId, baseName, extension, 100);
				//resource.setContent(lo.getContents());
				resource.setContentType(lo.getMimetype());
				
				
				contentHostingService.commitResource(resource, NotificationService.NOTI_NONE);
				
				//populate ID of LO field with resource ID field
				lo.setId(resource.getId());


				/*
				resource.set
				
				ResourceProperties props = resource.getPropertiesEdit();
				props.addProperty(ResourceProperties.PROP_CONTENT_TYPE, mimeType);
				props.addProperty(ResourceProperties.PROP_DISPLAY_NAME, fileName);
				props.addProperty(ResourceProperties.PROP_CREATOR, userId);
				resource.getPropertiesEdit().set(props);
				contentHostingService.commitResource(resource, NotificationService.NOTI_NONE);
				result = true;
				*/
			
			//catch (IdUsedException e){
			//	contentHostingService.cancelResource(resource);
				//log.error("SakaiProxy.saveFile(): id= " + fullResourceId + " is in use : " + e.getClass() + " : " + e.getMessage());
				//result = false;
			//}
			
			
		} catch (Exception e) {
			
			contentHostingService.cancelResource(resource);
			log.error("addNewLearningObject: failed: " + e.getClass() + " : " + e.getMessage());
			e.printStackTrace();
			//result = false;
		} finally {
			disableSecurityAdvisor(advisor);
		}
		
		//now populate the ID field of the LO with the
		
		return lo;
		
	}
	
	
	
	/**
	 * Enable an advisor
	 * @return
	 */
	private SecurityAdvisor enableSecurityAdvisor() {
		SecurityAdvisor advisor = new SecurityAdvisor() {
            public SecurityAdvice isAllowed(String userId, String function, String reference) {
                return SecurityAdvice.ALLOWED;
            }
        };
        securityService.pushAdvisor(advisor);
        
        return advisor;

	}
	
	/**
	 * Disable the advisor
	 * @param advisor
	 */
	private void disableSecurityAdvisor(SecurityAdvisor advisor){
		securityService.popAdvisor(advisor);
	}
	
	/**
	 * WRite a file upload out to disk and return the path to the file so that we can keep a reference to it, without keeping the whole file in memory
	 * 
	 * @param is
	 * @return
	 */
	private String stashUploadedFile(InputStream is) {
		
		return "somepath";
	}
	
	
}
