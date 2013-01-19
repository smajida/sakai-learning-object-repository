package org.sakaiproject.content.repository.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.sakaiproject.content.repository.model.Thing;

/**
 * Implementation of {@link ProjectLogic}
 * 
 * @author Steve Swinsburg (steve.swinsburg@anu.edu.au)
 *
 */
public class ProjectLogicImpl implements ProjectLogic {

	private static final Logger log = Logger.getLogger(ProjectLogicImpl.class);

	
	/**
	 * {@inheritDoc}
	 */
	public Thing getThing(long id) {
		/*
		//check cache 
		Element element = cache.get(id);
		if(element != null) {
			if(log.isDebugEnabled()) {
				log.debug("Fetching item from cache for: " + id);
			}
			return (Thing)element.getValue();
		}
		
		//if nothing from cache, get from db and cache it 
		Thing t = dao.getThing(id);
			
		if(t != null) {
			if(log.isDebugEnabled()) {
				log.debug("Adding item to cache for: " + id);
			}
			cache.put(new Element(id,t));
		}

		return t;
		*/
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<Thing> getThings() {
		return new ArrayList<Thing>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean addThing(Thing t) {
		//return dao.addThing(t);
		return false;
	}
	
	/**
	 * init - perform any actions required here for when this bean starts up
	 */
	public void init() {
		log.info("init");
	}


}
