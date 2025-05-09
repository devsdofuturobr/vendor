package br.com.devsdofuturobr.vendor.repositories;

import br.com.devsdofuturobr.vendor.dto.response.VendorShortProjectionResponse;
import br.com.devsdofuturobr.vendor.entities.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    @Query("SELECT v.id AS id, v.name AS name FROM Vendor v")
    Page<VendorShortProjectionResponse> findAllShortResponse(Pageable pageable);
}
