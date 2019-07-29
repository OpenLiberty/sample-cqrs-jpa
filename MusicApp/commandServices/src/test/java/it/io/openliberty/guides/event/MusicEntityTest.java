// // tag::copyright[]
// /*******************************************************************************
//  * Copyright (c) 2018 IBM Corporation and others.
//  * All rights reserved. This program and the accompanying materials
//  * are made available under the terms of the Eclipse Public License v1.0
//  * which accompanies this distribution, and is available at
//  * http://www.eclipse.org/legal/epl-v10.html
//  *
//  * Contributors:
//  *     IBM Corporation - Initial implementation
//  *******************************************************************************/
// // end::copyright[]
// package it.io.openliberty.guides.music;

// import static org.junit.Assert.assertEquals;

// import java.util.HashMap;
// import javax.json.JsonObject;
// import javax.ws.rs.client.ClientBuilder;
// import javax.ws.rs.core.Form;
// import javax.ws.rs.core.Response.Status;

// import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
// import org.junit.After;
// import org.junit.Before;
// import org.junit.BeforeClass;
// import org.junit.Test;
// import io.openliberty.guides.music.models.Music;

// public class MusicEntityTest extends MusicTest {

//     private static final String JSONFIELD_ARTIST = "artist";
//     private static final String JSONFIELD_NAME = "name";
//     private static final String JSONFIELD_LIKES = "likes";
//     private static final String JSONFIELD_PRICE = "price";
//     private static final String JSONFIELD_ID = "id";
//     private static final String MUSIC_PRICE = "2";
//     private static final String MUSIC_ARTIST = "tayswift";
//     private static final String MUSIC_LIKES = "500";
//     private static final String MUSIC_NAME = "teardrops";
//     private static final String UPDATE_MUSIC_PRICE = "808";
//     private static final String UPDATE_MUSIC_ARTIST = "arigrande";
//     private static final String UPDATE_MUSIC_NAME = "bang bang";
//     private static final String UPDATE_MUSIC_LIKES = "500";
    
//     private static final int NO_CONTENT_CODE = Status.NO_CONTENT.getStatusCode();
//     private static final int NOT_FOUND_CODE = Status.NOT_FOUND.getStatusCode();

//     @BeforeClass
//     public static void oneTimeSetup() {
//         port = System.getProperty("command.service.http.port");
//         baseUrl = "http://localhost:" + port + "/";
//     }

//     @Before
//     public void setup() {
//         form = new Form();
//         client = ClientBuilder.newClient();
//         client.register(JsrJsonpProvider.class);

//         musicForm = new HashMap<String, String>();

//         musicForm.put(JSONFIELD_NAME, MUSIC_NAME);
//         musicForm.put(JSONFIELD_ARTIST, MUSIC_ARTIST);
//         musicForm.put(JSONFIELD_PRICE, MUSIC_PRICE);
//         musicForm.put(JSONFIELD_LIKES, MUSIC_LIKES);
//     }

//     @Test
//     public void testInvalidRead() {
//         assertEquals("Reading an music that does not exist should return an empty list",
//             true, getIndividualMusic(-1).isEmpty());
//     }

//     @Test
//     public void testInvalidDelete() {
//         int deleteResponse = deleteRequest(-1);
//         assertEquals("Trying to delete an music that does not exist should return the "
//             + "HTTP response code " + NOT_FOUND_CODE, NOT_FOUND_CODE, deleteResponse);
//     }

//     @Test
//     public void testInvalidUpdate() {
//         int updateResponse = updateRequest(musicForm, -1);
//         assertEquals("Trying to update an music that does not exist should return the "
//             + "HTTP response code " + NOT_FOUND_CODE, NOT_FOUND_CODE, updateResponse);
//     }

//     /* @Test
//     public void testReadIndividualEvent() {
//         int postResponse = postRequest(musicForm);
//         assertEquals("Creating an music should return the HTTP reponse code " +  
//             NO_CONTENT_CODE, NO_CONTENT_CODE, postResponse);

//         Event e = new Event(MUSIC_NAME, MUSIC_LOCATION, MUSIC_TIME);
//         JsonObject music = findEvent(e);
//         music = getIndividualEvent(music.getInt("id"));
//         assertData(music, MUSIC_NAME, MUSIC_LOCATION, MUSIC_TIME);

//         int deleteResponse = deleteRequest(music.getInt("id"));
//         assertEquals("Deleting an music should return the HTTP response code " + 
//             NO_CONTENT_CODE, NO_CONTENT_CODE, deleteResponse);
//     }

//     @Test
//     public void testCRUD() {
//         int musicCount = getRequest().size();
//         int postResponse = postRequest(musicForm);
//         assertEquals("Creating an music should return the HTTP reponse code " + 
//             NO_CONTENT_CODE, NO_CONTENT_CODE, postResponse);
     
//         Event e = new Event(MUSIC_NAME, MUSIC_LOCATION, MUSIC_TIME);
//         JsonObject music = findEvent(e);
//         assertData(music, MUSIC_NAME, MUSIC_LOCATION, MUSIC_TIME);

//         musicForm.put(JSONFIELD_NAME, UPDATE_MUSIC_NAME);
//         musicForm.put(JSONFIELD_LOCATION, UPDATE_MUSIC_LOCATION);
//         musicForm.put(JSONFIELD_TIME, UPDATE_MUSIC_TIME);
//         int updateResponse = updateRequest(musicForm, music.getInt("id"));
//         assertEquals("Updating an music should return the HTTP response code " + 
//             NO_CONTENT_CODE, NO_CONTENT_CODE, updateResponse);
        
//         e = new Event(UPDATE_MUSIC_NAME, UPDATE_MUSIC_LOCATION, UPDATE_MUSIC_TIME);
//         music = findEvent(e);
//         assertData(music, UPDATE_MUSIC_NAME, UPDATE_MUSIC_LOCATION, UPDATE_MUSIC_TIME);

//         int deleteResponse = deleteRequest(music.getInt("id"));
//         assertEquals("Deleting an music should return the HTTP response code " + 
//             NO_CONTENT_CODE, NO_CONTENT_CODE, deleteResponse);
//         assertEquals("Total number of musics stored should be the same after testing "
//             + "CRUD operations.", musicCount, getRequest().size());
//     } 

//     @After
//     public void teardown() {
//         response.close();
//         client.close();
//     }

// }
