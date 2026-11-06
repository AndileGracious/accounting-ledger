package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

// 5 fields: date| time| description| vendor| amount

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    // getters are the safe read of private fields

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }
    // created toString to show transactions as readable text in columns
    public String toString(){
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }
    // constructors creates Transaction with 5 fields: date, time, description, vendor, amount
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;


    }
}
