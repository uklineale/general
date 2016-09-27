package com.seniordesigndbgt.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="dailystock")
public class DailyStock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "timestamp")
    private LocalDateTime time;

    @NotNull
    @Column(name = "price")
    private double value;

    public DailyStock() {}

    public DailyStock(String symbol, LocalDateTime time, double value) {
        this.symbol = symbol;
        this.time = time;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String convertTimetoHistoricalDate() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return time.format(format);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
