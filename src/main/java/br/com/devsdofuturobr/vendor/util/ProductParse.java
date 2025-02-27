package br.com.devsdofuturobr.vendor.util;

import br.com.devsdofuturobr.vendor.dto.request.ProductCreateRequest;
import br.com.devsdofuturobr.vendor.dto.response.ProductCreatedResponse;
import br.com.devsdofuturobr.vendor.entities.Product;
import br.com.devsdofuturobr.vendor.entities.Vendor;

public class ProductParse {

    public static ProductCreatedResponse toCreatedDTO(Product product){
        return new ProductCreatedResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription()
        );
    }

    public static Product createByDTO(Vendor vendor, ProductCreateRequest request){
        return Product.builder()
                .vendor(vendor)
                .name(request.name())
                .price(request.price())
                .description(request.description())
                .build();
    }
}
