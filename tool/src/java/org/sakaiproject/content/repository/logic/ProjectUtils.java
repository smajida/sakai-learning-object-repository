package org.sakaiproject.content.repository.logic;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.math.NumberUtils;
import org.sakaiproject.util.ResourceLoader;

/**
 * Util class for the repo app
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class ProjectUtils {

	/**
	 * Convert milliseconds string into a localised date string
	 * @param millis	String of the milliseconds
	 * @return
	 */
	public static String toDateStringFromResources(String millis) {
				
		
		//format 20130124101010761
		//need to ajust this
		Date d = new Date(NumberUtils.toLong(millis));
		
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
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
	
}
