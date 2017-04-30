package org.jary.routes;

import org.apache.camel.spring.SpringRouteBuilder;
import org.jary.OrderGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 *
 *
 * @author jary
 * @since Apr/30/2017
 */
@Component
public class GenerateOrderRoute extends SpringRouteBuilder {

    @Autowired
    private OrderGenerator orderGenerator;

    @Override
    public void configure() throws Exception {

        from("timer:order?period=3000").routeId("route-timer")
                .to("bean:orderGenerator?method=generateOrder")
                .setHeader("Exchange.FILE_NAME").method(orderGenerator, "generateFileName")
                .log("generating order ${file:name}")
                .to("amq:incomingOrders");
    }
}

/*
    <route id="generate-order-route" streamCache="true">
            <from id="route-timer" uri="timer:order?period=3000"/>
            <bean id="route-new-order" method="generateOrder" ref="orderGenerator"/>
            <setHeader headerName="Exchange.FILE_NAME" id="route-set-order-header">
                <!-- defining the header containing a simulated file name -->
                <method method="generateFileName" ref="orderGenerator"/>
            </setHeader>
            <log id="route-log-order" message="Generating order ${file:name}"/>
            <to id="route-to-incoming-orders" uri="amq:incomingOrders"/>
        </route>
*/
