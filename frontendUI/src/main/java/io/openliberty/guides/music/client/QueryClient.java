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
package io.openliberty.guides.music.client;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.BadRequestException;

import javax.json.JsonObject;
import javax.json.JsonArray;

@Dependent
@RegisterRestClient(baseUri="http://localhost:6050")
@RegisterProvider(UnknownUrlExceptionMapper.class)
@RegisterProvider(BadRequestExceptionMapper.class)
@Path("/music")
public interface QueryClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getSongs() throws UnknownUrlException;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getSong(@PathParam("id") int songId) throws UnknownUrlException;
    
    @GET
    @Path("/top")
    public JsonArray getTopMusic() throws UnknownUrlException;

}
