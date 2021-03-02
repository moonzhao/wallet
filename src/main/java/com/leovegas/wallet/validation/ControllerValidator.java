package com.leovegas.wallet.validation;

import com.leovegas.wallet.model.dto.HistorySearchDto;

import java.time.LocalDate;

public class ControllerValidator {

    private ControllerValidator(){}

    public static void validateHistorySearchDto(HistorySearchDto historySearchDto) {
        if (historySearchDto.getBegin() == null) {
            historySearchDto.setBegin(LocalDate.of(1970, 1, 1));
        }
        if (historySearchDto.getEnd() == null ) {
            historySearchDto.setEnd(LocalDate.now());
        }
    }
}
