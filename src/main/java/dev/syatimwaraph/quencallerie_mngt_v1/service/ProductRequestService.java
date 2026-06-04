package dev.syatimwaraph.quencallerie_mngt_v1.service;

import dev.syatimwaraph.quencallerie_mngt_v1.dto.ProductRequestDTO;
import dev.syatimwaraph.quencallerie_mngt_v1.model.ProductRequest;
import dev.syatimwaraph.quencallerie_mngt_v1.repository.ProductRequestRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ProductRequestService {

    private final ProductRequestRepository repository;

    public ProductRequestService(ProductRequestRepository repository) {
        this.repository = repository;
    }

    public void save(ProductRequestDTO dto, Authentication authentication) {
        ProductRequest request = new ProductRequest();
        request.setProductName(dto.getProductName());
        request.setDescription(dto.getDescription());
        request.setRequestedQuantity(dto.getRequestedQuantity());
        request.setRequestedBy(authentication.getName());

        repository.save(request);
    }
}