package com.sushma.olxadvertise.exception;

public class AdvertiseNotFoundException extends RuntimeException {
    public AdvertiseNotFoundException(int advertiseId) {
        super("Advertise not found with id: " + advertiseId);
    }
}
