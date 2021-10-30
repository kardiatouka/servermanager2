package com.example.servermanager.repo;

import com.example.servermanager.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepo extends JpaRepository<Server, Long> {
    public Server findByIpAddress(String ipAddress);
}
