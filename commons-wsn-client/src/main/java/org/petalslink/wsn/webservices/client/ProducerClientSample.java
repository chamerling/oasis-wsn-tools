/**
 * 
 */
package org.petalslink.wsn.webservices.client;

import javax.xml.namespace.QName;

import org.petalslink.dsb.notification.client.http.simple.HTTPProducerClient;
import org.petalslink.dsb.notification.commons.NotificationException;

import com.ebmwebsourcing.wsstar.basefaults.datatypes.impl.impl.WsrfbfModelFactoryImpl;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.impl.impl.WsnbModelFactoryImpl;
import com.ebmwebsourcing.wsstar.resource.datatypes.impl.impl.WsrfrModelFactoryImpl;
import com.ebmwebsourcing.wsstar.resourcelifetime.datatypes.impl.impl.WsrfrlModelFactoryImpl;
import com.ebmwebsourcing.wsstar.resourceproperties.datatypes.impl.impl.WsrfrpModelFactoryImpl;
import com.ebmwebsourcing.wsstar.topics.datatypes.impl.impl.WstopModelFactoryImpl;
import com.ebmwebsourcing.wsstar.wsnb.services.impl.util.Wsnb4ServUtils;

/**
 * @author chamerling
 * 
 */
public class ProducerClientSample {

    static {
        Wsnb4ServUtils.initModelFactories(new WsrfbfModelFactoryImpl(),
                new WsrfrModelFactoryImpl(), new WsrfrlModelFactoryImpl(),
                new WsrfrpModelFactoryImpl(), new WstopModelFactoryImpl(),
                new WsnbModelFactoryImpl());
    }

    public static void main(String[] args) {
        String endpoint = "http://localhost:8080/wsnservices/dom/NotificationProducerService";
        HTTPProducerClient client = new HTTPProducerClient(endpoint);
        String namespaceURI = "http://foo/bar";
        String localPart = "DSBTopic";
        String prefix = "dsb";
        QName topic = new QName(namespaceURI, localPart, prefix);

        String iWillReceiveNotifications = "http://localhost:8080/whatyouwant";
        try {
            String subscriptionID = client.subscribe(topic, iWillReceiveNotifications);
            System.out.printf("My subscription ID is %s", subscriptionID);
        } catch (NotificationException e) {
            e.printStackTrace();
        }
    }

}
