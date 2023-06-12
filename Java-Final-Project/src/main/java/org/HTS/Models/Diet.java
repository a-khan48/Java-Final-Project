package org.HTS.Models;
import java.util.Date;

public class Diet {
    private Date date;
    private String foodItem;
    private double calories;

    public Diet(Date date, String foodItem, double calories){
        this.date = date;
        this.foodItem = foodItem;
        this.calories = calories;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(){
        this.date = date;
    }
    public String getFoodItem(){
        return foodItem;
    }
    public void setFoodItem(){
        this.foodItem = foodItem;
    }
    public double getCalories(){
        return calories;
    }
    public void setCalories(){
        this.calories = calories;
    }
}

