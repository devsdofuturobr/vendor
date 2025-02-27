package br.com.devsdofuturobr.vendor.controllers;

import br.com.devsdofuturobr.vendor.dto.request.VendorCreateRequest;
import br.com.devsdofuturobr.vendor.dto.response.VendorFullResponse;
import br.com.devsdofuturobr.vendor.services.VendorService;
import br.com.devsdofuturobr.vendor.util.VendorParse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController {

    private final VendorService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    VendorFullResponse create(@RequestBody VendorCreateRequest request){
        return VendorParse.toDTO(service.create(request));
    }
}
