package dev.syatimwaraph.quencallerie_mngt_v1.model;

import dev.syatimwaraph.quencallerie_mngt_v1.enums.StockMovementType;
import dev.syatimwaraph.quencallerie_mngt_v1.enums.StockSource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "stock_mouvements")
public class StockMouvement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StockMovementType stockMovementType; // IN, OUT

    private double quantity;
    private LocalDateTime date;

    private StockSource source;  // SALE, PURCHASE, LOSS

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
