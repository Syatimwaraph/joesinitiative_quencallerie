package dev.syatimwaraph.quencallerie_mngt_v1.dto;

import dev.syatimwaraph.quencallerie_mngt_v1.model.Product;

public class CartItem {

    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // ================= GETTERS =================

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Long getProductId() {
        return product.getId();
    }

    public double getUnitPrice() {
        return product.getSellingPrice();
    }

    public double getTotalPrice() {
        return getUnitPrice() * quantity;
    }

    // ================= SETTERS =================

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // ================= HELPERS =================

    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {

        if (this.quantity > 1) {
            this.quantity--;
        }
    }
}