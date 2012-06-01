/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.openstack.quantum.v1_0.features;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.openstack.filters.AuthenticateRequest;
import org.jclouds.openstack.quantum.v1_0.domain.Attachment;
import org.jclouds.openstack.quantum.v1_0.domain.Port;
import org.jclouds.openstack.quantum.v1_0.domain.PortDetails;
import org.jclouds.openstack.quantum.v1_0.domain.Reference;
import org.jclouds.rest.annotations.ExceptionParser;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.SkipEncoding;
import org.jclouds.rest.annotations.WrapWith;
import org.jclouds.rest.functions.ReturnEmptySetOnNotFoundOr404;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Provides asynchronous access to Network operations on the openstack quantum API.
 *
 * @author Adam Lowe
 * @see PortClient
 * @see <a href="http://docs.openstack.org/api/openstack-network/1.0/content/Ports.html">api doc</a>
 */
@SkipEncoding({'/', '='})
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/networks")
public interface PortAsyncClient {

   /**
    * @see PortClient#listReferences
    */
   @GET
   @SelectJson("ports")
   @Path("/{net}/ports")
   @ExceptionParser(ReturnEmptySetOnNotFoundOr404.class)
   ListenableFuture<Set<Reference>> listReferences(@PathParam("net") String networkId);

   /**
    * @see PortClient#list
    */
   @GET
   @SelectJson("ports")
   @Path("/{net}/ports/detail")
   @ExceptionParser(ReturnEmptySetOnNotFoundOr404.class)
   ListenableFuture<Set<Port>> list(@PathParam("net") String networkId);

   /**
    * @see PortClient#show
    */
   @GET
   @SelectJson("port")
   @Path("/{net}/ports/{id}")
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<Port> show(@PathParam("net") String networkId, @PathParam("id") String id);

   /**
    * @see PortClient#showDetails
    */
   @GET
   @SelectJson("port")
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/{net}/ports/{id}/detail")
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)
   ListenableFuture<PortDetails> showDetails(@PathParam("net") String networkId, @PathParam("id") String id);

   /**
    * @see PortClient#create(String) 
    */
   @POST
   @SelectJson("port")
   @Path("/{net}/ports")
   ListenableFuture<Reference> create(@PathParam("net") String networkId);

   /**
    * @see PortClient#create(String, org.jclouds.openstack.quantum.v1_0.domain.Port.State) 
    */
   @POST
   @SelectJson("port")
   @Path("/{net}/ports")
   @WrapWith("port")
   ListenableFuture<Port> create(@PathParam("net") String networkId, @PayloadParam("state") Port.State state);

   /**
    * @see PortClient#update
    */
   @PUT
   @Path("/{net}/ports/{id}")
   @WrapWith("port")
   ListenableFuture<Boolean> update(@PathParam("net") String networkId, @PathParam("id") String id, @PayloadParam("state") Port.State state);

   /**
    * @see PortClient#delete
    */
   @DELETE
   @Path("/{net}/ports/{id}")
   ListenableFuture<Boolean> delete(@PathParam("net") String networkId, @PathParam("id") String id);

   /**
    * @see PortClient#showAttachment
    */
   @GET
   @SelectJson("attachment")
   @Path("/{net}/ports/{portId}/attachment")
   @ExceptionParser(ReturnNullOnNotFoundOr404.class)   
   ListenableFuture<Attachment> showAttachment(@PathParam("net") String networkId, @PathParam("portId") String portId);

   /**
    * @see PortClient#plugAttachment
    */
   @PUT
   @Path("/{net}/ports/{portId}/attachment")
   @WrapWith("attachment")
   ListenableFuture<Boolean> plugAttachment(@PathParam("net") String networkId, @PathParam("portId") String portId,
                                            @PayloadParam("id") String attachmentId);

   /**
    * @see PortClient#unplugAttachment
    */
   @DELETE
   @Path("/{net}/ports/{portId}/attachment")
   ListenableFuture<Boolean> unplugAttachment(@PathParam("net") String networkId, @PathParam("portId") String portId);

}
