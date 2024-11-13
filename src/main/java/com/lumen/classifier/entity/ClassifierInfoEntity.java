package com.lumen.classifier.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ClassifierInfoEntity {

	@Id
	@Column(name = "Customer_Nbr")
	private Long customerNbr;

	@Column(name = "secure_data_protection_ind")
	private Boolean secureDataProtectionInd;

	@Column(name = "secure_data_protection_typ")
	private List<String> secureDataProtectionTyp;

	@Column(name = "secure_data_segregation_typ")
	private List<String> secureDataSegregationTyp;
	
	@Column(name = "secure_data_segregation_subtyp")
	private List<String> secureDataSegregationSubtyp;
	
	

	public Long getCustomerNbr() {
		return customerNbr;
	}

	public void setCustomerNbr(Long customerNbr) {
		this.customerNbr = customerNbr;
	}

	public Boolean getSecureDataProtectionInd() {
		return secureDataProtectionInd;
	}

	public void setSecureDataProtectionInd(Boolean secureDataProtectionInd) {
		this.secureDataProtectionInd = secureDataProtectionInd;
	}

	public List<String> getSecureDataProtectionTyp() {
		return secureDataProtectionTyp;
	}

	public void setSecureDataProtectionTyp(List<String> secureDataProtectionTyp) {
		this.secureDataProtectionTyp = secureDataProtectionTyp;
	}

	public List<String> getSecureDataSegregationTyp() {
		return secureDataSegregationTyp;
	}

	public void setSecureDataSegregationTyp(List<String> secureDataSegregationTyp) {
		this.secureDataSegregationTyp = secureDataSegregationTyp;
	}

	public List<String> getSecureDataSegregationSubtyp() {
		return secureDataSegregationSubtyp;
	}

	public void setSecureDataSegregationSubtyp(List<String> secureDataSegregationSubtyp) {
		this.secureDataSegregationSubtyp = secureDataSegregationSubtyp;
	}
}
