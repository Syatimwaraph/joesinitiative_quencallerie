package dev.syatimwaraph.quencallerie_mngt_v1.model;

import dev.syatimwaraph.quencallerie_mngt_v1.enums.SaleStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String receiptNumber;

    private LocalDateTime saleDate;

    private Double totalBeforeDiscount;

    private Double discountAmount;

    private Double totalAfterDiscount;

    @Enumerated(EnumType.STRING)
    private SaleStatus status;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleItem> items = new ArrayList<>();

    // Getters and Setters

    public List<SaleItem> getItems() {
        return items;
    }

    public void setItems(List<SaleItem> items) {
        this.items = items;
    }

    public SaleStatus getStatus() {
        return status;
    }

    public void setStatus(SaleStatus status) {
        this.status = status;
    }

    public Double getTotalAfterDiscount() {
        return totalAfterDiscount;
    }

    public void setTotalAfterDiscount(Double totalAfterDiscount) {
        this.totalAfterDiscount = totalAfterDiscount;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getTotalBeforeDiscount() {
        return totalBeforeDiscount;
    }

    public void setTotalBeforeDiscount(Double totalBeforeDiscount) {
        this.totalBeforeDiscount = totalBeforeDiscount;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}


//public class Sale {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String receiptNumber;
//
//    private LocalDateTime saleDate;
//
//    private double totalAmount;
//
//    @Enumerated(EnumType.STRING)
//    private SaleStatus status;
//
//    // ================= GETTERS & SETTERS =================
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getReceiptNumber() {
//        return receiptNumber;
//    }
//
//    public void setReceiptNumber(String receiptNumber) {
//        this.receiptNumber = receiptNumber;
//    }
//
//    public LocalDateTime getSaleDate() {
//        return saleDate;
//    }
//
//    public void setSaleDate(LocalDateTime saleDate) {
//        this.saleDate = saleDate;
//    }
//
//    public double getTotalAmount() {
//        return totalAmount;
//    }
//
//    public SaleStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(SaleStatus status) {
//        this.status = status;
//    }
//
//    public void setTotalAmount(double totalAmount) {
//        this.totalAmount = totalAmount;
//    }
//
//
//}