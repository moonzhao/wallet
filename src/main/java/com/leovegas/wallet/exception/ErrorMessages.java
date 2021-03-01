package com.leovegas.wallet.exception;

public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
    INPUT_DATE_FORMAT_ERROR("The format of input date is invalid"),
    TRANSACTION_ID_ALREADY_EXISTS("Transaction Id already exists"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_ACCOUNT_FOUND("Account with provided id is not found"),
    NOT_ENOUGH_MONEY("There are not enough money in account for debit"),
    COULD_NOT_DO_DEBIT("Could not do transaction of debit"),
    COULD_NOT_DO_CREDIT("Could not do transaction of credit");
    

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
