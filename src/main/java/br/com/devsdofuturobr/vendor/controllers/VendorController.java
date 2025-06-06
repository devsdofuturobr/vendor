package br.com.devsdofuturobr.vendor.controllers;

import br.com.devsdofuturobr.vendor.dto.request.VendorCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.VendorFilter;
import br.com.devsdofuturobr.vendor.dto.request.VendorUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.VendorFullResponse;
import br.com.devsdofuturobr.vendor.services.VendorService;
import br.com.devsdofuturobr.vendor.util.VendorParse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController {

    private final VendorService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    VendorFullResponse create(@Valid @RequestBody VendorCreateRequest request) {
        return VendorParse.toDTO(service.create(request));
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    VendorFullResponse findById(@PathVariable(value = "id") @NotNull Long id) {
        return VendorParse.toDTO(service.findById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<?> findAll(VendorFilter filter, Pageable pageable) {
        filterIssuesInParameters(pageable.getPageNumber(), pageable.getPageSize());

        if (Objects.nonNull(filter.allFields()) && filter.allFields().equals(false)) {
            return service.findAllShortResponse(pageable);
        }
        return VendorParse.toPage(service.findAll(pageable));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    VendorFullResponse update(@Valid @RequestBody VendorUpdateRequest request) {
        return VendorParse.toDTO(service.update(request));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable(value = "id") @NotNull Long id) {
        service.delete(id);
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
