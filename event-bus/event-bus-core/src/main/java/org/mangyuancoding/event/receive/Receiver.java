package org.mangyuancoding.event.receive;

import org.mangyuancoding.event.support.Registration;

/**
 * Description
 * 接收器
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/19
 */
public interface Receiver {

    /**
     * 注册监听器
     *
     * @param eventListener 事件监听器
     * @return 票根
     */
    Registration register(EventListener eventListener);
}
