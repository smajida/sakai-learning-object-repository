package org.sakaiproject.content.repository.model;

import lombok.Data;

/**
 * Data to model a content item
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
@Data
public class ContentItem {

	private String title;
	private String url;
	private long size;
	private String mimeType;
}
