package org.mangyuancoding.event.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.dreamlu.mica.core.utils.$;
import org.mangyuancoding.event.support.AmqpChannelConstants;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description
 * 事件配置
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/23
 */
@Setter
@Getter
public class EventProperties {

    /**
     * 客户端配置
     */
    private Client client;
    /**
     * 服务端配置
     */
    private Server server;


    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Server {
        /**
         * 服务端地址
         */
        private String host;
        /**
         * 服务端端口号
         */
        private Long port;

        public String buildRegisterUrl() {
            return "http://" + host + ":" + port + AmqpChannelConstants.SUBSCRIBE_URL;
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Client {
        /**
         * 是否订阅消息
         */
        private boolean subscribe = false;
        /**
         * 订阅者的名字
         */
        private String subscribeName;
        /**
         * 要监听的消息
         */
        private Map<String, Subscribe> subscribes;

        public RegisterParam buildRegisterParam() {
            RegisterParam registerParam = new RegisterParam();
            registerParam.setName(subscribeName);
            registerParam.setExchange(subscribeName + AmqpChannelConstants.CLIENT_RECEIVE_EXCHANGE);
            registerParam.setRoutingKey(AmqpChannelConstants.CLIENT_RECEIVE_ROUTING_KEY);

            if ($.isEmpty(this.subscribes)) {
                throw new IllegalArgumentException("请填写要订阅的事件类型");
            }
            Set<RegisterParam.Event> events = new HashSet<>();
            for (Subscribe subscribe : this.subscribes.values()) {
                events.add(new RegisterParam.Event(subscribe));
            }
            registerParam.setEvents(events);
            return registerParam;
        }

        @Setter
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Subscribe {
            /**
             * 消息类型
             */
            private String messageType;
            /**
             * 开始收集的时间
             */
            private Long startTime;
        }

        @Setter
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class RegisterParam {

            private String name;

            private String exchange;

            private String routingKey = "#";

            private Set<Event> events;

            @Setter
            @Getter
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Event {

                private String eventType;

                private Long eventStartTime;

                public Event(Subscribe subscribe) {
                    this.eventType = subscribe.messageType;
                    this.eventStartTime = subscribe.startTime;
                }
            }
        }
    }
}
