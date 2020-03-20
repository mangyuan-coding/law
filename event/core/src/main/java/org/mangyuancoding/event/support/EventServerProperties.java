package org.mangyuancoding.event.support;

/**
 * Description
 * 事件服务器配置
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/18
 */
public class EventServerProperties {

    private static final String DEFAULT_EXCHANGE = "event-server";
    public static final String DEFAULT_QUEUE = "event-server.queue";
    private static final String DEFAULT_ROUTING_KEY = "#";

    /**
     * 交换机
     */
    private String exchange = DEFAULT_EXCHANGE;
    /**
     * 队列
     */
    private String queue = DEFAULT_QUEUE;
    /**
     * 消息上的路由key
     */
    private String routingKey = DEFAULT_ROUTING_KEY;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }
}
