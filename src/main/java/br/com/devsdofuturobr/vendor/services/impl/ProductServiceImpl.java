package br.com.devsdofuturobr.vendor.services.impl;

import br.com.devsdofuturobr.vendor.dto.request.ProductCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.ProductUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.ProductFullResponse;
import br.com.devsdofuturobr.vendor.dto.response.ProductResponse;
import br.com.devsdofuturobr.vendor.entities.Product;
import br.com.devsdofuturobr.vendor.entities.Vendor;
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
    private final ProductParse productParse;

    @Override
    public ProductResponse create(ProductCreateRequest request) {
        Vendor vendor = vendorService.findByIdAndReturnEntity(request.vendorId());
        Product toCreate = productParse.createByDTO(vendor).apply(request);
        Product saved = repository.save(toCreate);
        return productParse.toProductResponseDTO.apply(saved);
    }

    @Override
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new ProductNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public ProductResponse update(ProductUpdateRequest request) {
        Product product = findByIdAndReturnEntity(request.id());
        Product toUpdate = productParse.updateByDTO(product).apply(request);
        Product updated = repository.save(toUpdate);
        return productParse.toProductResponseDTO.apply(updated);
    }

    @Override
    public ProductFullResponse findById(Long id) {
        Product product = findByIdAndReturnEntity(id);
        return productParse.toProductFullResponse.apply(product);
    }

    @Override
    public Page<ProductFullResponse> findAll(Pageable pageable) {
        Page<Product> products = repository.findAll(pageable);
        return productParse.toPageFullResponse.apply(products);
    }

    private Product findByIdAndReturnEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }
}
