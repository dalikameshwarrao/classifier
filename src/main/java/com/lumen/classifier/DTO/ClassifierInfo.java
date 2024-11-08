package com.lumen.classifier.DTO;

import java.util.List;

public class ClassifierInfo {
	
	private Boolean secureDataProtectionInd;
    private List<String> secureDataProtectionTyp;
    private List<String> secureDataSegregationTyp;
    private List<String> secureDataSegregationSubtyp;

    public ClassifierInfo(Boolean secureDataProtectionInd,
                           List<String> secureDataProtectionTyp,
                           List<String> secureDataSegregationTyp,
                           List<String> secureDataSegregationSubtyp) {
        this.secureDataProtectionInd = secureDataProtectionInd;
        this.secureDataProtectionTyp = secureDataProtectionTyp;
        this.secureDataSegregationTyp = secureDataSegregationTyp;
        this.secureDataSegregationSubtyp = secureDataSegregationSubtyp;
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
