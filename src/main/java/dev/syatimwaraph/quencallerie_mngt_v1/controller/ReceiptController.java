package dev.syatimwaraph.quencallerie_mngt_v1.controller;

import dev.syatimwaraph.quencallerie_mngt_v1.model.Sale;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.SaleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/receipt")
public class ReceiptController {

    private final SaleRepository saleRepository;

    public ReceiptController(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @GetMapping("/{id}")
    public String printReceipt(@PathVariable Long id, Model model) {

        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        model.addAttribute("sale", sale);

        return "receipt";
    }
}