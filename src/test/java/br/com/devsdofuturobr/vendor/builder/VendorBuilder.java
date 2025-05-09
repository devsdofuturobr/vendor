package br.com.devsdofuturobr.vendor.builder;

import br.com.devsdofuturobr.vendor.dto.request.VendorCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.VendorUpdateRequest;
import br.com.devsdofuturobr.vendor.entities.Vendor;
import br.com.devsdofuturobr.vendor.util.VendorParse;

public class VendorBuilder {

    public static Vendor toBuild(){
        VendorCreateRequest request = new VendorCreateRequest("Vendor Name", "Address", "City", "State", "Zip", "Country");
        return VendorParse.createByDTO(request);
    }

    public static VendorCreateRequest toRequestBuild(){
        return new VendorCreateRequest("Vendor Name", "Address", "City", "State", "Zip", "Country");
    }

    public static Vendor toUpdateBuild(){
        Vendor vendor = toBuild();
        vendor.setId(1L);
        VendorUpdateRequest request = new VendorUpdateRequest(1L, "Vendor name updated", "Address updated", "City updated", "State updated", "Zip updated", "Country updated");
        return VendorParse.updateByDTO(vendor, request);
    }

    public static VendorUpdateRequest toUpdateRequestBuild(){
        return new VendorUpdateRequest(1L, "Vendor name updated", "Address updated", "City updated", "State updated", "Zip updated", "Country updated");
    }
}
