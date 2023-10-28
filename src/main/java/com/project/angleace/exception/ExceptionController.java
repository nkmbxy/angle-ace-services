package com.project.angleace.exception;

import com.project.angleace.model.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Response<String>> exception(Exception exception) {
        return new Response<String>("500", "Something went wrong").responseError();
    }
}
