package dev.syatimwaraph.quencallerie_mngt_v1.model;

import dev.syatimwaraph.quencallerie_mngt_v1.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "product_requests")
public class ProductRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private String description;

    private Integer requestedQuantity;

    private LocalDateTime requestDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

    // salesperson who created the request
    private String requestedBy;
}