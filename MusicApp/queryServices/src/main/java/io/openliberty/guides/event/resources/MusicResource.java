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
package io.openliberty.guides.event.resources;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import io.openliberty.guides.event.dao.ReadDao;
import io.openliberty.guides.event.models.Music;

@RequestScoped
@Path("music")
public class MusicResource {

    @Inject
    private ReadDao readDAO;
    
    /**
     * This method returns a specific existing/stored event in Json format
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonObject getMusic(@PathParam("id") int musicId) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        Music music = readDAO.readMusic(musicId);
        if(music != null) {
        	if (music.getName() != null && music.getPrice() != null && music.getArtist() != null && music.getLikes() != null){
            builder.add("name", music.getName()).add("price", music.getPrice())
                .add("artist", music.getArtist()).add("id", music.getId()).add("likes", music.getLikes());
           	}
        }
        return builder.build();
    }

    /**
     * This method returns the existing/stored music in Json format
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonArray getAllMusic() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder finalArray = Json.createArrayBuilder();
        for (Music music : readDAO.readAllMusic()) {
        	if (music.getName() != null && music.getPrice() != null && music.getArtist() != null && music.getLikes() != null){
            builder.add("name", music.getName()).add("price", music.getPrice())
                   .add("artist", music.getArtist()).add("id", music.getId()).add("likes", music.getLikes());
            finalArray.add(builder.build());
        	} else{
         		continue;
         	}
        }
        return finalArray.build();
    }

    /**
     * This method returns the top 10 songs in JSON format
     */
    @GET
    @Path("top")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonArray getTopMusic() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder finalArray = Json.createArrayBuilder();
        for (Music music : readDAO.readTopMusic()) {
        	if (music.getName() != null && music.getPrice() != null && music.getArtist() != null && music.getLikes() != null){
            builder.add("name", music.getName()).add("price", music.getPrice())
                   .add("artist", music.getArtist()).add("id", music.getId()).add("likes", music.getLikes());
            finalArray.add(builder.build());
        	} else{
         		continue;
         	}
        }
        return finalArray.build();
    }
}
