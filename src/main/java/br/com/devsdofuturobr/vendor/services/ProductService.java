package br.com.devsdofuturobr.vendor.services;

import br.com.devsdofuturobr.vendor.dto.request.ProductCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.ProductUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.ProductProjectionResponse;
import br.com.devsdofuturobr.vendor.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product create(ProductCreateRequest request);

    void delete(Long id);

    Product update(ProductUpdateRequest request);

    Product findById(Long id);

    Page<Product> findAll(Pageable pageable);

    Page<ProductProjectionResponse> findByVendorId(Long vendorId, Pageable pageable);
}
