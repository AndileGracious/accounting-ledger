package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String decription;
    private String vendor;
    private double Amount;

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDecription() {
        return decription;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return Amount;
    }

    public Transaction(LocalDate date, LocalTime time, String decription, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.decription = decription;
        this.vendor = vendor;
        Amount = amount;


    }
}
