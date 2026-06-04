package dev.syatimwaraph.quencallerie_mngt_v1.controller;


import dev.syatimwaraph.quencallerie_mngt_v1.model.Product;
import dev.syatimwaraph.quencallerie_mngt_v1.model.Sale;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.ProductRepository;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.SaleRepository;
import dev.syatimwaraph.quencallerie_mngt_v1.service.impl.InventoryPdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    // DAILY SALES REPORT
    @GetMapping("/daily")
    public String dailyReport(Model model) {

        LocalDate today = LocalDate.now();

        LocalDateTime start =
                today.atStartOfDay();

        LocalDateTime end =
                today.atTime(23, 59, 59);

        List<Sale> sales =
                saleRepository.findBySaleDateBetween(start, end);

        double totalRevenue = sales.stream()
                .mapToDouble(Sale::getTotalAfterDiscount)
                .sum();

        int totalSales = sales.size();

        model.addAttribute("sales", sales);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalSales", totalSales);

        return "daily-report";
    }

    // LOW STOCK PRODUCTS
    @GetMapping("/inventory")
    public String inventory(Model model) {

        List<Product> lowStockProducts =
                productRepository.findByStockLessThanEqual(5);

        model.addAttribute("products", lowStockProducts);

        return "inventory-report";
    }

    // PRINT INVENTORY TO PDF
    @GetMapping("/inventory/pdf")
    public void exportInventoryPdf(
            HttpServletResponse response
    ) throws IOException {

        response.setContentType("application/pdf");

        String headerKey =
                "Content-Disposition";

        String headerValue =
                "attachment; filename=inventory_report.pdf";

        response.setHeader(headerKey, headerValue);

        List<Product> products =
                productRepository.findByStockLessThanEqual(5);

        InventoryPdfService pdfService =
                new InventoryPdfService(products);

        pdfService.export(response);
    }
}