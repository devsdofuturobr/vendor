package br.com.devsdofuturobr.vendor.controllers;

import br.com.devsdofuturobr.vendor.dto.request.ProductCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.ProductUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.ProductFullResponse;
import br.com.devsdofuturobr.vendor.dto.response.ProductResponse;
import br.com.devsdofuturobr.vendor.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponse create(@Valid @RequestBody ProductCreateRequest request) {
        return service.create(request);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductFullResponse findById(@PathVariable(value = "id") @NotNull Long id) {
        return service.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<ProductFullResponse> findAll(Pageable pageable) {
        filterIssuesInParameters(pageable.getPageNumber(), pageable.getPageSize());
        return service.findAll(pageable);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable(value = "id") @NotNull Long id) {
        service.delete(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    ProductResponse update(@Valid @RequestBody ProductUpdateRequest request) {
        return service.update(request);
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
