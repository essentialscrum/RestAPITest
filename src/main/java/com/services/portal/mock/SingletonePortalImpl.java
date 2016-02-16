package com.services.portal.mock;

import com.services.portal.IPortal;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.TestOnly;

import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public class SingletonePortalImpl implements IPortal {
    private static volatile IPortal Instance;
    private static final Logger logger = Logger.getLogger(SingletonePortalImpl.class);
    private static final Random Rnd = new Random();
    private final Map<String, String> data = new Hashtable<String, String>();

    private SingletonePortalImpl() {
        logger.info("Create object SingletonePortalImpl");
        initData(data);
    }

    public static IPortal getInstance() {
        if (Instance == null) {
            synchronized (SingletonePortalImpl.class) {
                if (Instance == null) {
                    Instance = new SingletonePortalImpl();
                }
            }
        }
        return Instance;
    }

    public String createInfo(String id, String info) {
        logger.info(String.format("Create info %s with id %s", info, id));
        return data.put(id, info);
    }

    public String getInfo(String id) {
        final String info = data.get(id);
        logger.info(String.format("Get info %s by id %s", info, id));
        return info;
    }

    public boolean removeInfo(String id) {
        logger.info(String.format("Remove info with id %s", id));
        return data.remove(id) != null;
    }

    @TestOnly
    public void replaceData(Map<String, String> replacer) {
        data.clear();
        data.putAll(replacer);
    }

    public int getCountNotes() {
        return data.size();
    }

    protected static void initData(Map<String, String> data) {
        for (int i = 0; i < 100; i++) {
            data.put(String.valueOf(i), generateDataForKey(String.valueOf(i)));
        }
    }

    private static String generateDataForKey(String key) {
        return randomString(Rnd.nextInt(key.hashCode()));
    }

    private static String randomString(final int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (Rnd.nextInt((int) (Character.MAX_VALUE)));
            sb.append(c);
        }
        return sb.toString();
    }
}
