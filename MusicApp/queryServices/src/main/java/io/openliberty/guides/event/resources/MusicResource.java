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
// import io.openliberty.guides.event.dao.WriteDao;
import io.openliberty.guides.event.models.Music;

@RequestScoped
@Path("music")
public class MusicResource {

    @Inject
    private ReadDao readDAO;

    // @Inject
    // private WriteDao writeDAO;

    /**
     * This method creates a new event from the submitted data (name, time and
     * location) by the user.
     */
/*     @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response addNewMusic(@FormParam("name") String name,
        @FormParam("artist") String artist, @FormParam("price") String price, @FormParam("likes") String likes) {
        Music newMusic = new Music(name, artist, price, likes);
        if(!readDAO.findMusic(name, artist, price, likes).isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Music already exists").build();
        }
        readDAO.createMusic(newMusic);
        return Response.status(Response.Status.NO_CONTENT).build(); 
    } */

    /**
     * This method updates a new event from the submitted data (name, time and
     * location) by the user.
     */
    // @PUT
    // @Path("{id}")
    // @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    // @Transactional
    // public Response updateEvent(@FormParam("name") String name,
    //     @FormParam("time") String time, @FormParam("location") String location,
    //     @PathParam("id") int id) {
    //     Event prevEvent = readDAO.readEvent(id);
    //     if(prevEvent == null) {
    //         return Response.status(Response.Status.NOT_FOUND)
    //                        .entity("Event does not exist").build();
    //     }
    //     if(!readDAO.findEvent(name, location, time).isEmpty()) {
    //         return Response.status(Response.Status.BAD_REQUEST)
    //                        .entity("Event already exists").build();
    //     }
    //     prevEvent.setName(name);
    //     prevEvent.setLocation(location);
    //     prevEvent.setTime(time);

    //     writeDAO.updateEvent(prevEvent);
    //     return Response.status(Response.Status.NO_CONTENT).build(); 
    // }

    /**
     * This method deletes a specific existing/stored event
     */
    // @DELETE
    // @Path("{id}")
    // @Transactional
    // public Response deleteEvent(@PathParam("id") int id) {
    //     Event event = readDAO.readEvent(id);
    //     if(event == null) {
    //         return Response.status(Response.Status.NOT_FOUND)
    //                        .entity("Event does not exist").build();
    //     }
    //     writeDAO.deleteEvent(event);
    //     return Response.status(Response.Status.NO_CONTENT).build();
    // }

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
            builder.add("name", music.getName()).add("price", music.getPrice())
                .add("artist", music.getArtist()).add("id", music.getId()).add("likes", music.getLikes());
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
            builder.add("name", music.getName()).add("price", music.getPrice())
                   .add("artist", music.getArtist()).add("id", music.getId()).add("likes", music.getLikes());
            finalArray.add(builder.build());
        }
        return finalArray.build();
    }

    /**
     * This method returns the existing/stored music in Json format
     */
    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // @Transactional
    // public JsonArray getMusic() {
    //     JsonObjectBuilder builder = Json.createObjectBuilder();
    //     JsonArrayBuilder finalArray = Json.createArrayBuilder();
    //     for (Music music : readDAO.readAllMusic()) {
    //         builder.add("name", music.getName()).add("price", music.getPrice())
    //                .add("artist", music.getArtist()).add("id", music.getId()).add("likes", music.getLikes());
    //         finalArray.add(builder.build());
    //     }
    //     return finalArray.build();
    // }
}
