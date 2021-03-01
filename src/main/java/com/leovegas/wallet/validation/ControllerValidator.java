package com.leovegas.wallet.validation;

import com.leovegas.wallet.exception.ErrorMessages;
import com.leovegas.wallet.exception.InvalidRequestException;
import com.leovegas.wallet.model.dto.HistorySearchDto;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class ControllerValidator {

    private ControllerValidator(){}

    public static void validateHistorySearchDto(HistorySearchDto historySearchDto) {
        if (StringUtils.isEmpty(historySearchDto.getBegin())) {
            historySearchDto.setBegin("1970-01-01");
        }
        if (StringUtils.isEmpty(historySearchDto.getEnd())) {
            historySearchDto.setEnd(LocalDate.now().toString());
        }
        try {
            LocalDate.parse(historySearchDto.getBegin());
            LocalDate.parse(historySearchDto.getEnd());
        } catch (Exception e) {
            throw new InvalidRequestException(ErrorMessages.INPUT_DATE_FORMAT_ERROR.getErrorMessage());
        }
    }
}
