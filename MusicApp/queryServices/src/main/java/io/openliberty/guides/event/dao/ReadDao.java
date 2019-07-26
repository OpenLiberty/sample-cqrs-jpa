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

import io.openliberty.guides.event.models.Music;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ReadDao {

    @PersistenceContext(name = "jpa-unit")
    private EntityManager emRead;

public void createMusic(Music music) {
        emRead.persist(music);
    }
 
  public Music readMusic(int musicId){
      return emRead.find(Music.class, musicId);
  };
  public List<Music> readAllMusic(){
      return emRead.createNamedQuery("Music.findAll", Music.class).getResultList();
      
  };
  
    public List<Music> findMusic(String name, String artist, String price, String likes) {
        return emRead.createNamedQuery("Music.findMusic", Music.class)
            .setParameter("name", name)
            .setParameter("artist", artist)
            .setParameter("price", price)
            .setParameter("likes", likes).getResultList();
    }
 
}
