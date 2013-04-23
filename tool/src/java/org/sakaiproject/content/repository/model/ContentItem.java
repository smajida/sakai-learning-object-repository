package org.sakaiproject.content.repository.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * Data to model a content item
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
@Data
@ToString
public class ContentItem implements Serializable {

	private String id;
	private String title;
	private String url;
	private long size;
	private String mimeType;
	private String author;
	private String modifiedDate; //stores milliseconds as a String
	private int revision;

}
