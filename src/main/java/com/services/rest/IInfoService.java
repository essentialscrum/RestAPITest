package com.services.rest;

import javax.ws.rs.core.Response;

public interface IInfoService {
    Response getInfo(String id);

    Response removeInfo(String id);

    Response createInfo(String id, String data);
}
