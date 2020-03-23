package org.mangyuancoding.event.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/23
 */
@Slf4j
@Order(value = 1)
public class SubscribeEventRunner implements ApplicationRunner {

    private EventProperties eventProperties;
    private ApplicationContext applicationContext;
    private RestTemplate restTemplate;

    public SubscribeEventRunner(EventProperties eventProperties, ApplicationContext applicationContext,
                                RestTemplate restTemplate) {
        this.eventProperties = eventProperties;
        this.applicationContext = applicationContext;
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String response = restTemplate.postForObject(eventProperties.getServer().buildRegisterUrl(),
                eventProperties.getClient().buildRegisterParam(applicationContext.getApplicationName()), String.class);

        log.debug(response);
    }

}
