package com.example.productapp.service;

import com.example.productapp.dto.ProductDto;
import com.example.productapp.entity.Product;
import com.example.productapp.exception.ResourceNotFoundException;
import com.example.productapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    private ProductDto mapToDto(Product p) {
        ProductDto dto = new ProductDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setPrice(p.getPrice());
        return dto;
    }

    private Product mapToEntity(ProductDto dto) {
        Product p = new Product();
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        return p;
    }

    @Override
    public ProductDto create(ProductDto dto) {
        Product saved = repository.save(mapToEntity(dto));
        return mapToDto(saved);
    }

    @Override
    public ProductDto getById(Long id) {
        Product p = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        return mapToDto(p);
    }

    @Override
    public List<ProductDto> getAll() {
        return repository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto update(Long id, ProductDto dto) {
        Product existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        Product saved = repository.save(existing);
        return mapToDto(saved);
    }

    @Override
    public void delete(Long id) {
        Product existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        repository.delete(existing);
    }
}
