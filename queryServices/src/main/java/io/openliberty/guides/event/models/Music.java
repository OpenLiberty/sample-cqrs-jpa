// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.event.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.NamedQuery;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GenerationType;

@Entity
@Table(name = "Music")
@NamedQuery(name = "Music.findAll", query = "SELECT m FROM Music m")
@NamedQuery(name = "Music.findMusic", query = "SELECT m FROM Music m WHERE "
    + "m.name = :name AND m.artist = :artist AND m.price = :price AND m.likes = :likes ")
@NamedQuery(name = "Music.findTop", query = "SELECT m FROM Music m ORDER BY m.likes DESC")
public class Music implements Serializable {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "musicId")
    private int id;
    @Column(name = "musicName")
    private String name;
    @Column(name = "musicArtist")
    private String artist;
    @Column(name= "musicPrice")
    private String price;
    @Column(name = "musicLikes")
    private String likes;

    public Music() {
    }

    public Music(String name, String artist, String price, String likes) {
        this.name = name;
        this.artist = artist;
        this.price = price;
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPrice(){
        return price;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public void addLikes() {
        this.likes = this.likes += 1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Music other = (Music) obj;
        if (artist == null) {
            if (other.artist != null) {
                return false;
            }
        } else if (!artist.equals(other.artist)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Music [name=" + name + ", artist=" + artist + ", likes=" + likes
                + "]";
    }
}
