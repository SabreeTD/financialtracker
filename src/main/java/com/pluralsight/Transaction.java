package com.pluralsight;
import java.time.LocalDate;
import java.time.LocalTime;
public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String Vendors;
    private double amount;

    public Transaction(LocalDate date, LocalTime time, String description, String vendors, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        Vendors = vendors;
        this.amount = amount;
    }
}
