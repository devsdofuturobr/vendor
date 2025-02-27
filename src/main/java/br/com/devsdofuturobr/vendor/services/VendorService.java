package br.com.devsdofuturobr.vendor.services;

import br.com.devsdofuturobr.vendor.dto.request.VendorCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.VendorUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.VendorShortResponse;
import br.com.devsdofuturobr.vendor.entities.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VendorService {


    Vendor create(VendorCreateRequest request);

    Vendor update(VendorUpdateRequest request);

    Vendor findById(Long id);

    void delete(Long id);

    Page<Vendor> findAll(Pageable pageable);

    Page<VendorShortResponse> findAllShortResponse(Pageable pageable);
}
