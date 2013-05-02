package org.sakaiproject.content.repository.logic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.sakaiproject.util.ResourceLoader;

/**
 * Util class for the repo app
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class ProjectUtils {

	/**
	 * Convert timestamp string into a localised date string
	 * @param timestamp		timestamp of format 20130124101010761
	 * @return
	 */
	public static String toDateStringFromResources(String timestamp) {
		
		//input
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		Date d = null;
		try {
			d = format.parse(timestamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
						
		//output
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
		String s = formatter.format(d);

		return s;
	}
	
	/**
	 * Gets the users preferred locale, either from the user's session or Sakai preferences and returns it
	 * This depends on Sakai's ResourceLoader.
	 * 
	 * @return
	 */
	public static Locale getUserPreferredLocale() {
		ResourceLoader rl = new ResourceLoader();
		return rl.getLocale();
	}
	
	/**
	 * Format a file size into a display string
	 * @param size	file size
	 * @return
	 */
	public static String formatSize(long size) {
		return FileUtils.byteCountToDisplaySize(size);
	}
	
	/**
	 * Using the basename, get the properties for use in a dropdown. Properties come from dropdown_options.properties.
	 * 
	 * Format is:
	 * dropdown.engagement.count=3
	 * dropdown.engagement.1=active
	 * dropdown.engagement.1.label=Active
	 * dropdown.engagement.2=expositive
	 * dropdown.engagement.2.label=Expositive
	 * dropdown.engagement.3=mixed
	 * dropdown.engagement.3.label=Mixed
	 * @param basename eg dropdown.engagement
	 * @return map of options based on what we found
	 */
	public static LinkedHashMap<?,?> getLabelledDropdownMap(String basename) {
	
		LinkedHashMap map = new LinkedHashMap();
		
		ResourceLoader rl = new ResourceLoader("dropdown_options");
		
		int count = rl.getInt(basename + ".count", 0);
		for(int i=1;i<=count; i++) {
			map.put(rl.get(basename + "."  +i), rl.get(basename + "."  + i + ".label"));
		}
		
		return map;
	}
	
	
}
