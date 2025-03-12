package br.com.devsdofuturobr.vendor.services;

import br.com.devsdofuturobr.vendor.dto.request.VendorCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.VendorUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.VendorFullResponse;
import br.com.devsdofuturobr.vendor.entities.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VendorService {


    VendorFullResponse create(VendorCreateRequest request);

    VendorFullResponse update(VendorUpdateRequest request);

    VendorFullResponse findById(Long id);

    void delete(Long id);

    Page<VendorFullResponse> findAll(Pageable pageable);

    Vendor findByIdAndReturnEntity(Long id);

}
