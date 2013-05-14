package org.sakaiproject.content.repository.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Structure to hold a list of ChangeHitosy items. For XML purposes.
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
@Data
@Root(name="change_history_list")
public class ChangeHistoryList implements Serializable {

	@ElementList(name="change_history", required=false)
	public List<ChangeHistory> history = new ArrayList<ChangeHistory>();
}
