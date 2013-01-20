package org.sakaiproject.content.repository.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Model for the data in a simple search
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class SimpleSearch implements Serializable {

	@Getter @Setter
	private String searchString;
}
