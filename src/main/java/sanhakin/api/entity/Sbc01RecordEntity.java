
package sanhakin.api.entity;

import sanhakin.api.dto.Sbc01RecordDto;
import javax.persistence.*;

@Entity
@Table(name = "SBC01_RECORD")
public class Sbc01RecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 20)
    private String bizrNo;

    private String entNm;

    private Integer spclhsRcrutAcmlNope;
    private Integer planIntrNocs;
    private Integer techRcrutAcmlNope;
    private Integer techRcrutNope;
    private Integer cndeptTrn04Nope;
    private Integer cndeptTrn04AcmlNope;
    private Integer cndeptTrn01Nope;
    private Integer bsciExpryNocs;
    private Integer bsciVldNocs;
    private Integer bsciIntrNocs;
    private Integer cndeptTrn03Nope;
    private Integer planVldNocs;
    private Integer cndeptTrn01AcmlNope;
    private Integer cndeptTrn03AcmlNope;
    private Integer spclhsRcrutNope;
    private Integer planExpryNocs;
    private Integer cndeptTrn02Nope;
    private Integer cndeptTrn02AcmlNope;

    public void applyFromDto(Sbc01RecordDto dto){
        this.bizrNo=dto.BIZR_NO;
        this.entNm=dto.ENT_NM;

        this.spclhsRcrutAcmlNope=dto.SPCLHS_RCRUT_ACML_NOPE;
        this.planIntrNocs=dto.PLAN_INTR_NOCS;
        this.techRcrutAcmlNope=dto.TECH_RCRUT_ACML_NOPE;
        this.techRcrutNope=dto.TECH_RCRUT_NOPE;

        this.cndeptTrn04Nope=dto.CNDEPT_TRN04_NOPE;
        this.cndeptTrn04AcmlNope=dto.CNDEPT_TRN04_ACML_NOPE;
        this.cndeptTrn01Nope=dto.CNDEPT_TRN01_NOPE;

        this.bsciExpryNocs=dto.BSCI_EXPRY_NOCS;
        this.bsciVldNocs=dto.BSCI_VLD_NOCS;
        this.bsciIntrNocs=dto.BSCI_INTR_NOCS;

        this.cndeptTrn03Nope=dto.CNDEPT_TRN03_NOPE;
        this.planVldNocs=dto.PLAN_VLD_NOCS;
        this.cndeptTrn01AcmlNope=dto.CNDEPT_TRN01_ACML_NOPE;
        this.cndeptTrn03AcmlNope=dto.CNDEPT_TRN03_ACML_NOPE;

        this.spclhsRcrutNope=dto.SPCLHS_RCRUT_NOPE;
        this.planExpryNocs=dto.PLAN_EXPRY_NOCS;
        this.cndeptTrn02Nope=dto.CNDEPT_TRN02_NOPE;
        this.cndeptTrn02AcmlNope=dto.CNDEPT_TRN02_ACML_NOPE;
    }
}
