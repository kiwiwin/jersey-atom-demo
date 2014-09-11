package org.kiwi.atom.resources;

public class Order {
    private int amount;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public int getAmount() {

        return amount;
    }

    Order() {

    }

    Order(int amount, int quantity) {
        this.amount = amount;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "amount=" + amount +
                ", quantity=" + quantity +
                '}';
    }
}
