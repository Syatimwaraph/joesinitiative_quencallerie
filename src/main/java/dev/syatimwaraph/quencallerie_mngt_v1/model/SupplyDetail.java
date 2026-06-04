package dev.syatimwaraph.quencallerie_mngt_v1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "supply_detail")
public class SupplyDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double quantity;
    private double purchasePrice;

    @ManyToOne
    @JoinColumn(name = "supply_id")
    private Supply supply;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
