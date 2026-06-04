package dev.syatimwaraph.quencallerie_mngt_v1.controller;

import dev.syatimwaraph.quencallerie_mngt_v1.enums.SaleStatus;
import dev.syatimwaraph.quencallerie_mngt_v1.model.Sale;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.SaleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class SalesController {
    @Autowired
    private SaleRepository saleRepository;

    private void autoApproveSales() {

        List<Sale> sales = saleRepository.findAll();

        for (Sale sale : sales) {

            if (sale.getStatus() == SaleStatus.PENDING) {

                LocalDate saleDay =
                        sale.getSaleDate().toLocalDate();

                if (saleDay.isBefore(LocalDate.now())) {

                    sale.setStatus(SaleStatus.APPROVED);

                    saleRepository.save(sale);
                }
            }
        }
    }

    @GetMapping("/sales")
    public String salesPage(Model model,  HttpSession session) {

        autoApproveSales();

        List<Sale> sales = saleRepository.findAll()
                .stream()
                .sorted((a, b) ->
                        b.getSaleDate()
                                .compareTo(a.getSaleDate()))
                .toList();

        model.addAttribute("sales", sales);

        return "sales";
    }

    @GetMapping("/recepit")
    public Object String(){
        return "receipt";
    }
}
