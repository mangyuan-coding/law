package org.mangyuancoding.event.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
    private RestTemplate restTemplate;

    public SubscribeEventRunner(EventProperties eventProperties, RestTemplate restTemplate) {
        this.eventProperties = eventProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (eventProperties.getClient().isSubscribe()) {
            String response = restTemplate.postForObject(eventProperties.getServer().buildRegisterUrl(),
                    eventProperties.getClient().buildRegisterParam(),
                    String.class);
            log.info("订阅服务消息成功：" + response);
        }
    }

}
