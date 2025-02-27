package br.com.devsdofuturobr.vendor.services.impl;

import br.com.devsdofuturobr.vendor.dto.request.ProductCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.ProductUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.ProductProjectionResponse;
import br.com.devsdofuturobr.vendor.entities.Product;
import br.com.devsdofuturobr.vendor.exception.ProductNotFoundException;
import br.com.devsdofuturobr.vendor.repositories.ProductRepository;
import br.com.devsdofuturobr.vendor.services.ProductService;
import br.com.devsdofuturobr.vendor.services.VendorService;
import br.com.devsdofuturobr.vendor.util.ProductParse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new ProductNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public Product update(ProductUpdateRequest request) {
        return repository.save(ProductParse.updateByDTO(this.findById(request.id()), request));
    }

    @Override
    public Product findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<ProductProjectionResponse> findByVendorId(Long vendorId, Pageable pageable) {
        return repository.findByVendorId(vendorId, pageable);
    }
}
