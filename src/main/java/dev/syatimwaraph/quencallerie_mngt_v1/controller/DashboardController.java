package dev.syatimwaraph.quencallerie_mngt_v1.controller;

import dev.syatimwaraph.quencallerie_mngt_v1.dto.ProductDTO;
import dev.syatimwaraph.quencallerie_mngt_v1.enums.ProductCategory;
import dev.syatimwaraph.quencallerie_mngt_v1.model.Product;
import dev.syatimwaraph.quencallerie_mngt_v1.model.Sale;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.ProductRepository;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.SaleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;

    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            Model model,
            HttpSession session
    ) {

        Page<Product> products;

        if (keyword != null && !keyword.trim().isEmpty()) {
            products = productRepository
                    .findByProductNameContainingIgnoreCase(
                            keyword,
                            PageRequest.of(page, size, Sort.by("productName"))
                    );
        } else {
            products = productRepository.findAll(
                    PageRequest.of(page, size, Sort.by("productName"))
            );
        }

        // TOTAL PRODUCTS
        long totalProducts =
                productRepository.count();

        // TODAY SALES
        LocalDate today = LocalDate.now();

        LocalDateTime start =
                today.atStartOfDay();

        LocalDateTime end =
                today.atTime(23, 59, 59);

        List<Sale> todaySales =
                saleRepository.findBySaleDateBetween(start, end);

        double totalRevenue =
                todaySales.stream()
                        .mapToDouble(Sale::getTotalAfterDiscount)
                        .sum();

        // LOW STOCK
        List<Product> lowStockProducts =
                productRepository.findByStockLessThanEqual(5);

        // RECENT SALES
        List<Sale> recentSales =
                saleRepository.findTop5ByOrderBySaleDateDesc();

        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("todaySales", todaySales.size());
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("lowStockCount", lowStockProducts.size());
        model.addAttribute("recentSales", recentSales);

        // Products
        model.addAttribute("products", products);

        // Product modal
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categories", ProductCategory.values());

        return "dashboard";
    }
}