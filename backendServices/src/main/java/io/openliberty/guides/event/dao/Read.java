// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2018, 2019 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.event.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.openliberty.guides.event.models.Event;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class Read implements ReadInterface{
	 @PersistenceContext(name = "jpa-unit")
	    private EntityManager em_read;
	 
	  public Event readEvent(int eventId){
		  return em_read.find(Event.class, eventId);
	  };
	  public List<Event> readAllEvents(){
		  return em_read.createNamedQuery("Event.findAll", Event.class).getResultList();
		  
	  };
	  
	    public List<Event> findEvent(String name, String location, String time) {
	        return em_read.createNamedQuery("Event.findEvent", Event.class)
	            .setParameter("name", name)
	            .setParameter("location", location)
	            .setParameter("time", time).getResultList();
	    }
	 
}
