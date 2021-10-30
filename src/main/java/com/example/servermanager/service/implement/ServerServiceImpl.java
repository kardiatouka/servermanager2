package com.example.servermanager.service.implement;

import com.example.servermanager.model.Server;
import com.example.servermanager.repo.ServerRepo;
import com.example.servermanager.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static com.example.servermanager.enumerations.Status.SERVER_DOWN;
import static com.example.servermanager.enumerations.Status.SERVER_UP;
import static java.lang.Boolean.TRUE;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {

    private final ServerRepo serverRepo;

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by Id: {}", id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Server create(Server server) {
        log.info("Saving new server : {}", server.getName());
        server.setImageUrl(setServerImage());
        return serverRepo.save(server);
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server : {}", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by Id: {}", id);
        serverRepo.deleteById(id);
        return TRUE;
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Ping server with ipAddress: {}", ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000)? SERVER_UP : SERVER_DOWN);
        return serverRepo.save(server);
    }


    private String setServerImage() {
        String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
        String imageName = imageNames[new Random().nextInt(4)];
        //calling rest api '/server/image/{imageName}'
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/"+imageName).toUriString();
    }
}
