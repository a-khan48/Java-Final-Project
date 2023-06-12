package org.HTS.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sleep {
    private Date sleepTime;
    private Date wakeTime;

    public Sleep(Date sleepTime, Date wakeTime) {
        this.sleepTime = sleepTime;
        this.wakeTime = wakeTime;
    }

    public Date getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Date sleepTime) {
        this.sleepTime = sleepTime;
    }

    public Date getWakeTime() {
        return wakeTime;
    }

    public void setWakeTime(Date wakeTime) {
        this.wakeTime = wakeTime;
    }

    public int getTotalSleepHours() {
        long diffInMillis = wakeTime.getTime() - sleepTime.getTime();
        return (int) (diffInMillis / (60 * 60 * 1000)); // Convert milliseconds to hours
    }
}
