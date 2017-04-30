package org.jary.routes;

import org.apache.camel.spring.SpringRouteBuilder;

/***
 *
 *
 * @author jary
 * @since Apr/30/2017
 */
public class ProcessOrderRoute extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {

        from("amq:incomingOrders").routeId("processOrderRoute")
                .choice()
                    .when().xpath("/order/customer/country = 'UK'")
                        .log("sending order ${file:name} to the UK")
                    .when().xpath("/order/customer/country = 'US'")
                        .log("sending order ${file:name} to the US")
                    .otherwise()
                        .log("sending order ${file:name} to another country")
                .end()
                .log("done processing ${file:name}");
    }
}

/*

<route id="jms-cbr-route" streamCache="true">
    <from id="route-from-incoming-orders" uri="amq:incomingOrders"/>
    <choice id="route-choose-country">
        <when id="route-when-uk">
            <xpath>/order/customer/country = 'UK'</xpath>
            <log id="route-log-uk" message="Sending order ${file:name} to the UK"/>
            <!-- Put additional routing rules for UK messages here -->
        </when>
        <when id="route-when-us">
            <xpath>/order/customer/country = 'US'</xpath>
            <log id="route-log-us" message="Sending order ${file:name} to the US"/>
            <!-- Put additional routing rules for US messages here -->
        </when>
        <otherwise id="route-when-other-country">
            <log id="route-log-other" message="Sending order ${file:name} to another country"/>
            <!-- Put additional routing rules for other messages here -->
        </otherwise>
    </choice>
    <log id="route-final-log" message="Done processing ${file:name}"/>
</route>

 */
