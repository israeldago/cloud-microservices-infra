package com.israeldago.cloudbillingservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvoiceNotFoundException extends RuntimeException {
    public static final String MSG = "INVOICE NOT FOUND";

    public InvoiceNotFoundException() {
        super(MSG);
    }
}
