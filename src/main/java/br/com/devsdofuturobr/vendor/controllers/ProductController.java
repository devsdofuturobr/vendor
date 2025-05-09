package br.com.devsdofuturobr.vendor.controllers;

import br.com.devsdofuturobr.vendor.dto.request.ProductCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.ProductFilter;
import br.com.devsdofuturobr.vendor.dto.request.ProductUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.ProductFullResponse;
import br.com.devsdofuturobr.vendor.dto.response.ProductResponse;
import br.com.devsdofuturobr.vendor.entities.Product;
import br.com.devsdofuturobr.vendor.services.ProductService;
import br.com.devsdofuturobr.vendor.util.ProductParse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/vendor/product")
public class ProductController {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponse create(@Valid @RequestBody ProductCreateRequest request) {
        Product product = service.create(request);
        return ProductParse.toProductResponseDTO(product);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductFullResponse findById(@PathVariable(value = "id") @NotNull Long id) {
        Product product = service.findById(id);
        return ProductParse.toProductFullResponse(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<?> findAll(ProductFilter filter, Pageable pageable) {

        filterIssuesInParameters(pageable.getPageNumber(), pageable.getPageSize());

        if (Objects.nonNull(filter.vendorId())) {
            return service.findByVendorId(filter.vendorId(), pageable);
        }
        return ProductParse.toPageFullResponse(service.findAll(pageable));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable(value = "id") @NotNull Long id) {
        service.delete(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    ProductResponse update(@Valid @RequestBody ProductUpdateRequest request) {
        return ProductParse.toProductResponseDTO(service.update(request));
    }

    private void filterIssuesInParameters(Integer page, Integer size) {
        if (page < 0) {
            throw new IllegalArgumentException("The page parameter cannot be less than zero.");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("The size and page parameter cannot be less than or equal to zero.");
        }
    }

}
