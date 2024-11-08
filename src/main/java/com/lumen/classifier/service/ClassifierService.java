package com.lumen.classifier.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lumen.classifier.DTO.ClassifierInfo;
import com.lumen.classifier.repository.ClassifierRepository;

@Service
public class ClassifierService {
	
	@Autowired
	ClassifierRepository classifierRepository;
	
	public List<ClassifierInfo> getClassifierList(Long customerNbr) {
	    try {
	        List<Object[]> classifierInfoList = classifierRepository.getClassifierList(customerNbr);

	        return classifierInfoList.stream().map(result -> new ClassifierInfo(
	            (Boolean) result[0], // secureDataProtectionInd
	            Arrays.asList((String[]) result[1]),  // secureDataProtectionTyp
	            Arrays.asList((String[]) result[2]),  // secureDataSegregationTyp
	            Arrays.asList((String[]) result[3])   // secureDataSegregationSubtyp
	        )).toList();

	    } catch (Exception e) {
	        throw new RuntimeException("Error executing function fn_Get_Segregation_Info", e);
	    }
	}

}
