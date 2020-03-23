package com.example.chilke.financialadvisor.Model;

public class Expense {

    private Integer id;
    private String time;
    private double amount;
    private ExpenseType type;
    private String description;

    public Expense() { }

    public Expense(Integer id, String time, double amount, ExpenseType type, String description) {
        this.id = id;
        this.time = time;
        this.amount = amount;
        this.type = type;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ExpenseType getType() {
        return type;
    }

    public void setType(ExpenseType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return time + " " + amount + " " + type.toString() + " " + description;
    }
}
