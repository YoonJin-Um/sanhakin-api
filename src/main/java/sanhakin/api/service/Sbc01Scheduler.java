package sanhakin.api.service;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Sbc01Scheduler {

    private final Sbc01BatchService batchService;

    public Sbc01Scheduler(Sbc01BatchService batchService){
        this.batchService = batchService;
    }

    @Scheduled(cron="0 * * * * *")
    public void runHourly() {
        System.out.println("[Scheduler] SBC01 Batch Start");
        batchService.execute();
        System.out.println("[Scheduler] SBC01 Batch End");
    }
    
    @PostConstruct
    public void runOnStartup() {
        System.out.println("[Startup] SBC01 Batch Start");
        runHourly();
        System.out.println("[Startup] SBC01 Batch End");
    }    
}
