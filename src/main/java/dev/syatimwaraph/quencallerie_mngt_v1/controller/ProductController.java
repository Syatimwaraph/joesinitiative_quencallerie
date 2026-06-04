package dev.syatimwaraph.quencallerie_mngt_v1.controller;

import dev.syatimwaraph.quencallerie_mngt_v1.dto.ProductDTO;
import dev.syatimwaraph.quencallerie_mngt_v1.enums.ProductCategory;
import dev.syatimwaraph.quencallerie_mngt_v1.model.Product;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ✅ Save product
    @PostMapping("/save")
    public String saveProduct(
            @Valid @ModelAttribute("product") ProductDTO dto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("products", productRepository.findAll());
            model.addAttribute("categories", ProductCategory.values());
            return "pos";
        }

        Product product = new Product();
        product.setProductName(dto.getName());
        product.setProductCategory(dto.getProductCategory());
        product.setPurchasePrice(dto.getPurchasePrice());
        product.setSellingPrice(dto.getSellingPrice());
        product.setStock(dto.getStock());
        product.setUnit(dto.getUnit());

        productRepository.save(product);

        return "redirect:/dashboard";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product updatedProduct) {

        Product product =
                productRepository.findById(updatedProduct.getId())
                        .orElseThrow();

        product.setProductName(updatedProduct.getProductName());
        product.setProductCategory(updatedProduct.getProductCategory());
        product.setPurchasePrice(updatedProduct.getPurchasePrice());
        product.setSellingPrice(updatedProduct.getSellingPrice());
        product.setStock(updatedProduct.getStock());

        productRepository.save(product);

        return "redirect:/dashboard";
    }


     // Send products to POS page
//    @GetMapping
//    public String getProducts(Model model) {
//        model.addAttribute("products", productRepository.findAll());
//        return "pos";
//    }
//
//    @GetMapping
//    public String getProducts(
//            Model model,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "id") String sortBy,
//            @RequestParam(defaultValue = "DESC") String direction
//    ) {
//
//        Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        Page<Product> productPage = productRepository.findAll(pageable);
//
//        model.addAttribute("products", productPage.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", productPage.getTotalPages());
//        model.addAttribute("totalItems", productPage.getTotalElements());
//        model.addAttribute("pageSize", size);
//        model.addAttribute("sortBy", sortBy);
//        model.addAttribute("direction", direction);
//
//        return "pos";
//    }

//    @GetMapping
//    public String getProducts(
//            Model model,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size,
//            @RequestParam(defaultValue = "id") String sortBy,
//            @RequestParam(defaultValue = "DESC") String direction
//    ) {
//
//        Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        Page<Product> productPage = productRepository.findAll(pageable);
//
//        model.addAttribute("products", productPage.getContent());
//
//        // 🔥 IMPORTANT: never null values
//        model.addAttribute("currentPage", productPage.getNumber());
//        model.addAttribute("totalPages", productPage.getTotalPages());
//        model.addAttribute("totalItems", productPage.getTotalElements());
//
//        model.addAttribute("pageSize", size);
//        model.addAttribute("sortBy", sortBy);
//        model.addAttribute("direction", direction);
//
//        return "pos";
//    }

}