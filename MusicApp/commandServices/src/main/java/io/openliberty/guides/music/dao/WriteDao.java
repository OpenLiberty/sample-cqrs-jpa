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
package io.openliberty.guides.music.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.openliberty.guides.music.models.Music;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WriteDao {
    @PersistenceContext(name = "jpa-unit")
    private EntityManager emWrite;

    public void createMusic(Music music) {
        emWrite.persist(music);
    }

    public void updateMusic(Music music) {
        emWrite.merge(music);
    }

    public void deleteMusic(Music music) {
        emWrite.remove(music);
    }

    public Music readMusic(int musicId){
        return emWrite.find(Music.class, musicId);
    };

    public List<Music> readAllMusic(){
        return emWrite.createNamedQuery("Music.findAll", Music.class).getResultList();
        
    };

    public List<Music> findMusic(String name, String artist, String price, String likes) {
        return emWrite.createNamedQuery("Music.findMusic", Music.class)
            .setParameter("name", name)
            .setParameter("artist", artist)
            .setParameter("price", price)
            .setParameter("likes", likes).getResultList();
    }
}