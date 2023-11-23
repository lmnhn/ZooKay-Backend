package com.thezookaycompany.zookayproject.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatToSimpleDateFormat {

    public static String formatDateToSimpleDate (Date inputDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(inputDate);
        return formattedDate;
    }
}
