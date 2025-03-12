package br.com.devsdofuturobr.vendor.services;

import br.com.devsdofuturobr.vendor.dto.request.ProductCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.ProductUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.ProductFullResponse;
import br.com.devsdofuturobr.vendor.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponse create(ProductCreateRequest request);

    void delete(Long id);

    ProductResponse update(ProductUpdateRequest request);

    ProductFullResponse findById(Long id);

    Page<ProductFullResponse> findAll(Pageable pageable);

}
