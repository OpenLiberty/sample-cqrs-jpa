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
package io.openliberty.guides.music.resources;

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

import io.openliberty.guides.music.dao.WriteDao;
import io.openliberty.guides.music.models.Music;

@RequestScoped
@Path("music")
public class MusicResource {
/* 
    @Inject
    private ReadDao readDAO; */

    @Inject
    private WriteDao writeDAO;

    /**
     * This method creates a new music from the submitted data (name, artist and
     * price) by the user.
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response addNewMusic(@FormParam("name") String name,
        @FormParam("artist") String artist, @FormParam("price") String price, @FormParam("likes") String likes) {
        Music newMusic = new Music(name, artist, price, likes);
        if(!writeDAO.findMusic(name, artist, price, likes).isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Music already exists").build();
        }
        writeDAO.createMusic(newMusic);
        return Response.status(Response.Status.NO_CONTENT).build(); 
    }

    /**
     * This method updates a new music from the submitted data (name, artist and
     * price) by the user.
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response updateMusic(@FormParam("name") String name,
        @FormParam("artist") String artist, @FormParam("price") String price, @FormParam("likes") String likes,
        @PathParam("id") int id) {
        Music prevMusic = writeDAO.readMusic(id);
        if(prevMusic == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Music does not exist").build();
        }
        if(!writeDAO.findMusic(name, artist, price, likes).isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Music already exists").build();
        }
        prevMusic.setName(name);
        prevMusic.setArtist(artist);
        prevMusic.setPrice(price);
        prevMusic.setLikes(likes);

        writeDAO.updateMusic(prevMusic);
        return Response.status(Response.Status.NO_CONTENT).build(); 
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response addLike(
        @PathParam("id") int id) {
        Music prevMusic = writeDAO.readMusic(id);
        if(prevMusic == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Music does not exist").build();
        }
        prevMusic.addLike();
        writeDAO.updateMusic(prevMusic);
        return Response.status(Response.Status.NO_CONTENT).build(); 
    }

    /**
     * This method deletes a specific existing/stored music
     */
    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteMusic(@PathParam("id") int id) {
        Music music = writeDAO.readMusic(id);
        if(music == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Music does not exist").build();
        }
        writeDAO.deleteMusic(music);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    /**
     * This method returns a specific existing/stored music in Json format
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonObject getMusic(@PathParam("id") int musicId) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        Music music = writeDAO.readMusic(musicId);
        if(music != null) {
            builder.add("name", music.getName()).add("artist", music.getArtist())
                .add("price", music.getPrice()).add("likes", music.getLikes()).add("id", music.getId());
        }
        return builder.build();
    }

    /**
     * This method returns the existing/stored  in Json format
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonArray getAllMusic() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder finalArray = Json.createArrayBuilder();
        for (Music music : writeDAO.readAllMusic()) {
            builder.add("name", music.getName()).add("price", music.getPrice())
                   .add("artist", music.getArtist()).add("id", music.getId()).add("likes", music.getLikes());
            finalArray.add(builder.build());
        }
        return finalArray.build();
    } 
}
