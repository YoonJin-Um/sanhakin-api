package sanhakin.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
public class Sbc01StartupRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(Sbc01StartupRunner.class);

    private final Sbc01BatchService batchService;

    // 🔹 생성자 주입 추가 (필수)
    public Sbc01StartupRunner(Sbc01BatchService batchService) {
        this.batchService = batchService;
    }

    @Override
    public void run(ApplicationArguments args) {
        long start = System.currentTimeMillis();
        logger.info("[StartupRunner] SBC01 Batch Start");

        batchService.execute();

        long end = System.currentTimeMillis();
        logger.info("[StartupRunner] SBC01 Batch End (duration: {} ms)", (end - start));
    }
}
