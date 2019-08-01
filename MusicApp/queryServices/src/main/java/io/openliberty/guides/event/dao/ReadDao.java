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
 
    public Music readMusic(int musicId){
        Music temp = emRead.find(Music.class, musicId);
        if (temp != null) {
            emRead.refresh(temp);
        }
        return temp;
    };

    public List<Music> readAllMusic(){
        List<Music> allMusic = emRead.createNamedQuery("Music.findAll", Music.class).getResultList();
        for (Music music : allMusic) {
            emRead.refresh(music);
        }
        return allMusic;
    };
  
    public List<Music> findMusic(String name, String artist, String price, String likes) {
        return emRead.createNamedQuery("Music.findMusic", Music.class)
            .setParameter("name", name)
            .setParameter("artist", artist)
            .setParameter("price", price)
            .setParameter("likes", likes).getResultList();
    }

    public List<Music> findMusicByName(String name) {
        return emRead.createNamedQuery("Music.findMusicByName", Music.class)
            .setParameter("name", name).getResultList();
    }
    
 
}
