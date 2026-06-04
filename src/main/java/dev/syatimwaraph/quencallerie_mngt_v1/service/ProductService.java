package dev.syatimwaraph.quencallerie_mngt_v1.service;

import dev.syatimwaraph.quencallerie_mngt_v1.model.Product;

public interface ProductService {
    Product saveProduct(Product product);
    void increaseStock(Product product, double quantity);
    void decreaseStock(Product product, double quantity);
}
