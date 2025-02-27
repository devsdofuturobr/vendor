package br.com.devsdofuturobr.vendor.repositories;

import br.com.devsdofuturobr.vendor.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
