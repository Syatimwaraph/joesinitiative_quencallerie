package dev.syatimwaraph.quencallerie_mngt_v1.service.impl;

import dev.syatimwaraph.quencallerie_mngt_v1.enums.StockMovementType;
import dev.syatimwaraph.quencallerie_mngt_v1.enums.StockSource;
import dev.syatimwaraph.quencallerie_mngt_v1.model.Product;
import dev.syatimwaraph.quencallerie_mngt_v1.model.StockMouvement;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.StockMovementRepository;
import dev.syatimwaraph.quencallerie_mngt_v1.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StockMovementServiceImpl implements StockMovementService {

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Override
    public void recordMovement(Product product, double quantity, StockMovementType stockMovementType, StockSource stockSource) {
        StockMouvement stockMouvement = new StockMouvement();

        stockMouvement.setProduct(product);
        stockMouvement.setQuantity(quantity);
        stockMouvement.setStockMovementType(stockMovementType);
        stockMouvement.setSource(stockSource);
        stockMouvement.setDate(LocalDateTime.now());

        stockMovementRepository.save(stockMouvement);

    }
}
