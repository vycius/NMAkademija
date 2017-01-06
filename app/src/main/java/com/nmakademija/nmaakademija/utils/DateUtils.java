package com.nmakademija.nmaakademija.utils;


import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    private static final String DATE_FORMAT_PATTERN_TZ = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String TIME_FORMAT_PATTERN = "HH:mm";

    private static final SimpleDateFormat TIME_FORMAT =
            new SimpleDateFormat(TIME_FORMAT_PATTERN, Locale.US);

    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_PATTERN_TZ,
            Locale.US);

    static {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
        TIME_FORMAT.setTimeZone(TimeZone.getDefault());
    }

    @Nullable
    public static Date parseTZDate(String pattern) {
        try {
            return DATE_FORMAT.parse(pattern);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static String formatTime(@Nullable Date date) {
        if (date == null) {
            return null;
        }

        return TIME_FORMAT.format(date);
    }


}
