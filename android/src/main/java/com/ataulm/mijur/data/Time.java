package com.ataulm.mijur.data;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

public class Time {

    private static final Time INVALID = new Time(Long.MIN_VALUE);
    private static final DateFormat FORMATTER = new TimeElapsedFormatter();

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

    @Override
    public String toString() {
        Date date = new Date(millisSinceEpoch);
        return FORMATTER.format(date);
    }

    private static class TimeElapsedFormatter extends DateFormat {

        private static final long ONE_SECOND = 1000;
        private static final long ONE_MINUTE = ONE_SECOND * 60;
        private static final long ONE_HOUR = ONE_MINUTE * 60;
        private static final long ONE_DAY = ONE_HOUR * 24;

        @Override
        public StringBuffer format(Date date, StringBuffer buffer, FieldPosition field) {
            long deltaMillis = Time.now().millisSinceEpoch - date.getTime();
            deltaMillis = deltaMillis > 0 ? deltaMillis : 0;

            int days = (int) (deltaMillis / ONE_DAY);
            int hours = (int) (deltaMillis % ONE_DAY / ONE_HOUR);
            int mins = (int) (deltaMillis % ONE_HOUR / ONE_MINUTE);
            int seconds = (int) (deltaMillis % ONE_MINUTE / ONE_SECOND);

            if (days > 0) {
                buffer.append(days + "d ");
            }

            if (hours > 0) {
                buffer.append(hours + "h ");
            }

            if (mins > 0) {
                buffer.append(mins + "m ");
            }

            if (buffer.length() == 0) {
                buffer.append(seconds + "s ");
            }

            buffer.append("ago");
            return buffer;
        }

        @Override
        public Date parse(String string, ParsePosition position) {
            throw new NoSuchMethodError("not needed yet");
        }

    }

}
