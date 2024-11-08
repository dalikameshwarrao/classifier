package com.lumen.classifier.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lumen.classifier.DTO.ClassifierInfo;
import com.lumen.classifier.service.ClassifierService;

@RestController
@RequestMapping("/api")
public class ClassifierController {
	
	 @Autowired
	 private ClassifierService classifierService;
	
	 @GetMapping("/classifierInfo/{customerNbr}")
	    public ResponseEntity<List<ClassifierInfo>> getSegregationInfo2(@PathVariable Long customerNbr) {
	        List<ClassifierInfo> infoList = classifierService.getClassifierList(customerNbr);
	        return ResponseEntity.ok(infoList);
	}

}
