package dev.syatimwaraph.quencallerie_mngt_v1.service.impl;

import dev.syatimwaraph.quencallerie_mngt_v1.model.Supply;
import dev.syatimwaraph.quencallerie_mngt_v1.model.SupplyDetail;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.SupplyRepository;
import dev.syatimwaraph.quencallerie_mngt_v1.service.ProductService;
import dev.syatimwaraph.quencallerie_mngt_v1.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SupplyServiceImpl implements SupplyService {
    @Autowired
    private SupplyRepository supplyRepository;

    @Autowired
    private ProductService productService;

    @Override
    public void createSupply(Supply supply) {

        supply.setDate(LocalDateTime.now());
        supplyRepository.save(supply);

        for (SupplyDetail supplyDetail : supply.getSupplyDetailList()){
            productService.increaseStock(
                    supplyDetail.getProduct(),
                    supplyDetail.getQuantity()
            );
        }
    }
}
