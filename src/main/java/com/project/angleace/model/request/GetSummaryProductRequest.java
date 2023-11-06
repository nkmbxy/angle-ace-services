package com.project.angleace.model.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class GetSummaryProductRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}
