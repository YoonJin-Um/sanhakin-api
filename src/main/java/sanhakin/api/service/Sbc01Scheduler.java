
package sanhakin.api.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class Sbc01Scheduler {

    @Autowired
    private Sbc01BatchService batchService;

    @Scheduled(cron="0 0 * * * *")
    public void runHourly(){
        System.out.println("[Scheduler] SBC01 Batch Start");
        batchService.execute();
        System.out.println("[Scheduler] SBC01 Batch End");
    }
}
