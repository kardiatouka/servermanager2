package com.example.servermanager.controller;

import com.example.servermanager.model.Response;
import com.example.servermanager.model.Server;
import com.example.servermanager.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import static com.example.servermanager.enumerations.Status.SERVER_UP;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController {

    private final ServerService serverService;

    @PostMapping("/save")
    ResponseEntity<Response> createServer(@RequestBody @Valid Server server){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(Map.of("Server", serverService.create(server)))
                        .message("Server created!")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @PutMapping("/update")
    ResponseEntity<Response> updateServer(@RequestBody @Valid Server server){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(Map.of("Server", serverService.update(server)))
                        .message("Server updated!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/list")
    ResponseEntity<Response> getServers(){
        return ResponseEntity.ok(
            Response.builder()
                    .timestamp(now())
                    .data(Map.of("Servers", serverService.list(30)))
                    .message("Servers retrieved")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<Response> getServer(@PathVariable("id") Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(Map.of("Server", serverService.get(id)))
                        .message("Server retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    ResponseEntity<Response> getServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(Map.of("Server", server))
                        .message(server.getStatus()==SERVER_UP ? "Ping Success" : "Ping Failed")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Response> deleteServer(@PathVariable("id") Long id){
        Boolean deleted = serverService.delete(id);
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(Map.of("Deleted", deleted))
                        .message(deleted? "Server deleted" : "Server not deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping(path ="/image/{imageName}", produces = IMAGE_PNG_VALUE)
    byte[] getServerImage(@PathVariable("imageName") String imageName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") +"/Downloads/serverImages/"+imageName));
    }
}
