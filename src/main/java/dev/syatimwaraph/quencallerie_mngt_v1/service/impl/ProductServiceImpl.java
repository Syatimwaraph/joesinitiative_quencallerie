package dev.syatimwaraph.quencallerie_mngt_v1.service.impl;

import dev.syatimwaraph.quencallerie_mngt_v1.model.Product;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.ProductRepository;
import dev.syatimwaraph.quencallerie_mngt_v1.service.ProductService;
import dev.syatimwaraph.quencallerie_mngt_v1.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockMovementService stockMovementService;

    @Override
    public Product saveProduct(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    public void increaseStock(Product product, double quantity) {
        product.setStock(product.getStock() + quantity);
        productRepository.save(product);
    }

    @Override
    public void decreaseStock(Product product, double quantity) {
        if (product.getStock() < quantity){
            throw new RuntimeException("Stock insuffisant!");
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);


    }
}
