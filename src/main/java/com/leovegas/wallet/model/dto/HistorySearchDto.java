package com.leovegas.wallet.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class HistorySearchDto {

    @JsonProperty(required = true)
    @NotBlank(message = "accountId is mandatory")
    private String accountId;

    private LocalDate begin;

    private LocalDate end;
}
