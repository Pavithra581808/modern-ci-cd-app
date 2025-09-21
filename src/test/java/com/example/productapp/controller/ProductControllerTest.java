package com.example.productapp.controller;

import com.example.productapp.dto.ProductDto;
import com.example.productapp.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_shouldReturnCreated() throws Exception {
        ProductDto dto = new ProductDto();
        dto.setName("Prod");
        dto.setPrice(new BigDecimal("1.00"));

        ProductDto created = new ProductDto();
        created.setId(1L);
        created.setName("Prod");
        created.setPrice(new BigDecimal("1.00"));

        when(service.create(org.mockito.ArgumentMatchers.any(ProductDto.class))).thenReturn(created);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}
