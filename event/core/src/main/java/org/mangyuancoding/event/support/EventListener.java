package org.mangyuancoding.event.support;

import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.constitution.support.Registration;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/19
 */
public interface EventListener {

    void collectRegistration(Registration registration);

    void on(byte[] payload, MetaData metaData);
}
