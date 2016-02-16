package com.services.rest.mock;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.services.portal.IPortal;
import com.services.portal.mock.SingletonePortalImpl;
import com.services.rest.IInfoService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/")
public final class InfoServiceJson implements IInfoService {
    private static final Gson Gson = new Gson();
    private final IPortal portal;

    public InfoServiceJson() {
        this.portal = SingletonePortalImpl.getInstance();
    }

    public InfoServiceJson(IPortal portal) {
        this.portal = portal;
    }

    public static String getNotFoundMsg(String id) {
        return String.format("Info by id: %s not found", id);
    }

    private static String convertToJson(String key, String value) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("info_id", key);
        jsonObject.addProperty("info", value);
        return jsonObject.toString();
    }

    public static String getBadRequestMsg() {
        return "Inappropriate id or data value";
    }

    @GET
    public Response getInfo(@QueryParam("info_id") String id) {
        try {
            final String info = portal.getInfo(id);
            if (info != null)
                return Response.ok(convertToJson(id, info)).build();
            else
                return Response.status(Response.Status.NOT_FOUND).entity(getNotFoundMsg(id)).build();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(getNotFoundMsg(null)).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removeInfo(@PathParam("id") String id) {
        try {
            portal.removeInfo(id);
            return Response.ok().build();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(getNotFoundMsg(null)).build();
        }
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response createInfo(@FormParam("id") String id, @FormParam("data") String data) {
        try {
            portal.createInfo(id, data);
            return Response.status(201).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(getBadRequestMsg()).build();
        } catch (NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(getBadRequestMsg()).build();
        }
    }
}
