package com.ataulm.mijur.data;

public class Time {

    private static final Time INVALID = new Time(Long.MIN_VALUE);

    private final long millisSinceEpoch;

    public Time(long millisSinceEpoch) {
        this.millisSinceEpoch = millisSinceEpoch;
    }

    public static Time now() {
        return new Time(System.currentTimeMillis());
    }

    public static Time invalid() {
        return INVALID;
    }

    public boolean isValid() {
        return !this.equals(INVALID);
    }

    public boolean isBefore(Time otherTime) {
        return millisSinceEpoch < otherTime.millisSinceEpoch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Time)) {
            return false;
        }

        Time time = (Time) o;

        return millisSinceEpoch == time.millisSinceEpoch;
    }

    @Override
    public int hashCode() {
        return (int) (millisSinceEpoch ^ (millisSinceEpoch >>> 32));
    }

}
