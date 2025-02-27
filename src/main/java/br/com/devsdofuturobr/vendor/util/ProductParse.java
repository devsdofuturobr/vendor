package br.com.devsdofuturobr.vendor.util;

import br.com.devsdofuturobr.vendor.dto.request.ProductCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.ProductUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.ProductFullResponse;
import br.com.devsdofuturobr.vendor.dto.response.ProductResponse;
import br.com.devsdofuturobr.vendor.entities.Product;
import br.com.devsdofuturobr.vendor.entities.Vendor;
import org.springframework.data.domain.Page;

import java.util.Optional;

public class ProductParse {



    public static ProductResponse toProductResponseDTO(Product product){
        return new ProductResponse(
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


    public static Product updateByDTO(Product product,ProductUpdateRequest request){
        product.setName(Optional.ofNullable(request.name()).orElse(product.getName()));
        product.setPrice(Optional.ofNullable(request.price()).orElse(product.getPrice()));
        product.setDescription(Optional.ofNullable(request.description()).orElse(product.getDescription()));
        return product;
    }

    public static ProductFullResponse toProductFullResponse(Product product) {
        return new ProductFullResponse(
                product.getId(),
                VendorParse.toShortDTO(product.getVendor()),
                product.getName(),
                product.getPrice(),
                product.getDescription()
        );
    }

    public static Page<ProductFullResponse> toPageFullResponse(Page<Product> all) {
        return all.map(ProductParse::toProductFullResponse);
    }
}
