package dev.syatimwaraph.quencallerie_mngt_v1.controller;

import dev.syatimwaraph.quencallerie_mngt_v1.dto.CartItem;
import dev.syatimwaraph.quencallerie_mngt_v1.dto.ProductDTO;
import dev.syatimwaraph.quencallerie_mngt_v1.enums.ProductCategory;
import dev.syatimwaraph.quencallerie_mngt_v1.model.Product;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.ProductRepository;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.SaleItemRepository;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.SaleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class POSController {

    private final ProductRepository productRepository;

    public POSController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/pos")
    public String posPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            Model model,
            HttpSession session) {

        Page<Product> productPage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            productPage = productRepository
                    .findByProductNameContainingIgnoreCase(
                            keyword,
                            PageRequest.of(page, size)
                    );
        } else {
            productPage = productRepository
                    .findAll(PageRequest.of(page, size));
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        Double discount = (Double) session.getAttribute("discount");

        if (discount == null) {
            discount = 0.0;
        }

        // Total before discount
        double totalBeforeDiscount = cart.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();

        // Prevent discount larger than total
        if (discount > totalBeforeDiscount) {
            discount = totalBeforeDiscount;
            session.setAttribute("discount", discount);
        }

        // Final total
        double totalAfterDiscount =
                totalBeforeDiscount - discount;

        model.addAttribute("products", productPage);
        model.addAttribute("keyword", keyword);

        model.addAttribute("cart", cart);
        model.addAttribute("totalBeforeDiscount", totalBeforeDiscount);
        model.addAttribute("discount", discount);
        model.addAttribute("totalAfterDiscount", totalAfterDiscount);

        return "pos";
    }

    @PostMapping("/cart/apply-discount")
    public String applyDiscount(
            @RequestParam Double discount,
            HttpSession session) {

        if (discount == null || discount < 0) {
            discount = 0.0;
        }

        session.setAttribute("discount", discount);

        return "redirect:/pos";
    }












//    @GetMapping("/pos")
//    public String posPage(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(required = false) String keyword,
//            Model model,
//            HttpSession session) {
//
//        Page<Product> productPage;
//
//        if (keyword != null && !keyword.trim().isEmpty()) {
//            productPage = productRepository
//                    .findByProductNameContainingIgnoreCase(
//                            keyword,
//                            PageRequest.of(page, size)
//                    );
//        } else {
//            productPage = productRepository
//                    .findAll(PageRequest.of(page, size));
//        }
//
//        List<CartItem> cart =
//                (List<CartItem>) session.getAttribute("cart");
//
//        if (cart == null) {
//            cart = new ArrayList<>();
//        }
//
//        Double discount =
//                (Double) session.getAttribute("discount");
//
//        if (discount == null) {
//            discount = 0.0;
//        }
//
//        double totalBeforeDiscount = cart.stream()
//                .mapToDouble(CartItem::getTotalPrice)
//                .sum();
//
//        double totalAfterDiscount =
//                Math.max(totalBeforeDiscount - discount, 0);
//
//        model.addAttribute("products", productPage); // IMPORTANT
//        model.addAttribute("keyword", keyword);
//
//        model.addAttribute("cart", cart);
//        model.addAttribute("totalBeforeDiscount", totalBeforeDiscount);
//        model.addAttribute("discount", discount);
//        model.addAttribute("totalAfterDiscount", totalAfterDiscount);
//
//        return "pos";
//    }

//    @GetMapping("/pos")
//    public String posPage(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(required = false) String keyword,
//            Model model,
//            HttpSession session) {
//
//        Page<Product> productPage;
//
//        if (keyword != null && !keyword.trim().isEmpty()) {
//            productPage = productRepository.findByProductNameContainingIgnoreCase(
//                    keyword, PageRequest.of(page, size));
//        } else {
//            productPage = productRepository.findAll(PageRequest.of(page, size));
//        }
//
//        List<CartItem> cart =
//                (List<CartItem>) session.getAttribute("cart");
//
//        if (cart == null) {
//            cart = new ArrayList<>();
//        }
//
//        Double discount =
//                (Double) session.getAttribute("discount");
//
//        if (discount == null) {
//            discount = 0.0;
//        }
//
//        double totalBeforeDiscount = cart.stream()
//                .mapToDouble(CartItem::getTotalPrice)
//                .sum();
//
//        double totalAfterDiscount =
//                Math.max(totalBeforeDiscount - discount, 0);
//
//        model.addAttribute("products", productPage.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", productPage.getTotalPages());
//        model.addAttribute("pageSize", size);
//        model.addAttribute("keyword", keyword);
//
//        model.addAttribute("cart", cart);
//        model.addAttribute("totalBeforeDiscount", totalBeforeDiscount);
//        model.addAttribute("discount", discount);
//        model.addAttribute("totalAfterDiscount", totalAfterDiscount);
//
//        return "pos";
//    }

//    @PostMapping("/cart/discount")
//    public String applyDiscount(
//            @RequestParam Double discount,
//            HttpSession session) {
//
//        if (discount == null || discount < 0) {
//            discount = 0.0;
//        }
//
//        session.setAttribute("discount", discount);
//
//        return "redirect:/pos";
//    }


//    @GetMapping("/pos")
//    public String posPage(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(required = false) String keyword,
//            Model model,
//            HttpSession session
//    ) {
//        Page<Product> products;
//
//        if (keyword != null && !keyword.trim().isEmpty()) {
//            products = productRepository
//                    .findByProductNameContainingIgnoreCase(
//                            keyword,
//                            PageRequest.of(page, size, Sort.by("productName"))
//                    );
//        } else {
//            products = productRepository.findAll(
//                    PageRequest.of(page, size, Sort.by("productName"))
//            );
//        }
//
//        List<CartItem> cart =
//                (List<CartItem>) session.getAttribute("cart");
//
//        if (cart == null) {
//            cart = new ArrayList<>();
//        }
//
//        double total = cart.stream()
//                .mapToDouble(CartItem::getTotalPrice)
//                .sum();
//
//        model.addAttribute("products", products);
//        model.addAttribute("cart", cart);
//        model.addAttribute("total", total);
//        model.addAttribute("keyword", keyword);
//
//        // Product modal
//        model.addAttribute("product", new ProductDTO());
//        model.addAttribute("categories", ProductCategory.values());
//
//        return "pos";
//    }
//
//    @GetMapping("/pos")
//    public String posPage(Model model,
//                          HttpSession session) {
//
//        List<Product> products = productRepository.findAll();
//
//        List<CartItem> cart =
//                (List<CartItem>) session.getAttribute("cart");
//
//        if (cart == null) {
//            cart = new ArrayList<>();
//        }
//
//        double total = cart.stream()
//                .mapToDouble(CartItem::getTotalPrice)
//                .sum();
//
//        model.addAttribute("products", products);
//        model.addAttribute("cart", cart);
//        model.addAttribute("total", total);
//
//        // IMPORTANT
//        model.addAttribute("product", new ProductDTO());
//
//        // IMPORTANT
//        model.addAttribute("categories", ProductCategory.values());
//
//        return "pos";
//    }
}
