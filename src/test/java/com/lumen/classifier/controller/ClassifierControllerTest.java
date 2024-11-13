package com.lumen.classifier.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.lumen.classifier.entity.ClassifierInfoEntity;
import com.lumen.classifier.service.ClassifierService;

public class ClassifierControllerTest {

    @Mock
    private ClassifierService classifierService;

    @InjectMocks
    private ClassifierController classifierController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(classifierController).build();
    }
    
    @Test
    void getClassifierInfoShouldReturnOkWithData() throws Exception {
        Long customerNbr = 1001L;
        ClassifierInfoEntity classifierInfoEntity =new ClassifierInfoEntity();
        classifierInfoEntity.setCustomerNbr(customerNbr);
        List<ClassifierInfoEntity> infoList = Arrays.asList(classifierInfoEntity);
        when(classifierService.getClassifierList(customerNbr)).thenReturn(infoList);

        mockMvc.perform(get("/api/classifierInfo/{customerNbr}", customerNbr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getClassifierInfoShouldReturnBadRequestForInvalidCustomerNbr() throws Exception {
        mockMvc.perform(get("/api/classifierInfo/{customerNbr}", -1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Customer number must be a positive number."));
    }

    @Test
    void getClassifierInfoShouldReturnNotFoundWhenNoDataFound() throws Exception {
        Long customerNbr = 100L;
        when(classifierService.getClassifierList(customerNbr)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/classifierInfo/{customerNbr}", customerNbr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No data available for customer number: " + customerNbr));
    }

    
}
