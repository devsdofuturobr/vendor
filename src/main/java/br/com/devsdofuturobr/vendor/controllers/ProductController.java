package br.com.devsdofuturobr.vendor.controllers;

import br.com.devsdofuturobr.vendor.dto.request.ProductCreateRequest;
import br.com.devsdofuturobr.vendor.dto.response.ProductCreatedResponse;
import br.com.devsdofuturobr.vendor.services.ProductService;
import br.com.devsdofuturobr.vendor.util.ProductParse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductCreatedResponse create(@Valid @RequestBody ProductCreateRequest request){
        return ProductParse.toCreatedDTO(service.create(request));
    }
}
