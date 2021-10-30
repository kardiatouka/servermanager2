package com.example.servermanager.service;

import com.example.servermanager.model.Server;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;

public interface ServerService {
    Collection<Server> list(int limit);
    Server get(Long id);
    Server create(Server server);
    Server update(Server server);
    Boolean delete(Long id);
    Server ping(String ipAddress) throws IOException;
}
