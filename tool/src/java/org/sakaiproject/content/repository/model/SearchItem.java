package org.sakaiproject.content.repository.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An example thing
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchItem implements Serializable {

	private long id;
	private String name;
}
