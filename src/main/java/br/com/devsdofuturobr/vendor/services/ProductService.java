package br.com.devsdofuturobr.vendor.services;

import br.com.devsdofuturobr.vendor.dto.request.ProductCreateRequest;
import br.com.devsdofuturobr.vendor.entities.Product;

public interface ProductService {
    Product create(ProductCreateRequest request);
}
