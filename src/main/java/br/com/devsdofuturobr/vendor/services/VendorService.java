package br.com.devsdofuturobr.vendor.services;

import br.com.devsdofuturobr.vendor.dto.request.VendorCreateRequest;
import br.com.devsdofuturobr.vendor.entities.Vendor;

public interface VendorService {


    Vendor create(VendorCreateRequest request);
}
