package com.project.angleace.model.request;

import lombok.Data;

import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class GetProductRequest {
    private String name;
    private Integer page;
    private Integer perPage;
}
