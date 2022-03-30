package com.effecti.configuration;

import com.effecti.service.BiddingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Configuration
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    private BiddingRequestService biddingRequestService;

    // Create a new JSESSIONID every fixed time interval. See the value in the properties.
    @Scheduled(fixedRateString = "${interval-to-update-jsessionid}")
    public void updateCookie() {
        try {
            biddingRequestService.updateJsessionid();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
