/* // tag::copyright[]
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
package it.io.openliberty.guides.music;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import io.openliberty.guides.music.models.Music;

public abstract class MusicTest {

    private WebTarget webTarget;

    protected Form form;
    protected Client client;
    protected Response response;
    protected HashMap<String, String> musicForm;

    protected static String baseUrl;
    protected static String port;
    protected static final String MUSICS = "musics";

    /**
     *  Makes a POST request to the /musics endpoint
     */
    protected int postRequest(HashMap<String, String> formDataMap) {
        formDataMap.forEach((formField, data) -> {
            form.param(formField, data);
        });
        webTarget = client.target(baseUrl + MUSICS);
        response = webTarget.request().post(Entity.form(form));
        form = new Form();
        return response.getStatus();
    }

    /**
     *  Makes a PUT request to the /musics/{musicId} endpoint
     */
    protected int updateRequest(HashMap<String, String> formDataMap, int musicId) {
        formDataMap.forEach((formField, data) -> {
            form.param(formField, data);
        });
        webTarget = client.target(baseUrl + MUSICS + "/" + musicId);
        response = webTarget.request().put(Entity.form(form));
        form = new Form();
        return response.getStatus();
    }
    
    /**
     *  Makes a DELETE request to /musics/{musicId} endpoint and return the response 
     *  code 
     */
    protected int deleteRequest(int musicId) {
        webTarget = client.target(baseUrl + MUSICS + "/" + musicId);
        response = webTarget.request().delete();
        return response.getStatus();
    }
    
    /**
     *  Makes a GET request to the /musics endpoint and returns result in a JsonArray
     */
    protected JsonArray getRequest() {
        webTarget = client.target(baseUrl + MUSICS);
        response = webTarget.request().get();
        return response.readEntity(JsonArray.class);
    }

    /**
     *  Makes a GET request to the /musics/{musicId} endpoint and returns a JsonObject
     */ 
    protected JsonObject getIndividualMusic(int musicId) {
        webTarget = client.target(baseUrl + MUSICS + "/" + musicId);
        response = webTarget.request().get();
        return response.readEntity(JsonObject.class);
    }
    
    /**
     *  Makes a GET request to the /musics endpoint and returns the music provided
     *  if it exists. 
     */
    protected JsonObject findMusic(Music e) {
        JsonArray musics = getRequest();
        for (int i = 0; i < musics.size(); i++) {
            JsonObject testMusic = musics.getJsonObject(i);
            Music test = new Music(testMusic.getString("name"),
                    testMusic.getString("artist"), testMusic.getString("price"), testMusic.getString("likes"));
            if (test.equals(e)) {
                return testMusic;
            }
        }
        return null;
    }

    /**
     *  Asserts music fields (name, artist, time) equal the provided name, artist
     *  and date
     */
    protected void assertData(JsonObject music, String name, String artist, String price, String likes) {
        assertEquals(music.getString("name"), name);
        assertEquals(music.getString("artist"), artist);
        assertEquals(music.getString("pricd"), price);
        assertEquals(music.getString("likes"), likes);
    }

}