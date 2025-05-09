package br.com.devsdofuturobr.vendor.services;

import br.com.devsdofuturobr.vendor.builder.VendorBuilder;
import br.com.devsdofuturobr.vendor.dto.request.VendorCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.VendorUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.VendorShortProjectionResponse;
import br.com.devsdofuturobr.vendor.entities.Vendor;
import br.com.devsdofuturobr.vendor.exception.VendorNotFoundException;
import br.com.devsdofuturobr.vendor.repositories.VendorRepository;
import br.com.devsdofuturobr.vendor.services.impl.VendorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

public class VendorServiceTest {

    @Mock
    private VendorRepository repository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private VendorServiceImpl vendorService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vendorService = new VendorServiceImpl(repository, rabbitTemplate);
    }

    @Test
    void testCreate() {
        VendorCreateRequest request = VendorBuilder.toRequestBuild();
        Vendor vendorCreated = VendorBuilder.toBuild();
        vendorCreated.setId(1L);

        ArgumentCaptor<Vendor> vendorCaptor = ArgumentCaptor.forClass(Vendor.class);

        when(repository.save(any(Vendor.class))).thenReturn(vendorCreated);


        Vendor result = vendorService.create(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Vendor Name", result.getName());
        assertEquals("Address", result.getAddress());
        assertEquals("City", result.getCity());
        assertEquals("State", result.getState());
        assertEquals("Zip", result.getZip());
        assertEquals("Country", result.getCountry());

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
        Vendor vendor = VendorBuilder.toBuild();
        vendor.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(vendor));

        Vendor result = vendorService.findById(1L);

        assertNotNull(result);
        assertEquals("Vendor Name", result.getName());
        assertEquals("Address", result.getAddress());
        assertEquals("City", result.getCity());
        assertEquals("State", result.getState());
        assertEquals("Zip", result.getZip());
        assertEquals("Country", result.getCountry());

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
        VendorUpdateRequest request = VendorBuilder.toUpdateRequestBuild();
        Vendor vendor = VendorBuilder.toBuild();
        vendor.setId(1L);
        Vendor vendorUpdated = VendorBuilder.toUpdateBuild();

        when(repository.findById(request.id())).thenReturn(Optional.of(vendor));
        when(repository.save(any(Vendor.class))).thenReturn(vendorUpdated);

        Vendor result = vendorService.update(request);

        assertNotNull(result);
        assertEquals("Vendor name updated", result.getName());
        assertEquals("Address updated", result.getAddress());
        assertEquals("City updated", result.getCity());
        assertEquals("State updated", result.getState());
        assertEquals("Zip updated", result.getZip());
        assertEquals("Country updated", result.getCountry());
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
            vendors.add(VendorBuilder.toBuild());
        }
        Page<Vendor> page = new PageImpl<>(vendors, pageable, vendors.size());
        when(repository.findAll(pageable)).thenReturn(page);

        Page<Vendor> result = vendorService.findAll(pageable);

        assertNotNull(result);
        assertEquals(10, result.getSize());
        assertEquals(15, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void testFindAllShortResponse() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<VendorShortProjectionResponse> page = new PageImpl<>(List.of());
        when(repository.findAllShortResponse(pageable)).thenReturn(page);

        Page<VendorShortProjectionResponse> result = vendorService.findAllShortResponse(pageable);

        assertNotNull(result);
        verify(repository, times(1)).findAllShortResponse(pageable);
    }
}
