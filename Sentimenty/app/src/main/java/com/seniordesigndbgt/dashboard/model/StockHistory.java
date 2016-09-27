package com.seniordesigndbgt.dashboard.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
@Entity
@Table(name="stockhistory")
public class StockHistory {
    @Id
    @NotNull
    @Column(name="date")
    private String date;
    @NotNull
    @Column(name="price")
    private double price;

    public StockHistory(){}

    public StockHistory(String date, double price) {
        this.date = date;
        this.price = price;
    }

    public String getDateStock() {
        return date;
    }

    public void setDateStock(String date) {
        this.date = date;
    }

    public double getClosePrice() {
        return price;
    }

    public void setClosePrice(double price) {
        this.price = price;
    }
}
