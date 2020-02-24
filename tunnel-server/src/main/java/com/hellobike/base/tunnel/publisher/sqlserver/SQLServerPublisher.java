package com.hellobike.base.tunnel.publisher.sqlserver;

import com.hellobike.base.tunnel.config.KafkaConfig;
import com.hellobike.base.tunnel.model.Event;
import com.hellobike.base.tunnel.model.InvokeContext;
import com.hellobike.base.tunnel.publisher.BasePublisher;
import com.hellobike.base.tunnel.publisher.IPublisher;
import com.hellobike.base.tunnel.publisher.kafka.KafkaPublisher;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wissy 2020-02-03
 */
public class SQLServerPublisher extends BasePublisher implements IPublisher {
    private static final Logger                     /**/ log = LoggerFactory.getLogger(SQLServerPublisher.class);
    private final List<SQLServerConfig>                 /**/ sqlServerConfigs;

    public SQLServerPublisher(List<SQLServerConfig>  sqlServerConfigs){
        this.sqlServerConfigs=sqlServerConfigs;
    }

    @Override
    public void publish(Event event, Callback callback) {

    }

    @Override
    public void publish(InvokeContext context, Callback callback) {

    }

    @Override
    public void close() {

    }

    @Override
    public void publish(List<InvokeContext> contexts) {

    }
}
