package br.com.devsdofuturobr.vendor.controllers;

import br.com.devsdofuturobr.vendor.dto.request.ProductCreateRequest;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponse create(@Valid @RequestBody ProductCreateRequest request) {
        return ProductParse.toProductResponseDTO(service.create(request));
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductFullResponse findById(@PathVariable(value = "id") @NotNull Long id) {
        return ProductParse.toProductFullResponse(service.findById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "5") Integer size,
                                      @RequestParam(value = "sort", defaultValue = "name") String sort,
                                      @RequestParam(value = "direction", defaultValue = "desc") String direction,
                                      @RequestParam(value = "vendorId", required = false) Long vendorId) {
        filterIssuesInParameters(page, size);

        if (!isValidSortField(sort)) {
            sort = "name";
        }

        Sort.Direction setDirection = Sort.Direction.fromOptionalString(direction).orElse(Sort.Direction.DESC);
        Pageable pageable = PageRequest.of(page, size, Sort.by(setDirection, sort));

        if(Objects.nonNull(vendorId)){
         return service.findByVendorId(vendorId, pageable);
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

    private static boolean isValidSortField(String sort) {
        return Arrays.stream(Product.class.getDeclaredFields())
                .map(Field::getName)
                .anyMatch(nameField -> nameField.equalsIgnoreCase(sort));
    }
}
