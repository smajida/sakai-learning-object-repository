package org.sakaiproject.content.repository.tool;

import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.resource.loader.BundleStringResourceLoader;
import org.apache.wicket.resource.loader.IStringResourceLoader;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.sakaiproject.content.repository.tool.pages.BrowsePage;
import org.sakaiproject.util.ResourceLoader;

/**
 * Main application class for our app
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class RepositoryApp extends WebApplication {    
   
	public static final int MAX_SEARCH_ITEMS_PER_PAGE = 5;
	public static final int MAX_CONTENT_ITEMS_PER_PAGE = 100;
	public static final int MAX_FILE_SIZE_MB= 100;

	public static final String CONTENT_IMAGE_BASE_URL = "/library/image/";
	public static final String CONTENT_IMAGE_DEFAULT = "sakai/generic.gif";
	
	public static final String PARAM_TAB = "tab";
	
	/**
	 * Configure your app here
	 */
	@Override
	protected void init() {
		
		//Configure for Spring injection
		addComponentInstantiationListener(new SpringComponentInjector(this));
		
		//Don't throw an exception if we are missing a property, just fallback
		getResourceSettings().setThrowExceptionOnMissingResource(false);
		
		//Remove the wicket specific tags from the generated markup
		getMarkupSettings().setStripWicketTags(true);
		
		//Don't add any extra tags around a disabled link (default is <em></em>)
		getMarkupSettings().setDefaultBeforeDisabledLink(null);
		getMarkupSettings().setDefaultAfterDisabledLink(null);
				
		// On Wicket session timeout, redirect to main page
		getApplicationSettings().setPageExpiredErrorPage(BrowsePage.class);
		getApplicationSettings().setAccessDeniedPage(BrowsePage.class);
		
		// Add a custom resource loader for the extra properties file
		getResourceSettings().addStringResourceLoader(new ContentTypeImagesResourceLoader());		
		
		//to put this app into deployment mode, see web.xml
		
	}
	
	/**
	 *  Throw RuntimeExceptions so they are caught by the Sakai ErrorReportHandler(non-Javadoc)
	 *  
	 * @see org.apache.wicket.protocol.http.WebApplication#newRequestCycle(org.apache.wicket.Request, org.apache.wicket.Response)
	 */
	@Override
	public RequestCycle newRequestCycle(Request request, Response response) {
		return new WebRequestCycle(this, (WebRequest)request, (WebResponse)response) {
			@Override
			public Page onRuntimeException(Page page, RuntimeException e) {
				throw e;
			}
		};
	}
	
	//Custom resource loader for the images properties
	private static class ContentTypeImagesResourceLoader implements IStringResourceLoader {
		
		private ResourceLoader messages = new ResourceLoader("content_type_images");
		
		public String loadStringResource(Component component, String key) {
			return messages.getString(key);
		}

		public String loadStringResource(Class clazz, String key, Locale locale, String style) {
			return messages.getString(key);
		}

	}
	
	/**
	 * The main page for our app
	 * 
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<BrowsePage> getHomePage() {
		return BrowsePage.class;
	}
	
	
	/**
     * Constructor
     */
	public RepositoryApp()
	{
	}
	
	

}
