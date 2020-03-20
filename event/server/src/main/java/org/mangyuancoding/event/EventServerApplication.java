package org.mangyuancoding.event;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class EventServerApplication {
    public static void main(String[] args) {
        run(EventServerApplication.class, args);
    }
}
