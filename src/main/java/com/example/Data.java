package com.example;

import java.io.Serializable;
import java.util.Objects;

abstract class Data implements Serializable {
    String name;
    Integer amount;
    Integer ducats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getDucats() {
        return ducats;
    }

    public void setDucats(Integer ducats) {
        this.ducats = ducats;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;
        return Objects.equals(getName(), data.getName()) && Objects.equals(getAmount(), data.getAmount()) && Objects.equals(getDucats(), data.getDucats());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getName());
        result = 31 * result + Objects.hashCode(getAmount());
        result = 31 * result + Objects.hashCode(getDucats());
        return result;
    }

    @Override
    public String toString() {
        return "Name: "+name+", Amount: "+amount+", Ducats: "+ducats;
    }
}