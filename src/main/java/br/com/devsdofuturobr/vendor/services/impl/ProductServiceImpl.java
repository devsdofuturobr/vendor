package br.com.devsdofuturobr.vendor.services.impl;

import br.com.devsdofuturobr.vendor.dto.request.ProductCreateRequest;
import br.com.devsdofuturobr.vendor.entities.Product;
import br.com.devsdofuturobr.vendor.repositories.ProductRepository;
import br.com.devsdofuturobr.vendor.services.ProductService;
import br.com.devsdofuturobr.vendor.services.VendorService;
import br.com.devsdofuturobr.vendor.util.ProductParse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final VendorService vendorService;

    @Override
    public Product create(ProductCreateRequest request) {
        return repository.save(ProductParse.createByDTO(vendorService.findById(request.vendorId()), request));
    }
}
