package com.hitesh.ofcwork;

public class DateList {
    String day,date,month,year;
    float price;

    public DateList(){}

    public DateList(String day, String date, String month, String year, float price) {
        this.day = day;
        this.date = date;
        this.month = month;
        this.year = year;
        this.price = price;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public float getPrice() {
        return price;
    }

}
