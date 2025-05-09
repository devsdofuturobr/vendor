package br.com.devsdofuturobr.vendor.services.impl;

import br.com.devsdofuturobr.vendor.dto.request.VendorCreateRequest;
import br.com.devsdofuturobr.vendor.dto.request.VendorUpdateRequest;
import br.com.devsdofuturobr.vendor.dto.response.VendorShortProjectionResponse;
import br.com.devsdofuturobr.vendor.entities.Vendor;
import br.com.devsdofuturobr.vendor.exception.VendorNotFoundException;
import br.com.devsdofuturobr.vendor.repositories.VendorRepository;
import br.com.devsdofuturobr.vendor.services.VendorService;
import br.com.devsdofuturobr.vendor.util.VendorParse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VendorServiceImpl implements VendorService {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final VendorRepository repository;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public Vendor create(VendorCreateRequest request) {
        Vendor vendor = repository.save(VendorParse.createByDTO(request));
        rabbitTemplate.convertAndSend(exchangeName, routingKey, vendor);
        return vendor;
    }

    @Override
    public Vendor update(VendorUpdateRequest request) {
        Vendor vendor = this.findById(request.id());
        Vendor toUpdate = VendorParse.updateByDTO(vendor, request);
        return repository.save(toUpdate);
    }

    @Override
    public Vendor findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new VendorNotFoundException(id));
    }

    @Override
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new VendorNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public Page<Vendor> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<VendorShortProjectionResponse> findAllShortResponse(Pageable pageable) {
        return repository.findAllShortResponse(pageable);
    }
}
