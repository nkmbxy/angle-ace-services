package com.project.angleace.model.response;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

@Data
public class Response<T> {

    private String status;
    private T data;

    public Response(String status, @Nullable T data) {
        this.status = status;
        this.data = data;
    }

    public ResponseEntity<Response<T>> response() {
        return new ResponseEntity<>(this, HttpStatus.OK);
    }

    public ResponseEntity<Response<T>> responseError() {
        return new ResponseEntity<>(this, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
