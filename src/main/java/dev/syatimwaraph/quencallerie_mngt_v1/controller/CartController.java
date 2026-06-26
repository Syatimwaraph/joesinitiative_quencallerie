package dev.syatimwaraph.quencallerie_mngt_v1.controller;

import dev.syatimwaraph.quencallerie_mngt_v1.dto.CartItem;
import dev.syatimwaraph.quencallerie_mngt_v1.enums.SaleStatus;
import dev.syatimwaraph.quencallerie_mngt_v1.model.Product;
import dev.syatimwaraph.quencallerie_mngt_v1.model.Sale;
import dev.syatimwaraph.quencallerie_mngt_v1.model.SaleItem;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.ProductRepository;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.SaleItemRepository;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.SaleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductRepository productRepository;

    public CartController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleItemRepository saleItemRepository;

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id,
                            HttpSession session) {

        Product product = productRepository.findById(id).orElseThrow();

        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        boolean found = false;

        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(id)) {
                item.increaseQuantity();
                found = true;
                break;
            }
        }

        if (!found) {
            cart.add(new CartItem(product, 1));
        }

        session.setAttribute("cart", cart);

        return "redirect:/pos";
    }

    // INCREASE QUANTITY OF PRODUCT FROM CART
    @GetMapping("/increase/{id}")
    public String increaseQuantity(@PathVariable Long id,
                                   HttpSession session) {

        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {

            for (CartItem item : cart) {

                if (item.getProductId().equals(id)) {

                    item.increaseQuantity();
                    break;
                }
            }
        }

        session.setAttribute("cart", cart);

        return "redirect:/pos";
    }

    // DECREASE QUANTITY OF PRODUCT FROM CART
    @GetMapping("/decrease/{id}")
    public String decreaseQuantity(@PathVariable Long id,
                                   HttpSession session) {

        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {

            Iterator<CartItem> iterator = cart.iterator();

            while (iterator.hasNext()) {

                CartItem item = iterator.next();

                if (item.getProductId().equals(id)) {

                    if (item.getQuantity() > 1) {

                        item.decreaseQuantity();

                    } else {

                        iterator.remove();
                    }

                    break;
                }
            }
        }

        session.setAttribute("cart", cart);

        return "redirect:/pos";
    }

    // REMOVE ITEM FROM CART
    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id,
                                 HttpSession session) {

        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {

            cart.removeIf(item ->
                    item.getProductId().equals(id));
        }

        session.setAttribute("cart", cart);

        return "redirect:/pos";
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/pos";
        }
        Double discount = (Double) session.getAttribute("discount");
        if (discount == null) {
            discount = 0.0;
        }
        double totalBeforeDiscount = cart.stream() .mapToDouble(CartItem::getTotalPrice) .sum();
        if (discount > totalBeforeDiscount) {
            discount = totalBeforeDiscount;
        }
        double totalAfterDiscount = totalBeforeDiscount - discount;
        Sale sale = new Sale();
        sale.setReceiptNumber(
                "RCPT-" +
                        LocalDate.now().toString().replace("-", "")
                        +
                        "-" + UUID.randomUUID() .toString() .substring(0,5) .toUpperCase() );
        sale.setSaleDate(LocalDateTime.now());
        sale.setTotalBeforeDiscount(totalBeforeDiscount);
        sale.setDiscountAmount(discount);
        sale.setTotalAfterDiscount(totalAfterDiscount);
        sale.setStatus(SaleStatus.PENDING);

        List<SaleItem> items = new ArrayList<>();

        for (CartItem cartItem : cart) {
            Product product = cartItem.getProduct();
        // Reduce stock
            product.setStock( product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            SaleItem saleItem = new SaleItem();
            saleItem.setSale(sale);
            saleItem.setProduct(product);
            saleItem.setQuantity(cartItem.getQuantity());
            saleItem.setUnitPrice(product.getSellingPrice());
            saleItem.setTotalPrice(cartItem.getTotalPrice());
            items.add(saleItem);
        }

        sale.setItems(items);
        Sale savedSales = saleRepository.save(sale);

        // Save receipt number for printing
            session.setAttribute("lastSaleId", sale.getId());

        // Clear cart
        session.removeAttribute("cart");
        session.removeAttribute("discount");
        session.removeAttribute("totalBeforeDiscount");
        session.removeAttribute("totalAfterDiscount");
        session.removeAttribute("finalTotal");

        model.addAttribute("saleId", sale.getId());

        return "redirect:/cart/print_receipt/" + savedSales.getId();
    }

    @GetMapping("/clear")
    public String clearCart(HttpSession session) {

        session.removeAttribute("cart");
        session.removeAttribute("discount");
        session.removeAttribute("totalBeforeDiscount");
        session.removeAttribute("totalAfterDiscount");
        session.removeAttribute("finalTotal");

        return "redirect:/pos";
    }

    @GetMapping("/print_receipt/{saleId}")
    public String printReceipt(@PathVariable Long saleId,
                               Model model) {

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        model.addAttribute("sale", sale);

        return "thermal_receipt";
    }

    // CHECKOUT
//@GetMapping("/checkout")
//public String checkout(HttpSession session) {
//
//    List<CartItem> cart =
//            (List<CartItem>) session.getAttribute("cart");
//
//    if (cart == null || cart.isEmpty()) {
//        return "redirect:/pos";
//    }
//
//    Double discount =
//            (Double) session.getAttribute("discount");
//
//    if (discount == null) {
//        discount = 0.0;
//    }
//
//    double totalBeforeDiscount = cart.stream()
//            .mapToDouble(CartItem::getTotalPrice)
//            .sum();
//
//    if (discount > totalBeforeDiscount) {
//        discount = totalBeforeDiscount;
//    }
//
//    double totalAfterDiscount =
//            totalBeforeDiscount - discount;
//
//    Sale sale = new Sale();
//    sale.setReceiptNumber(
//            "RCPT-" +
//                    LocalDate.now().toString().replace("-", "") +
//                    "-" +
//                    UUID.randomUUID()
//                            .toString()
//                            .substring(0,5)
//                            .toUpperCase()
//    );
//    sale.setSaleDate(LocalDateTime.now());
//    sale.setTotalBeforeDiscount(totalBeforeDiscount);
//    sale.setDiscountAmount(discount);
//    sale.setTotalAfterDiscount(totalAfterDiscount);
//    sale.setStatus(SaleStatus.PENDING);
//
//    List<SaleItem> items = new ArrayList<>();
//
//    for (CartItem cartItem : cart) {
//
//        Product product = cartItem.getProduct();
//
//        // Reduce stock
//        product.setStock(
//                product.getStock() - cartItem.getQuantity());
//        productRepository.save(product);
//
//        SaleItem saleItem = new SaleItem();
//        saleItem.setSale(sale);
//        saleItem.setProduct(product);
//        saleItem.setQuantity(cartItem.getQuantity());
//        saleItem.setUnitPrice(product.getSellingPrice());
//        saleItem.setTotalPrice(cartItem.getTotalPrice());
//
//        items.add(saleItem);
//    }
//
//    sale.setItems(items);
//
//    Sale savedSales = saleRepository.save(sale);
//
//    // Save receipt number for printing
//    session.setAttribute("lastSaleId", sale.getId());
//
//    // Clear cart
//    session.removeAttribute("cart");
//    session.removeAttribute("discount");
//
//    return "redirect:/cart/receipt/" + savedSales.getId();
//}

//    @GetMapping("/cart/checkout")
//    public String checkout(HttpSession session) {
//
//        List<CartItem> cart =
//                (List<CartItem>) session.getAttribute("cart");
//
//        if (cart == null || cart.isEmpty()) {
//            return "redirect:/pos";
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
//        Sale sale = new Sale();
//        sale.setSaleDate(LocalDateTime.now());
//        sale.setReceiptNumber(
//                "RCPT-" +
//                        LocalDate.now().toString().replace("-", "") +
//                        "-" +
//                        UUID.randomUUID()
//                                .toString()
//                                .substring(0,5)
//                                .toUpperCase()
//        );
//        sale.setTotalBeforeDiscount(totalBeforeDiscount);
//        sale.setDiscountAmount(discount);
//        sale.setTotalAfterDiscount(totalAfterDiscount);
//        sale.setStatus(SaleStatus.PENDING);
//
//        List<SaleItem> items = new ArrayList<>();
//
//        for (CartItem cartItem : cart) {
//
//            Product product = cartItem.getProduct();
//
//            // Reduce stock
//            product.setStock(
//                    product.getStock() - cartItem.getQuantity());
//            productRepository.save(product);
//
//            SaleItem item = new SaleItem();
//            item.setSale(sale);
//            item.setProduct(product);
//            item.setQuantity(cartItem.getQuantity());
//            item.setUnitPrice(product.getSellingPrice());
//            item.setTotalPrice(cartItem.getTotalPrice());
//
//            items.add(item);
//        }
//
//        sale.setItems(items);
//        saleRepository.save(sale);
//
//        session.removeAttribute("cart");
//        session.removeAttribute("discount");
//
//        return "redirect:/pos";
//    }


//    @GetMapping("/checkout")
//    public String checkout(HttpSession session) {
//
//        List<CartItem> cart =
//                (List<CartItem>) session.getAttribute("cart");
//
//        if (cart == null || cart.isEmpty()) {
//            return "redirect:/pos";
//        }
//
//        // ================= CREATE SALE =================
//
//        Sale sale = new Sale();
//
//        sale.setSaleDate(LocalDateTime.now());
//
//        double grandTotal = cart.stream()
//                .mapToDouble(CartItem::getTotalPrice)
//                .sum();
//
//        sale.setTotalAmount(grandTotal);
//
//        sale.setSaleDate(LocalDateTime.now());
//
//        sale.setReceiptNumber(
//                "RCPT-" +
//                        LocalDate.now().toString().replace("-", "") +
//                        "-" +
//                        UUID.randomUUID()
//                                .toString()
//                                .substring(0,5)
//                                .toUpperCase()
//        );
//
//        sale.setStatus(SaleStatus.PENDING);
//
//        Sale savedSale = saleRepository.save(sale);
//
//        // ================= SAVE SALE ITEMS =================
//
//        for (CartItem item : cart) {
//
//            Product product = item.getProduct();
//
//            // REDUCE STOCK
//            product.setStock(
//                    product.getStock() - item.getQuantity()
//            );
//
//            productRepository.save(product);
//
//            // CREATE SALE ITEM
//            SaleItem saleItem = new SaleItem();
//
//            saleItem.setSale(savedSale);
//
//            saleItem.setProduct(product);
//
//            saleItem.setQuantity(item.getQuantity());
//
//            saleItem.setUnitPrice(item.getUnitPrice());
//
//            saleItem.setTotalPrice(item.getTotalPrice());
//
//            saleItemRepository.save(saleItem);
//        }
//
//        // CLEAR CART
//        session.removeAttribute("cart");
//
//        return "redirect:/cart/receipt/" + savedSale.getId();
//    }


    String receiptNumber = "RCPT-" + LocalDate.now().toString().replace("-", "") + "-" +
            UUID.randomUUID()
                    .toString()
                    .substring(0,5)
                    .toUpperCase();

    // PRINTING A RECEIPT
    @GetMapping("/cart/receipt")
    public String printReceipt(Model model, HttpSession session) {

        Long saleId = (Long) session.getAttribute("lastSaleId");

        if (saleId == null) {
            return "redirect:/pos";
        }

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow();

        model.addAttribute("sale", sale);

        return "receipt";
    }




//    @GetMapping("/receipt/{id}")
//    public String receiptPage(@PathVariable Long id,
//                              Model model) {
//
//        Sale sale = saleRepository.findById(id)
//                .orElseThrow();
//
//        List<SaleItem> items =
//                saleItemRepository.findAll()
//                        .stream()
//                        .filter(i ->
//                                i.getSale().getId().equals(id))
//                        .toList();
//
//        model.addAttribute("sale", sale);
//        model.addAttribute("items", items);
//
//        return "receipt";
//    }
}