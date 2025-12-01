package sanhakin.api.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Sbc01Scheduler {
	
    private final Sbc01BatchService batchService;
    private static final Logger logger = LoggerFactory.getLogger(Sbc01BatchService.class);

    public Sbc01Scheduler(Sbc01BatchService batchService){
        this.batchService = batchService;
    }

//    @Scheduled(cron="0 0 * * * *")
    public void runHourly() {
    	
    	long start = System.currentTimeMillis();
    	logger.info("[SBC01] START at {}", LocalDateTime.now());
;
        batchService.execute();

        long end = System.currentTimeMillis();
        logger.info("[SBC01] END at {} (duration: {} ms)", LocalDateTime.now(), end - start);
    }
 
}
