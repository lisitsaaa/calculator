package entity;

import java.util.List;

public class Operation {
    private int id = 1;
    private final List<Double> numbers;
    private final Type type;
    private double result;
    private final User owner;

    public Operation(List<Double> numbers, Type type, User owner) {
        this.numbers = numbers;
        this.type = type;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }
    public User getOwner() {
        return owner;
    }
    public List<Double> getNumbers() {
        return numbers;
    }
    public Type getType() {
        return type;
    }
    public void setResult(double result) {
        this.result = result;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "numbers = " + numbers +
                ", type = '" + type + '\'' +
                ", result = " + result;
    }
    public enum Type {
        SUM, SUB, MUL, DIV
    }
}

