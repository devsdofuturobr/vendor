package br.com.devsdofuturobr.vendor.repositories;

import br.com.devsdofuturobr.vendor.dto.response.ProductProjectionResponse;
import br.com.devsdofuturobr.vendor.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p.id AS id, p.name AS name, p.price AS price, p.description AS description FROM Product p WHERE p.vendor.id = :vendorId")
    Page<ProductProjectionResponse> findByVendorId(Long vendorId, Pageable pageable);
}
