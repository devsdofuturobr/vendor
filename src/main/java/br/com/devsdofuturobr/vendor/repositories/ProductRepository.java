package br.com.devsdofuturobr.vendor.repositories;

import br.com.devsdofuturobr.vendor.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
