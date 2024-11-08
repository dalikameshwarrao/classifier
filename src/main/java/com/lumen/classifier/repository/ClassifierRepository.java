package com.lumen.classifier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lumen.classifier.DTO.ClassifierInfo;
import com.lumen.classifier.entity.SegregationLookup;

public interface ClassifierRepository extends JpaRepository <SegregationLookup, Long>  {
	
	@Query(
	        value = "SELECT * FROM \"dh_classifier\".\"fn_Get_Segregation_Info\"(:customerNbr)",
	        nativeQuery = true
	    )
	    List<Object[]> getClassifierList(@Param("customerNbr") Long customerNbr);
	
}
