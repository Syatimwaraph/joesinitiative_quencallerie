package dev.syatimwaraph.quencallerie_mngt_v1.model;

import dev.syatimwaraph.quencallerie_mngt_v1.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_category")
    private ProductCategory productCategory;

    private double purchasePrice;
    private double sellingPrice;
    private double stock;
    private String unit;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "product")
    private List<TransactionDetail> transactionDetails;

    @OneToMany(mappedBy = "product")
    private List<SupplyDetail> supplyDetailList;

    @Column(nullable = false)
    private Integer alertStock = 5;
}
