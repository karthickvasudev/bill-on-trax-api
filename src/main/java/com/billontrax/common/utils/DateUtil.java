package com.billontrax.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String formatyyyyMMdd(Date date) {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }
}
