package com.services.portal;

public interface IPortal {
    String createInfo(String id, String info);

    String getInfo(String id);

    boolean removeInfo(String id);
}
