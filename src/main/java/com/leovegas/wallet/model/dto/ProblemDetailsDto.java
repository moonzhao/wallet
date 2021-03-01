package com.leovegas.wallet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProblemDetailsDto {

    private String exceptionMessage;
    private String title;
    private int httpStatusCode;
}
