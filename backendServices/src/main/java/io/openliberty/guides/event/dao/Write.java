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

public class Write implements WriteInterface{
	
	@PersistenceContext(name = "jpa-unit")
	private EntityManager em_write;
	
	public void createEvent(Event event){
		em_write.persist(event);
	}

    public void updateEvent(Event event){
    	em_write.merge(event);
    }

    public void deleteEvent(Event event){
    	em_write.remove(event);
    }
}
