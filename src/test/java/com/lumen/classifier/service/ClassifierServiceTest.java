package com.lumen.classifier.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lumen.classifier.entity.ClassifierInfoEntity;
import com.lumen.classifier.exception.ClassifierException;
import com.lumen.classifier.repository.ClassifierRepository;

public class ClassifierServiceTest {

	@Mock
	private ClassifierRepository classifierRepository;

	@InjectMocks
	private ClassifierService classifierService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getClassifierListShouldReturnDataWhenDataExists() {

		ClassifierInfoEntity classifierInfoEntity = new ClassifierInfoEntity();
		classifierInfoEntity.setCustomerNbr(1001L);
		classifierInfoEntity.setSecureDataProtectionInd(true);
		classifierInfoEntity.setSecureDataProtectionTyp(Arrays.asList("Type1"));
		classifierInfoEntity.setSecureDataSegregationTyp(Arrays.asList("Segregation1"));
		classifierInfoEntity.setSecureDataSegregationSubtyp(Arrays.asList("Subtype1"));
		List<ClassifierInfoEntity> mockData = Arrays.asList(classifierInfoEntity);
		when(classifierRepository.getClassifierList(classifierInfoEntity.getCustomerNbr())).thenReturn(mockData);
		List<ClassifierInfoEntity> result = classifierService.getClassifierList(classifierInfoEntity.getCustomerNbr());
		assertNotNull(result, "Result should not be null");
		assertEquals(1, result.size(), "Result size should be 1");
		verify(classifierRepository, times(1)).getClassifierList(classifierInfoEntity.getCustomerNbr());
	}

	@Test
	void getClassifierListShouldThrowClassifierExceptionWhenRepositoryThrowsException() {

		Long customerNbr = 100L;
		when(classifierRepository.getClassifierList(customerNbr)).thenThrow(new RuntimeException("Database Error"));
		ClassifierException exception = assertThrows(ClassifierException.class, () -> {
			classifierService.getClassifierList(customerNbr);
		});
		assertEquals("Error while fetching classifier list for customer: ", exception.getMessage());
		assertEquals(customerNbr, exception.getCustomerNbr());
		verify(classifierRepository, times(1)).getClassifierList(customerNbr);
	}

	@Test
	void getClassifierListShouldReturnEmptyListWhenNoDataFound() {

		Long customerNbr = 100L;
		when(classifierRepository.getClassifierList(customerNbr)).thenReturn(Collections.emptyList());
		List<ClassifierInfoEntity> result = classifierService.getClassifierList(customerNbr);
		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(classifierRepository, times(1)).getClassifierList(customerNbr);
	}
}
