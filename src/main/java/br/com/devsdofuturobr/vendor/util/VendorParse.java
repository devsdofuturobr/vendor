package br.com.devsdofuturobr.vendor.util;

import br.com.devsdofuturobr.vendor.dto.request.VendorCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.VendorUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.VendorFullResponse;
import br.com.devsdofuturobr.vendor.dto.response.VendorShortResponse;
import br.com.devsdofuturobr.vendor.entities.Vendor;
import org.springframework.data.domain.Page;

import java.util.Optional;

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

    public static Vendor updateByDTO(Vendor vendor, VendorUpdateRequest request) {
        vendor.setName(Optional.ofNullable(request.name()).orElse(vendor.getName()));
        vendor.setAddress(Optional.ofNullable(request.address()).orElse(vendor.getAddress()));
        vendor.setCity(Optional.ofNullable(request.city()).orElse(vendor.getCity()));
        vendor.setState(Optional.ofNullable(request.state()).orElse(vendor.getState()));
        vendor.setZip(Optional.ofNullable(request.zip()).orElse(vendor.getZip()));
        vendor.setCountry(Optional.ofNullable(request.country()).orElse(vendor.getCountry()));
        return vendor;
    }

    public static VendorShortResponse toShortDTO(Vendor vendor){
        return new VendorShortResponse(
                vendor.getId(),
                vendor.getName()
        );
    }

    public static Page<VendorFullResponse> toPage(Page<Vendor> all) {
        return all.map(VendorParse::toDTO);
    }
}
