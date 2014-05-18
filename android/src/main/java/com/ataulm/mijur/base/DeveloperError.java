package com.ataulm.mijur.base;

public class DeveloperError extends Error {

    private DeveloperError(String message) {
        super(message);
    }

    private DeveloperError(String message, Throwable e) {
        super(message, e);
    }

    public static DeveloperError because(String reason) {
        return new DeveloperError(reason);
    }

    public static DeveloperError because(String reason, Throwable e) {
        return new DeveloperError(reason, e);
    }

}
