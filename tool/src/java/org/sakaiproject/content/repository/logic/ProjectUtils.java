package org.sakaiproject.content.repository.logic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
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
	
}
