
package sanhakin.api.dto;

import java.util.List;

public class Sbc01Response {
    private List<Sbc01RecordDto> RECORD;
    private Sbc01ResultDto RESULT;

    public List<Sbc01RecordDto> getRECORD() { return RECORD; }
    public void setRECORD(List<Sbc01RecordDto> RECORD) { this.RECORD = RECORD; }

    public Sbc01ResultDto getRESULT() { return RESULT; }
    public void setRESULT(Sbc01ResultDto RESULT) { this.RESULT = RESULT; }
}