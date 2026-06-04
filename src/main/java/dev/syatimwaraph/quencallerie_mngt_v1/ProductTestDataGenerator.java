//package dev.syatimwaraph.quencallerie_mngt_v1;
//
//import dev.syatimwaraph.quencallerie_mngt_v1.enums.ProductCategory;
//import dev.syatimwaraph.quencallerie_mngt_v1.model.Product;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class ProductTestDataGenerator {
//
//    private static final Random random = new Random();
//
//    private static final String[] PRODUCT_NAMES = {
//            "Cement", "Sugar", "Rice", "Flour", "Oil", "Soap", "Milk", "Beans", "Salt", "Detergent"
//    };
//
//    private static final String[] UNITS = {
//            "kg", "bag", "liter", "box", "piece"
//    };
//
//    public static List<Product> generateRandomProducts(int count) {
//
//        List<Product> products = new ArrayList<>();
//
//        for (int i = 0; i < count; i++) {
//            Product p = new Product();
//
//            p.setProductName(randomName());
//            p.setProductCategory(randomCategory());
//            p.setPurchasePrice(randomDouble(1, 50));
//            p.setSellingPrice(p.getPurchasePrice() + randomDouble(1, 20));
//            p.setStock(randomDouble(0, 200));
//            p.setUnit(randomUnit());
//            p.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(30)));
//            p.setAlertStock(random.nextInt(10) + 1);
//
//            products.add(p);
//        }
//
//        return products;
//    }
//
//    private static String randomName() {
//        return PRODUCT_NAMES[random.nextInt(PRODUCT_NAMES.length)] + "-" + (random.nextInt(900) + 100);
//    }
//
//    private static ProductCategory randomCategory() {
//        ProductCategory[] values = ProductCategory.values();
//        return values[random.nextInt(values.length)];
//    }
//
//    private static String randomUnit() {
//        return UNITS[random.nextInt(UNITS.length)];
//    }
//
//    private static double randomDouble(double min, double max) {
//        return Math.round((min + (max - min) * random.nextDouble()) * 100.0) / 100.0;
//    }
//}