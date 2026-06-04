//package dev.syatimwaraph.quencallerie_mngt_v1;
//
//import dev.syatimwaraph.quencallerie_mngt_v1.model.Product;
//import dev.syatimwaraph.quencallerie_mngt_v1.repository.ProductRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class DataSeeder implements CommandLineRunner {
//
//    private final ProductRepository productRepository;
//
//    public DataSeeder(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }
//
//    @Override
//    public void run(String... args) {
//        List<Product> products = ProductTestDataGenerator.generateRandomProducts(50);
//        productRepository.saveAll(products);
//
//        System.out.println("Random products inserted successfully!");
//    }
//}