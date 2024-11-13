package com.lumen.classifier.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lumen.classifier.entity.ClassifierInfoEntity;
import com.lumen.classifier.service.ClassifierService;

@RestController
@RequestMapping("/api")
public class ClassifierController {
	
	 @Autowired
	 private ClassifierService classifierService;
	
	 @GetMapping("/classifierInfo/{customerNbr}")
	 public ResponseEntity<?> getClassifierInfo(@PathVariable Long customerNbr) {
	     if (customerNbr == null || customerNbr <= 0) {
	         return ResponseEntity.badRequest().body("Customer number must be a positive number.");
	     }
	     try {
	         List<ClassifierInfoEntity> infoList = classifierService.getClassifierList(customerNbr);

	         if (infoList == null || infoList.isEmpty()) {
	             return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                  .body("No data available for customer number: " + customerNbr);
	         }
	         return ResponseEntity.ok(infoList);
	     } catch (IllegalArgumentException ex) {
	         return ResponseEntity.badRequest().body("Invalid request: " + ex.getMessage());
	     }
	 }

}
