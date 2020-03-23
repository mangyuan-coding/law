package org.mangyuancoding.event.support;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/23
 */
public class AmqpChannelConstants {

    public static final String SERVER_RECEIVE_EXCHANGE = "event-server";
    public static final String SERVER_RECEIVE_QUEUE = "event-server.queue";
    public static final String SERVER_RECEIVE_ROUTING_KEY = "#";
    public static final String SUBSCRIBE_URL = "/subscribe";
    public static final String CLIENT_RECEIVE_EXCHANGE = ".event-client";
    public static final String CLIENT_RECEIVE_QUEUE = ".event-client.queue";
    public static final String CLIENT_RECEIVE_ROUTING_KEY = "#";
}
