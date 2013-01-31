package org.sakaiproject.content.repository.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data to model a search result item
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
@Data
public class SearchItem implements Serializable {

	private String title;
	private String url;
	private String preview;
}
