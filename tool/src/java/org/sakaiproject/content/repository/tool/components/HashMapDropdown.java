package org.sakaiproject.content.repository.tool.components;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Generic dropdown choice component that takes a hashmap
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class HashMapDropdown extends DropDownChoice {
	
	public HashMapDropdown(String id, final LinkedHashMap<Integer,String> options) {
		super(id);
		
		//model that wraps our options
		IModel optionsModel = new Model() {
			public ArrayList<Integer> getObject() {
				 return new ArrayList(options.keySet());
			} 
		};
		
		setChoices(optionsModel);
		setChoiceRenderer(new HashMapChoiceRenderer(options));

	}

}
