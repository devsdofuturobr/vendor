package br.com.devsdofuturobr.vendor.services;

import br.com.devsdofuturobr.vendor.builder.VendorBuilder;
import br.com.devsdofuturobr.vendor.dto.request.VendorCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.VendorUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.VendorFullResponse;
import br.com.devsdofuturobr.vendor.entities.Vendor;
import br.com.devsdofuturobr.vendor.exception.VendorNotFoundException;
import br.com.devsdofuturobr.vendor.repositories.VendorRepository;
import br.com.devsdofuturobr.vendor.services.impl.VendorServiceImpl;
import br.com.devsdofuturobr.vendor.util.VendorParse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class VendorServiceTest {

    @Mock
    private VendorRepository repository;

    @InjectMocks
    private VendorServiceImpl vendorService;

    private VendorBuilder vendorBuilder;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vendorBuilder = new VendorBuilder(new VendorParse());
        vendorService = new VendorServiceImpl(repository, new VendorParse());
    }

    @Test
    void testCreate() {
        VendorCreateRequest request = vendorBuilder.toRequestBuild();
        Vendor vendorCreated = vendorBuilder.toBuild();
        vendorCreated.setId(1L);

        ArgumentCaptor<Vendor> vendorCaptor = ArgumentCaptor.forClass(Vendor.class);

        when(repository.save(any(Vendor.class))).thenReturn(vendorCreated);


        VendorFullResponse result = vendorService.create(request);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Vendor Name", result.name());
        assertEquals("Address", result.address());
        assertEquals("City", result.city());
        assertEquals("State", result.state());
        assertEquals("Zip", result.zip());
        assertEquals("Country", result.country());

        //verify(repository, times(1)).save(any(Vendor.class));

        // Captura o argumento passado ao repository.save
        verify(repository, times(1)).save(vendorCaptor.capture());
        Vendor capturedVendor = vendorCaptor.getValue();

        // Verifica se o objeto salvo tem os valores corretos
        assertNull(capturedVendor.getId());
        assertEquals("Vendor Name", capturedVendor.getName());
        assertEquals("Address", capturedVendor.getAddress());
        assertEquals("City", capturedVendor.getCity());
        assertEquals("State", capturedVendor.getState());
        assertEquals("Zip", capturedVendor.getZip());
        assertEquals("Country", capturedVendor.getCountry());
    }

    @Test
    void testFindById() {
        Vendor vendor = vendorBuilder.toBuild();
        vendor.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(vendor));

        VendorFullResponse result = vendorService.findById(1L);

        assertNotNull(result);
        assertEquals("Vendor Name", result.name());
        assertEquals("Address", result.address());
        assertEquals("City", result.city());
        assertEquals("State", result.state());
        assertEquals("Zip", result.zip());
        assertEquals("Country", result.country());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VendorNotFoundException.class, () -> vendorService.findById(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testUpdate() {
        VendorUpdateRequest request = vendorBuilder.toUpdateRequestBuild();
        Vendor vendor = vendorBuilder.toBuild();
        vendor.setId(1L);
        Vendor vendorUpdated = vendorBuilder.toUpdateBuild();

        when(repository.findById(request.id())).thenReturn(Optional.of(vendor));
        when(repository.save(any(Vendor.class))).thenReturn(vendorUpdated);

        VendorFullResponse result = vendorService.update(request);

        assertNotNull(result);
        assertEquals("Vendor name updated", result.name());
        assertEquals("Address updated", result.address());
        assertEquals("City updated", result.city());
        assertEquals("State updated", result.state());
        assertEquals("Zip updated", result.zip());
        assertEquals("Country updated", result.country());
        verify(repository, times(1)).findById(request.id());
        verify(repository, times(1)).save(any(Vendor.class));
    }

    @Test
    void testDelete() {
        when(repository.existsById(1L)).thenReturn(true);

        vendorService.delete(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(VendorNotFoundException.class, () -> vendorService.delete(1L));
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(0)).deleteById(1L);
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Vendor> vendors = new ArrayList<>();
        for(int i = 0; i < 15; i++){
            vendors.add(vendorBuilder.toBuild());
        }
        Page<Vendor> page = new PageImpl<>(vendors, pageable, vendors.size());
        when(repository.findAll(pageable)).thenReturn(page);

        Page<VendorFullResponse> result = vendorService.findAll(pageable);

        assertNotNull(result);
        assertEquals(10, result.getSize());
        assertEquals(15, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
        verify(repository, times(1)).findAll(pageable);
    }

}
