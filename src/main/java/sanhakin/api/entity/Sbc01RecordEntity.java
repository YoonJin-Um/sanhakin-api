
package sanhakin.api.entity;

import sanhakin.api.dto.Sbc01RecordDto;
import javax.persistence.*;

@Entity
@Table(name = "SANHAKIN_SBC01")
public class Sbc01RecordEntity {

    @Id
    @Column(name = "BIZR_NO", length = 20, nullable = false)
    private String bizrNo;

    @Column(name = "ENT_NM")
    private String entNm;

    @Column(name = "SPCLHS_RCRUT_NOPE")
    private Integer spclhsRcrutNope;

    @Column(name = "SPCLHS_RCRUT_ACML_NOPE")
    private Integer spclhsRcrutAcmlNope;

    @Column(name = "TECH_RCRUT_NOPE")
    private Integer techRcrutNope;

    @Column(name = "TECH_RCRUT_ACML_NOPE")
    private Integer techRcrutAcmlNope;

    @Column(name = "CNDEPT_TRN01_NOPE")
    private Integer cndeptTrn01Nope;

    @Column(name = "CNDEPT_TRN01_ACML_NOPE")
    private Integer cndeptTrn01AcmlNope;

    @Column(name = "CNDEPT_TRN02_NOPE")
    private Integer cndeptTrn02Nope;

    @Column(name = "CNDEPT_TRN02_ACML_NOPE")
    private Integer cndeptTrn02AcmlNope;

    @Column(name = "CNDEPT_TRN03_NOPE")
    private Integer cndeptTrn03Nope;

    @Column(name = "CNDEPT_TRN03_ACML_NOPE")
    private Integer cndeptTrn03AcmlNope;

    @Column(name = "CNDEPT_TRN04_NOPE")
    private Integer cndeptTrn04Nope;

    @Column(name = "CNDEPT_TRN04_ACML_NOPE")
    private Integer cndeptTrn04AcmlNope;

    @Column(name = "PLAN_EXPRY_NOCS")
    private Integer planExpryNocs;

    @Column(name = "PLAN_VLD_NOCS")
    private Integer planVldNocs;

    @Column(name = "PLAN_INTR_NOCS")
    private Integer planIntrNocs;

    @Column(name = "BSCI_EXPRY_NOCS")
    private Integer bsciExpryNocs;

    @Column(name = "BSCI_VLD_NOCS")
    private Integer bsciVldNocs;

    @Column(name = "BSCI_INTR_NOCS")
    private Integer bsciIntrNocs;

    public Sbc01RecordEntity(){}

    public void applyFromDto(Sbc01RecordDto dto){
        if(dto==null) return;
        this.bizrNo = dto.BIZR_NO;
        this.entNm = dto.ENT_NM;
        this.spclhsRcrutNope = dto.SPCLHS_RCRUT_NOPE;
        this.spclhsRcrutAcmlNope = dto.SPCLHS_RCRUT_ACML_NOPE;
        this.techRcrutNope = dto.TECH_RCRUT_NOPE;
        this.techRcrutAcmlNope = dto.TECH_RCRUT_ACML_NOPE;
        this.cndeptTrn01Nope = dto.CNDEPT_TRN01_NOPE;
        this.cndeptTrn01AcmlNope = dto.CNDEPT_TRN01_ACML_NOPE;
        this.cndeptTrn02Nope = dto.CNDEPT_TRN02_NOPE;
        this.cndeptTrn02AcmlNope = dto.CNDEPT_TRN02_ACML_NOPE;
        this.cndeptTrn03Nope = dto.CNDEPT_TRN03_NOPE;
        this.cndeptTrn03AcmlNope = dto.CNDEPT_TRN03_ACML_NOPE;
        this.cndeptTrn04Nope = dto.CNDEPT_TRN04_NOPE;
        this.cndeptTrn04AcmlNope = dto.CNDEPT_TRN04_ACML_NOPE;
        this.planExpryNocs = dto.PLAN_EXPRY_NOCS;
        this.planVldNocs = dto.PLAN_VLD_NOCS;
        this.planIntrNocs = dto.PLAN_INTR_NOCS;
        this.bsciExpryNocs = dto.BSCI_EXPRY_NOCS;
        this.bsciVldNocs = dto.BSCI_VLD_NOCS;
        this.bsciIntrNocs = dto.BSCI_INTR_NOCS;
    }

    public String getBizrNo(){ return this.bizrNo; }
    public void setBizrNo(String bizrNo){ this.bizrNo = bizrNo; }

    public String getEntNm(){ return this.entNm; }
    public void setEntNm(String entNm){ this.entNm = entNm; }

    public Integer getSpclhsRcrutNope(){ return spclhsRcrutNope; }
    public void setSpclhsRcrutNope(Integer spclhsRcrutNope){ this.spclhsRcrutNope = spclhsRcrutNope; }

    public Integer getSpclhsRcrutAcmlNope(){ return spclhsRcrutAcmlNope; }
    public void setSpclhsRcrutAcmlNope(Integer spclhsRcrutAcmlNope){ this.spclhsRcrutAcmlNope = spclhsRcrutAcmlNope; }

    public Integer getTechRcrutNope(){ return techRcrutNope; }
    public void setTechRcrutNope(Integer techRcrutNope){ this.techRcrutNope = techRcrutNope; }

    public Integer getTechRcrutAcmlNope(){ return techRcrutAcmlNope; }
    public void setTechRcrutAcmlNope(Integer techRcrutAcmlNope){ this.techRcrutAcmlNope = techRcrutAcmlNope; }

    public Integer getCndeptTrn01Nope(){ return cndeptTrn01Nope; }
    public void setCndeptTrn01Nope(Integer cndeptTrn01Nope){ this.cndeptTrn01Nope = cndeptTrn01Nope; }

    public Integer getCndeptTrn01AcmlNope(){ return cndeptTrn01AcmlNope; }
    public void setCndeptTrn01AcmlNope(Integer cndeptTrn01AcmlNope){ this.cndeptTrn01AcmlNope = cndeptTrn01AcmlNope; }

    public Integer getCndeptTrn02Nope(){ return cndeptTrn02Nope; }
    public void setCndeptTrn02Nope(Integer cndeptTrn02Nope){ this.cndeptTrn02Nope = cndeptTrn02Nope; }

    public Integer getCndeptTrn02AcmlNope(){ return cndeptTrn02AcmlNope; }
    public void setCndeptTrn02AcmlNope(Integer cndeptTrn02AcmlNope){ this.cndeptTrn02AcmlNope = cndeptTrn02AcmlNope; }

    public Integer getCndeptTrn03Nope(){ return cndeptTrn03Nope; }
    public void setCndeptTrn03Nope(Integer cndeptTrn03Nope){ this.cndeptTrn03Nope = cndeptTrn03Nope; }

    public Integer getCndeptTrn03AcmlNope(){ return cndeptTrn03AcmlNope; }
    public void setCndeptTrn03AcmlNope(Integer cndeptTrn03AcmlNope){ this.cndeptTrn03AcmlNope = cndeptTrn03AcmlNope; }

    public Integer getCndeptTrn04Nope(){ return cndeptTrn04Nope; }
    public void setCndeptTrn04Nope(Integer cndeptTrn04Nope){ this.cndeptTrn04Nope = cndeptTrn04Nope; }

    public Integer getCndeptTrn04AcmlNope(){ return cndeptTrn04AcmlNope; }
    public void setCndeptTrn04AcmlNope(Integer cndeptTrn04AcmlNope){ this.cndeptTrn04AcmlNope = cndeptTrn04AcmlNope; }

    public Integer getPlanExpryNocs(){ return planExpryNocs; }
    public void setPlanExpryNocs(Integer planExpryNocs){ this.planExpryNocs = planExpryNocs; }

    public Integer getPlanVldNocs(){ return planVldNocs; }
    public void setPlanVldNocs(Integer planVldNocs){ this.planVldNocs = planVldNocs; }

    public Integer getPlanIntrNocs(){ return planIntrNocs; }
    public void setPlanIntrNocs(Integer planIntrNocs){ this.planIntrNocs = planIntrNocs; }

    public Integer getBsciExpryNocs(){ return bsciExpryNocs; }
    public void setBsciExpryNocs(Integer bsciExpryNocs){ this.bsciExpryNocs = bsciExpryNocs; }

    public Integer getBsciVldNocs(){ return bsciVldNocs; }
    public void setBsciVldNocs(Integer bsciVldNocs){ this.bsciVldNocs = bsciVldNocs; }

    public Integer getBsciIntrNocs(){ return bsciIntrNocs; }
    public void setBsciIntrNocs(Integer bsciIntrNocs){ this.bsciIntrNocs = bsciIntrNocs; }
}
