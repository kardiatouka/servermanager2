package com.example.servermanager.model;

import com.example.servermanager.enumerations.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    @NotEmpty(message = "ipAddress must not be empty or null")
    private String ipAddress;
    private String name;
    private String type;
    private String memory;
    private String imageUrl;
    private Status status;
}
