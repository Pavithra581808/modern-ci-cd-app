package com.example.productapp.service;

import com.example.productapp.dto.ProductDto;
import com.example.productapp.entity.Product;
import com.example.productapp.exception.ResourceNotFoundException;
import com.example.productapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldSaveAndReturnDto() {
        ProductDto dto = new ProductDto();
        dto.setName("Test");
        dto.setPrice(new BigDecimal("9.99"));

        Product saved = new Product("Test", null, new BigDecimal("9.99"));
        saved.setId(1L);

        when(repository.save(any(Product.class))).thenReturn(saved);

        ProductDto result = service.create(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test", result.getName());
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void getById_whenNotFound_shouldThrow() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getById(1L));
    }
}
