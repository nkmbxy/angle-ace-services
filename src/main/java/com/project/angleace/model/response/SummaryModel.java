package com.project.angleace.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class SummaryModel {
    private LocalDate date;
    private Double profit;
}
