package br.com.devsdofuturobr.vendor.services.impl;

import br.com.devsdofuturobr.vendor.dto.request.VendorCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.VendorUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.VendorFullResponse;
import br.com.devsdofuturobr.vendor.entities.Vendor;
import br.com.devsdofuturobr.vendor.exception.VendorNotFoundException;
import br.com.devsdofuturobr.vendor.repositories.VendorRepository;
import br.com.devsdofuturobr.vendor.services.VendorService;
import br.com.devsdofuturobr.vendor.util.VendorParse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository repository;
    private final VendorParse vendorParse;

    @Override
    public VendorFullResponse create(VendorCreateRequest request) {
        Vendor toCreate = vendorParse.createByDTO.apply(request);
        Vendor saved = repository.save(toCreate);
        return vendorParse.toDTO.apply(saved);
    }

    @Override
    public VendorFullResponse update(VendorUpdateRequest request) {
        Vendor vendor = findByIdAndReturnEntity(request.id());
        Vendor toUpdate = vendorParse.updateByDTO(vendor).apply(request);
        Vendor updated = repository.save(toUpdate);
        return vendorParse.toDTO.apply(updated);
    }

    @Override
    public VendorFullResponse findById(Long id) {
        Vendor vendor = findByIdAndReturnEntity(id);
        return vendorParse.toDTO.apply(vendor);
    }

    @Override
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new VendorNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public Page<VendorFullResponse> findAll(Pageable pageable) {
        Page<Vendor> all = repository.findAll(pageable);
        return vendorParse.toPage.apply(all);
    }

    @Override
    public Vendor findByIdAndReturnEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new VendorNotFoundException(id));
    }
}
