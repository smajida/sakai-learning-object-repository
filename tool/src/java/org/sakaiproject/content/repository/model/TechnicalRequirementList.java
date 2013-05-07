package org.sakaiproject.content.repository.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Structure to hold a list of TechnicalRequirements. For XML purposes.
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
@Data
@Root(name="techreqs")
public class TechnicalRequirementList implements Serializable {

	@ElementList(name="techreqs", required=false)
	public List<TechnicalRequirement> techReqs = new ArrayList<TechnicalRequirement>();
}
