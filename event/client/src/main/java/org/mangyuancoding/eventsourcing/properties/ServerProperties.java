package org.mangyuancoding.eventsourcing.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/18
 */

public class ServerProperties {

    private String host;

    private String port;


    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
