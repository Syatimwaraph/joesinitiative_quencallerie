package dev.syatimwaraph.quencallerie_mngt_v1.service;

import dev.syatimwaraph.quencallerie_mngt_v1.enums.StockMovementType;
import dev.syatimwaraph.quencallerie_mngt_v1.enums.StockSource;
import dev.syatimwaraph.quencallerie_mngt_v1.model.Product;

public interface StockMovementService {
    void recordMovement(Product product, double quantity, StockMovementType stockMovementType, StockSource stockSource);
}
