package com.example.papajohns.models;

public class Order {
    private int id;
    private String name;
    private int count;
    private int cost;
    private int orderId;
    private int status;
    private int typeChoice;
    public Order() {
    }

    public Order(String name, int count, int cost, int orderId, int status, int typeChoice) {

        this.name = name;
        this.count = count;
        this.cost = cost;
        this.orderId = orderId;
        this.status = status;
        this.typeChoice = typeChoice;
    }
    public Order(int id, String name, int count, int cost, int orderId, int status, int typeChoice) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.cost = cost;
        this.orderId = orderId;
        this.status = status;
        this.typeChoice = typeChoice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTypeChoice() {
        return typeChoice;
    }

    public void setTypeChoice(int typeChoice) {
        this.typeChoice = typeChoice;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", cost=" + cost +
                ", orderId=" + orderId +
                ", status=" + status +
                ", typeChoice=" + typeChoice +
                '}';
    }
}
