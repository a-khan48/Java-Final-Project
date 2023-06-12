package org.HTS.Models;
import java.util.Date;

public class Exercise {
    private Date date;
    private String type;
    private double duration;
    private double caloriesBurned;

    public Exercise(Date date, String type, double duration, double caloriesBurned){
        this.date = date;
        this.type = type;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(){
        this.date = date;
    }
    public String getType(){
        return type;
    }
    public void setType(){
        this.type = type;
    }
    public double getDuration(){
        return duration;
    }
    public void setDuration(){
        this.duration = duration;
    }
    public double getCaloriesBurned(){
        return caloriesBurned;
    }
    public void setCaloriesBurned(){
        this.caloriesBurned = caloriesBurned;
    }
}
