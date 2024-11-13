package com.lumen.classifier.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import com.lumen.classifier.entity.ClassifierInfoEntity;
import com.lumen.classifier.service.ClassifierService;

@ExtendWith(MockitoExtension.class)  // For unit tests with Mockito
@SpringBootTest  // For integration tests (if needed)
class ClassifierRepositoryTest {

    @Mock
    private ClassifierRepository classifierRepository;

    @InjectMocks
    private ClassifierService classifierService; // The service which uses the repository, if applicable

    @BeforeEach
    void setUp() {
        // Setup mocks or test data
    }

    @Test
    void testGetClassifierList() {
        // Mock the repository method
        ClassifierInfoEntity mockEntity = new ClassifierInfoEntity();
        mockEntity.setCustomerNbr(1001L);
        
        List<ClassifierInfoEntity> mockList = List.of(mockEntity);
        when(classifierRepository.getClassifierList(anyLong())).thenReturn(mockList);
        
        // Call the repository method through service or directly
        List<ClassifierInfoEntity> result = classifierRepository.getClassifierList(123L);

        // Assert the results
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCustomerNbr()).isEqualTo(1001L);
    }

    @Test
    void testGetClassifierList_withNoResults() {
        // Mock an empty list when no data is found
        when(classifierRepository.getClassifierList(anyLong())).thenReturn(List.of());

        List<ClassifierInfoEntity> result = classifierRepository.getClassifierList(999L);

        assertThat(result).isEmpty();
    }

    @Test
    void testGetClassifierList_withDatabaseError() {
        // Simulate a database exception (DataAccessException) to test error handling
        when(classifierRepository.getClassifierList(anyLong())).thenThrow(new DataAccessException("Database error") {});

        try {
            classifierRepository.getClassifierList(123L);
        } catch (DataAccessException e) {
            assertThat(e).hasMessage("Database error");
        }
    }
}
