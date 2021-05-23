package com.krupatek.courier.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class DateUtils {
    public Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public String ddmmyyFormat(Date date){
        return new SimpleDateFormat("dd/MM/yy").format(date);
    }

    public String ddmmyyFormat(LocalDate localDate){
        return new SimpleDateFormat("dd/MM/yy").format(asDate(localDate));
    }

    public String currentFiscalYear(LocalDate localDate){
        // Check fiscal year
        int month = localDate.getMonthValue();
        int year = localDate.getYear() % 100;
        String billYearTag = "";

        if(month > 4){
            billYearTag = year+"-"+(year + 1);
        } else {
            billYearTag = (year - 1)+"-"+year;
        }
        return billYearTag;
    }
}
