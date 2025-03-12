package br.com.devsdofuturobr.vendor.builder;

import br.com.devsdofuturobr.vendor.dto.request.VendorCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.VendorUpdateRequest;
import br.com.devsdofuturobr.vendor.entities.Vendor;
import br.com.devsdofuturobr.vendor.util.VendorParse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VendorBuilder {

    private final VendorParse vendorParse;

    public Vendor toBuild(){
        VendorCreateRequest request = new VendorCreateRequest("Vendor Name", "Address", "City", "State", "Zip", "Country");
        return vendorParse.createByDTO.apply(request);
    }

    public VendorCreateRequest toRequestBuild(){
        return new VendorCreateRequest("Vendor Name", "Address", "City", "State", "Zip", "Country");
    }

    public Vendor toUpdateBuild(){
        Vendor vendor = toBuild();
        vendor.setId(1L);
        VendorUpdateRequest request = new VendorUpdateRequest(1L, "Vendor name updated", "Address updated", "City updated", "State updated", "Zip updated", "Country updated");
        return vendorParse.updateByDTO(vendor).apply(request);
    }

    public VendorUpdateRequest toUpdateRequestBuild(){
        return new VendorUpdateRequest(1L, "Vendor name updated", "Address updated", "City updated", "State updated", "Zip updated", "Country updated");
    }
}
