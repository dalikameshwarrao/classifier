package com.lumen.classifier.entity;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.ConstructorResult;

@Entity
@Table(name = "Segregation_Lookup", schema = "dh_classifier")
public class SegregationLookup {

    @Id
    @Column(name = "Customer_Nbr")
    private Long customerNbr;

    @Column(name = "LOB_Code")
    private Integer lobCode;

    @Column(name = "Secure_Data_Protection_Ind")
    private Boolean secureDataProtectionInd;

    @ElementCollection
    @Column(name = "Secure_Data_Protection_Typ")
    private List<String> secureDataProtectionTyp;

    @ElementCollection
    @Column(name = "Secure_Data_Segregation_Typ")
    private List<String> secureDataSegregationTyp;

    @ElementCollection
    @Column(name = "Secure_Data_Segregation_Subtyp")
    private List<String> secureDataSegregationSubtyp;

    // Getters and Setters
    public Long getCustomerNbr() {
        return customerNbr;
    }

    public void setCustomerNbr(Long customerNbr) {
        this.customerNbr = customerNbr;
    }

    public Integer getLobCode() {
        return lobCode;
    }

    public void setLobCode(Integer lobCode) {
        this.lobCode = lobCode;
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
