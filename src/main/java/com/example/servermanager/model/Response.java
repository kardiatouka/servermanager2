package com.example.servermanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL) //that annotation means is that when an object is deserialized whether to include only non-null fields on not
public class Response {
    public LocalDateTime timestamp;
    public int statusCode;
    public HttpStatus status;
    public String message;
    public String reason;
    public String developerMessage;
    public Map<?,?> data;
}
