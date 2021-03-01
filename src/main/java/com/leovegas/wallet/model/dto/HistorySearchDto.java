package com.leovegas.wallet.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class HistorySearchDto {

    @NotBlank(message = "accountId is mandatory")
    private String accountId;
    private String begin;
    private String end;
}
