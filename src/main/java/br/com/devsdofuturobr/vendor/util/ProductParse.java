package br.com.devsdofuturobr.vendor.util;

import br.com.devsdofuturobr.vendor.dto.request.ProductCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.ProductUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.ProductFullResponse;
import br.com.devsdofuturobr.vendor.dto.response.ProductResponse;
import br.com.devsdofuturobr.vendor.entities.Product;
import br.com.devsdofuturobr.vendor.entities.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

@Component
public class ProductParse {

    @Autowired
    private VendorParse vendorParse;

    public Function<Product, ProductResponse> toProductResponseDTO =
            product -> new ProductResponse(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getDescription()
            );


    public Function<ProductCreateRequest, Product> createByDTO(Vendor vendor) {
        return request ->
                Product.builder()
                        .vendor(vendor)
                        .name(request.name())
                        .price(request.price())
                        .description(request.description())
                        .build();
    }


    public Function<ProductUpdateRequest, Product> updateByDTO(Product toUpdate) {
        return product -> {
            toUpdate.setName(ObjectUtils.isEmpty(product.name()) ? toUpdate.getName() : product.name());
            toUpdate.setPrice(ObjectUtils.isEmpty(product.price()) ? toUpdate.getPrice() : product.price());
            toUpdate.setDescription(ObjectUtils.isEmpty(product.description()) ? toUpdate.getDescription() : product.description());

            return toUpdate;
        };
    }


    public Function<Product, ProductFullResponse> toProductFullResponse =
            product -> new ProductFullResponse(
                    product.getId(),
                    vendorParse.toShortDTO.apply(product.getVendor()),
                    product.getName(),
                    product.getPrice(),
                    product.getDescription()
            );


    public Function<Page<Product>, Page<ProductFullResponse>> toPageFullResponse = page -> page.map(toProductFullResponse);
}
