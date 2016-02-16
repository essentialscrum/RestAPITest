package com.services.rest;

import com.services.portal.mock.SingletonePortalImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;
import java.util.Map;

public final class PortalImplTest {
    private SingletonePortalImpl portal;

    private static String getDataForKey(int i) {
        return String.format("Data: %s", i);
    }

    @Before
    public void initTestData() {
        final Map<String, String> data = new Hashtable<String, String>();
        for (int i = 0; i < 10; i++) {
            data.put(String.valueOf(i), getDataForKey(i));
        }
        portal = (SingletonePortalImpl) SingletonePortalImpl.getInstance();
        portal.replaceData(data);
    }

    @Test
    public void getInfo() {
        final String firstId = String.valueOf(0);
        final String firstValue = getDataForKey(0);
        final String lastId = String.valueOf(9);
        final String lastValue = getDataForKey(9);

        Assert.assertEquals(firstValue, portal.getInfo(firstId));
        Assert.assertEquals(lastValue, portal.getInfo(lastId));
    }

    @Test
    public void createInfo() {
        final String id = String.valueOf(10);
        final String value = getDataForKey(10);
        Assert.assertNull(portal.getInfo(id));

        portal.createInfo(id, value);
        Assert.assertEquals(value, portal.getInfo(id));
    }

    @Test
    public void removeInfo() {
        final String id = String.valueOf(9);
        final String value = getDataForKey(9);
        Assert.assertEquals(value, portal.getInfo(id));

        Assert.assertTrue(portal.removeInfo(id));
        Assert.assertNull(portal.getInfo(id));
    }

    @Test
    public void addWhenAlreadyHave() {
        final String id = String.valueOf(9);
        final String value = getDataForKey(9);
        final int size = portal.getCountNotes();
        Assert.assertEquals(value, portal.getInfo(id));

        portal.createInfo(id, value);
        Assert.assertEquals(value, portal.getInfo(id));
        //Haven't added to the end
        Assert.assertEquals(size, portal.getCountNotes());
    }

    @Test
    public void addedTwice() {
        final String id = String.valueOf(10);
        final String value = getDataForKey(10);
        final int size = portal.getCountNotes();
        Assert.assertNull(portal.getInfo(id));

        portal.createInfo(id, value);
        Assert.assertEquals(value, portal.getInfo(id));

        portal.createInfo(id, value);
        Assert.assertEquals(value, portal.getInfo(id));
        //Haven't added to the end
        Assert.assertEquals(size + 1, portal.getCountNotes());
    }

    @Test
    public void gotTwice() {
        final String id = String.valueOf(9);
        final String value = getDataForKey(9);
        final int size = portal.getCountNotes();
        Assert.assertEquals(value, portal.getInfo(id));
        Assert.assertEquals(value, portal.getInfo(id));
        Assert.assertEquals(size, portal.getCountNotes());
    }

    @Test
    public void removeAfterAddition() {
        final String id = String.valueOf(10);
        final String value = getDataForKey(10);
        Assert.assertNull(portal.getInfo(id));

        portal.createInfo(id, value);
        Assert.assertEquals(value, portal.getInfo(id));

        portal.removeInfo(id);
        Assert.assertNull(portal.getInfo(id));
    }

    @Test
    public void removeTwice() {
        final String id = String.valueOf(9);
        final String value = getDataForKey(9);
        final int size = portal.getCountNotes();
        Assert.assertEquals(value, portal.getInfo(id));

        portal.removeInfo(id);
        Assert.assertNull(portal.getInfo(id));

        portal.removeInfo(id);
        Assert.assertNull(portal.getInfo(id));
        Assert.assertEquals(size - 1, portal.getCountNotes());
    }

    @Test
    public void endToEnd() {
        final String id = String.valueOf(0);
        final String value = getDataForKey(0);
        Assert.assertEquals(value, portal.getInfo(id));

        Assert.assertTrue(portal.removeInfo(id));
        Assert.assertNull(portal.getInfo(id));

        portal.createInfo(id, value);
        Assert.assertEquals(value, portal.getInfo(id));
    }
}
