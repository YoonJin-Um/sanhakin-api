package sanhakin.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
=======
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
>>>>>>> branch 'main' of https://github.com/YoonJin-Um/sanhakin-api
public class SanhakinSchedulerApplication {
    public static void main(String[] args){
        SpringApplication.run(SanhakinSchedulerApplication.class, args);
    }
}
