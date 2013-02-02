package org.sakaiproject.content.repository.tool.components;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.Model;

/**
 * Component to render an image outside of the wicket application
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class ExternalImage extends WebComponent {

	private static final long serialVersionUID = 1L;

	/**
	 * Render an image into an 'img' tag with the wicket:id and src url
	 * @param id
	 * @param imageUrl
	 */
	public ExternalImage(String id, String imageUrl) {
	    super(id);
	    add(new AttributeModifier("src", true, new Model(imageUrl)));
	    setVisible(!(imageUrl==null || imageUrl.equals("")));
	}
	
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		checkComponentTag(tag, "img");
	}

}
