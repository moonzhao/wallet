package com.leovegas.wallet.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WalletUtils {

    private WalletUtils(){}

    private static final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";

    public static LocalDateTime formatDateTime(LocalDateTime dateTime) {
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
            String formatDateTime = dateTime.format(formatter);
            return LocalDateTime.parse(formatDateTime, DateTimeFormatter.ofPattern(DATE_FORMATTER));
        }
        return null;
    }
}
