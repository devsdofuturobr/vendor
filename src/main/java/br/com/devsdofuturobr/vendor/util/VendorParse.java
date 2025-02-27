package br.com.devsdofuturobr.vendor.util;

import br.com.devsdofuturobr.vendor.dto.request.VendorCreateRequest;
import br.com.devsdofuturobr.vendor.dto.response.VendorFullResponse;
import br.com.devsdofuturobr.vendor.entities.Vendor;

public class VendorParse {

    public static VendorFullResponse toDTO(Vendor vendor){
        return new VendorFullResponse(
                vendor.getId(),
                vendor.getName(),
                vendor.getAddress(),
                vendor.getCity(),
                vendor.getState(),
                vendor.getZip(),
                vendor.getCountry()
        );
    }

    public static Vendor createByDTO(VendorCreateRequest request){
        return Vendor.builder()
                .name(request.name())
                .address(request.address())
                .city(request.city())
                .state(request.state())
                .zip(request.zip())
                .country(request.country())
                .build();
    }
}
