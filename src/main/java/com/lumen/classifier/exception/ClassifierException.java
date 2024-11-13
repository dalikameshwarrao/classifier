package com.lumen.classifier.exception;



public class ClassifierException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    private final Long customerNbr;

    public ClassifierException(String message, Long customerNbr) {
        super(message);
        this.customerNbr = customerNbr;
    }

    public Long getCustomerNbr() {
        return customerNbr;
    }
}

