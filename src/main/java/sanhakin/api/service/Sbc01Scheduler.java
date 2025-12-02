package sanhakin.api.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Sbc01Scheduler {
	
    private final Sbc01BatchService batchService;
    private static final Logger logger = LoggerFactory.getLogger(Sbc01BatchService.class);

    public Sbc01Scheduler(Sbc01BatchService batchService){
        this.batchService = batchService;
    }

    @Scheduled(cron="0 */10 * * * *")
    public void runHourly() {
    	
    	long start = System.currentTimeMillis();
    	logger.info("[SBC01] START at {}", LocalDateTime.now());

        batchService.execute();

        long end = System.currentTimeMillis();
        logger.info("[SBC01] END at {} (duration: {} ms)", LocalDateTime.now(), end - start);
    }
 
}
