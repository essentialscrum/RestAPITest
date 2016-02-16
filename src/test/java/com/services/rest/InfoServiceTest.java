package com.services.rest;

import com.services.portal.IPortal;
import com.services.rest.mock.InfoServiceJson;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class InfoServiceTest {
    @Test
    public void getInfo() {
        final IPortal portal = mock(IPortal.class);
        final IInfoService infoService = new InfoServiceJson(portal);
        when(portal.getInfo("0")).thenReturn("Data 0");

        final Response responseExpected = Response.ok().entity("{\"info_id\":\"0\",\"info\":\"Data 0\"}").build();
        final Response responseActual = infoService.getInfo("0");
        AssertEqualsResponses(responseExpected, responseActual);
    }

    @Test
    public void addInfo() {
        final IPortal portal = mock(IPortal.class);
        final IInfoService infoService = new InfoServiceJson(portal);
        when(portal.createInfo("0", "Data 0")).thenReturn("Data 0");

        final Response responseExpected = Response.status(201).build();
        final Response responseActual = infoService.createInfo("0", "Data 0");
        AssertEqualsResponses(responseExpected, responseActual);
    }

    @Test
    public void removeInfo() {
        final IPortal portal = mock(IPortal.class);
        final IInfoService infoService = new InfoServiceJson(portal);
        when(portal.removeInfo("0")).thenReturn(true);

        final Response responseExpected = Response.status(200).build();
        final Response responseActual = infoService.removeInfo("0");
        AssertEqualsResponses(responseExpected, responseActual);
    }

    @Test
    public void infoNotFound() {
        final IPortal portal = mock(IPortal.class);
        final IInfoService infoService = new InfoServiceJson(portal);
        when(portal.getInfo("0")).thenReturn(null);

        final Response responseExpected = Response.status(Response.Status.NOT_FOUND).entity(InfoServiceJson.getNotFoundMsg("0")).build();
        final Response responseActual = infoService.getInfo("0");
        AssertEqualsResponses(responseExpected, responseActual);
    }

    @Test
    public void getInfoThrowNPE() {
        final IPortal portal = mock(IPortal.class);
        final IInfoService infoService = new InfoServiceJson(portal);
        when(portal.getInfo("0")).thenThrow(new NullPointerException());

        final Response responseExpected = Response.status(Response.Status.NOT_FOUND).entity(InfoServiceJson.getNotFoundMsg(null)).build();
        final Response responseActual = infoService.getInfo("0");
        AssertEqualsResponses(responseExpected, responseActual);
    }


    @Test
    public void removeInfoThrowNPE() {
        final IPortal portal = mock(IPortal.class);
        final IInfoService infoService = new InfoServiceJson(portal);
        when(portal.removeInfo("0")).thenThrow(new NullPointerException());

        final Response responseExpected = Response.status(Response.Status.NOT_FOUND).entity(InfoServiceJson.getNotFoundMsg(null)).build();
        final Response responseActual = infoService.removeInfo("0");
        AssertEqualsResponses(responseExpected, responseActual);
    }

    @Test
    public void createInfoThrowNPE() {
        final IPortal portal = mock(IPortal.class);
        final IInfoService infoService = new InfoServiceJson(portal);
        when(portal.createInfo("0", "Data 0")).thenThrow(new NullPointerException());

        final Response responseExpected = Response.status(Response.Status.BAD_REQUEST).entity(InfoServiceJson.getBadRequestMsg()).build();
        final Response responseActual = infoService.createInfo("0", "Data 0");
        AssertEqualsResponses(responseExpected, responseActual);
    }

    @Test
    public void createInfoThrowIAE() {
        final IPortal portal = mock(IPortal.class);
        final IInfoService infoService = new InfoServiceJson(portal);
        when(portal.createInfo("0", "Data 0")).thenThrow(new IllegalArgumentException());

        final Response responseExpected = Response.status(Response.Status.BAD_REQUEST).entity(InfoServiceJson.getBadRequestMsg()).build();
        final Response responseActual = infoService.createInfo("0", "Data 0");
        AssertEqualsResponses(responseExpected, responseActual);
    }

    private void AssertEqualsResponses(Response firstResponse, Response secondResponse) {
        Assert.assertEquals(firstResponse.getEntity(), secondResponse.getEntity());
        Assert.assertEquals(firstResponse.getMetadata(), secondResponse.getMetadata());
        Assert.assertEquals(firstResponse.getStatus(), secondResponse.getStatus());
    }
}
