package br.com.devsdofuturobr.vendor.controllers;

import br.com.devsdofuturobr.vendor.dto.request.VendorCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.VendorUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.VendorFullResponse;
import br.com.devsdofuturobr.vendor.entities.Vendor;
import br.com.devsdofuturobr.vendor.services.VendorService;
import br.com.devsdofuturobr.vendor.util.VendorParse;
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
    Page<?> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                    @RequestParam(value = "size", defaultValue = "5") Integer size,
                    @RequestParam(value = "sort", defaultValue = "name") String sort,
                    @RequestParam(value = "direction", defaultValue = "desc") String direction,
                    @RequestParam(value = "allFields", defaultValue = "true") Boolean allFields) {
        filterIssuesInParameters(page, size);

        if (!isValidSortField(sort)) {
            sort = "name";
        }

        Sort.Direction setDirection = Sort.Direction.fromOptionalString(direction).orElse(Sort.Direction.DESC);
        Pageable pageable = PageRequest.of(page, size, Sort.by(setDirection, sort));
        if (allFields.equals(false)) {
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

    private static boolean isValidSortField(String sort) {
        return Arrays.stream(Vendor.class.getDeclaredFields())
                .map(Field::getName)
                .anyMatch(nameField -> nameField.equalsIgnoreCase(sort));
    }
}
