package com.ataulm.mijur.base;

public class DeveloperError extends Error {

    private DeveloperError(String message) {
        super(message);
    }

    public static DeveloperError because(String reason) {
        return new DeveloperError(reason);
    }

}
