package dev.syatimwaraph.quencallerie_mngt_v1.model;

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
@Table(name = "supplies")
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private String supplier;
    private double total;

    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL )
    private List<SupplyDetail> supplyDetailList;
}
