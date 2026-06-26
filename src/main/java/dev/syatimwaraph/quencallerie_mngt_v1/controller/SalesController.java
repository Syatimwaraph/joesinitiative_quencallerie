package dev.syatimwaraph.quencallerie_mngt_v1.controller;

import dev.syatimwaraph.quencallerie_mngt_v1.enums.SaleStatus;
import dev.syatimwaraph.quencallerie_mngt_v1.model.Sale;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/sales")
public class SalesController {

    private final SaleRepository saleRepository;

    public SalesController(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @GetMapping
    public String sales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String keyword,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("saleDate").descending());

        Page<Sale> sales;

        if (keyword != null && !keyword.isBlank()) {
            sales = saleRepository.findByReceiptNumberContainingIgnoreCase(keyword, pageable);
        } else {
            sales = saleRepository.findAll(pageable);
        }

        model.addAttribute("sales", sales);
        model.addAttribute("keyword", keyword);

        return "sales";
    }
}
//@Controller
//public class SalesController {
//    @Autowired
//    private SaleRepository saleRepository;
//
//    private void autoApproveSales() {
//
//        List<Sale> sales = saleRepository.findAll();
//
//        for (Sale sale : sales) {
//
//            if (sale.getStatus() == SaleStatus.PENDING) {
//
//                LocalDate saleDay =
//                        sale.getSaleDate().toLocalDate();
//
//                if (saleDay.isBefore(LocalDate.now())) {
//
//                    sale.setStatus(SaleStatus.APPROVED);
//
//                    saleRepository.save(sale);
//                }
//            }
//        }
//    }
//
//    @GetMapping("/sales")
//    public String salesPage(Model model,  HttpSession session) {
//
//        autoApproveSales();
//
//        List<Sale> sales = saleRepository.findAll()
//                .stream()
//                .sorted((a, b) ->
//                        b.getSaleDate()
//                                .compareTo(a.getSaleDate()))
//                .toList();
//
//        model.addAttribute("sales", sales);
//
//        return "sales";
//    }
//
//    @GetMapping("/recepit")
//    public Object String(){
//        return "receipt";
//    }
//}
