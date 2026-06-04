package dev.syatimwaraph.quencallerie_mngt_v1.controller;

import dev.syatimwaraph.quencallerie_mngt_v1.dto.ProductRequestDTO;
import dev.syatimwaraph.quencallerie_mngt_v1.enums.RequestStatus;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.ProductRequestRepository;
import dev.syatimwaraph.quencallerie_mngt_v1.service.ProductRequestService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product-requests")
public class ProductRequestController {

    private final ProductRequestService service;
    private final ProductRequestRepository repository;

    public ProductRequestController(ProductRequestService service,
                                    ProductRequestRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ProductRequestDTO dto,
                       Authentication authentication) {
        service.save(dto, authentication);
        return "redirect:/pos?requestSuccess";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("requests", repository.findAll());
        return "product-requests";
    }

    @GetMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        var request = repository.findById(id).orElseThrow();
        request.setStatus(RequestStatus.APPROVED);
        repository.save(request);
        return "redirect:/product-requests";
    }

    @GetMapping("/reject/{id}")
    public String reject(@PathVariable Long id) {
        var request = repository.findById(id).orElseThrow();
        request.setStatus(RequestStatus.REJECTED);
        repository.save(request);
        return "redirect:/product-requests";
    }
}