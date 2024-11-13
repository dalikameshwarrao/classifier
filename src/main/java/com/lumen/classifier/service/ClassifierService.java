package com.lumen.classifier.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lumen.classifier.entity.ClassifierInfoEntity;
import com.lumen.classifier.exception.ClassifierException;
import com.lumen.classifier.repository.ClassifierRepository;

import jakarta.transaction.Transactional;

@Service
public class ClassifierService {
	
	@Autowired
	ClassifierRepository classifierRepository;
	
    @Transactional
    public List<ClassifierInfoEntity> getClassifierList(Long customerNbr) {
        try {
            return classifierRepository.getClassifierList(customerNbr);
        } catch (Exception e) {
            throw new ClassifierException("Error while fetching classifier list for customer: ", customerNbr);
        }
    }

}
