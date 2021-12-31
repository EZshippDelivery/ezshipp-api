package com.ezshipp.api.util;

import java.util.Calendar;

import com.ezshipp.api.model.BaseFilter;

public class BaseFilterHelper {
    public static void setDates(BaseFilter baseFilter)    {
        if (baseFilter.getStartDate() != null) {
            Calendar startDate = Calendar.getInstance();
            startDate.setTime(baseFilter.getStartDate());
            startDate.set(Calendar.HOUR_OF_DAY, 0);
            startDate.set(Calendar.MINUTE, 01);
            startDate.set(Calendar.SECOND, 01);
            baseFilter.setStartDate(startDate.getTime());
        }
        if (baseFilter.getEndDate() != null) {
            Calendar endDate = Calendar.getInstance();
            endDate.setTime(baseFilter.getEndDate());
            endDate.set(Calendar.HOUR_OF_DAY, 23);
            endDate.set(Calendar.MINUTE, 59);
            endDate.set(Calendar.SECOND, 59);
            baseFilter.setEndDate(endDate.getTime());
        }

    }
}
