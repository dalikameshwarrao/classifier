package com.lumen.classifier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lumen.classifier.entity.ClassifierInfoEntity;

public interface ClassifierRepository extends JpaRepository <ClassifierInfoEntity, Long>  {
	
	@Query(
	        value = "SELECT * FROM \"dh_classifier\".\"fn_Get_Segregation_Info\"(:customerNbr)",
	        nativeQuery = true
	    )
	    List<ClassifierInfoEntity> getClassifierList(@Param("customerNbr") Long customerNbr);
	
}
