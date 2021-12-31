package com.ezshipp.api.model;

import lombok.Data;

@Data
public class LapseTime {
    long diffSeconds;
    long diffMinutes;
    long diffHours;
    long diffDays;
}
