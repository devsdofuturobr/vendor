package br.com.devsdofuturobr.vendor.util;

import br.com.devsdofuturobr.vendor.dto.request.VendorCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.VendorUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.VendorFullResponse;
import br.com.devsdofuturobr.vendor.dto.response.VendorShortResponse;
import br.com.devsdofuturobr.vendor.entities.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

@Component
public class VendorParse {


    public Function<Vendor, VendorFullResponse> toDTO =
            vendor -> new VendorFullResponse(
                    vendor.getId(),
                    vendor.getName(),
                    vendor.getAddress(),
                    vendor.getCity(),
                    vendor.getState(),
                    vendor.getZip(),
                    vendor.getCountry()
            );


    public Function<VendorCreateRequest, Vendor> createByDTO =
            request -> Vendor.builder()
                    .name(request.name())
                    .address(request.address())
                    .city(request.city())
                    .state(request.state())
                    .zip(request.zip())
                    .country(request.country())
                    .build();


    public Function<Vendor, VendorShortResponse> toShortDTO =
            vendor -> new VendorShortResponse(
                    vendor.getId(),
                    vendor.getName()
            );


    public Function<Page<Vendor>, Page<VendorFullResponse>> toPage = page -> page.map(toDTO);


    public Function<VendorUpdateRequest, Vendor> updateByDTO(Vendor toUpdate) {
        return request -> {
            toUpdate.setName(ObjectUtils.isEmpty(request.name()) ? toUpdate.getName() : request.name());
            toUpdate.setAddress(ObjectUtils.isEmpty(request.address()) ? toUpdate.getAddress() : request.address());
            toUpdate.setCity(ObjectUtils.isEmpty(request.city()) ? toUpdate.getCity() : request.city());
            toUpdate.setState(ObjectUtils.isEmpty(request.state()) ? toUpdate.getState() : request.state());
            toUpdate.setZip(ObjectUtils.isEmpty(request.zip()) ? toUpdate.getZip() : request.zip());
            toUpdate.setCountry(ObjectUtils.isEmpty(request.country()) ? toUpdate.getCountry() : request.country());
            return toUpdate;
        };
    }
}
