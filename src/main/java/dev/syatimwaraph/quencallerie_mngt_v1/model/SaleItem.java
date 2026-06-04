package dev.syatimwaraph.quencallerie_mngt_v1.model;

import jakarta.persistence.*;

@Entity
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // MANY ITEMS BELONG TO ONE SALE
    @ManyToOne
    private Sale sale;

    // SOLD PRODUCT
    @ManyToOne
    private Product product;

    private int quantity;

    private double unitPrice;

    private double totalPrice;

    // ================= GETTERS & SETTERS =================

    public Long getId() {
        return id;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}