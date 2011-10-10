/**
 * 
 */
package org.petalslink.wsn.webservices.client;

import javax.xml.namespace.QName;

import org.petalslink.dsb.notification.client.http.simple.HTTPConsumerClient;
import org.petalslink.dsb.notification.commons.NotificationException;
import org.w3c.dom.Document;

import com.ebmwebsourcing.easycommons.xml.XMLHelper;
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
public class ConsumerClientSample {

    static {
        Wsnb4ServUtils.initModelFactories(new WsrfbfModelFactoryImpl(),
                new WsrfrModelFactoryImpl(), new WsrfrlModelFactoryImpl(),
                new WsrfrpModelFactoryImpl(), new WstopModelFactoryImpl(),
                new WsnbModelFactoryImpl());
    }

    public static void main(String[] args) {
        String endpoint = "http://localhost:8080/wsnservices/dom/NotificationConsumerService";
        HTTPConsumerClient client = new HTTPConsumerClient(endpoint);
        Document document = null;
        try {
            // this is just the business message content, the client will wrap
            // it like required...
            document = XMLHelper.createDocumentFromString("<foo>bar</foo>");
        } catch (Exception e1) {
        }
        String namespaceURI = "http://foo/bar";
        String localPart = "DSBTopic";
        String prefix = "dsb";
        QName topic = new QName(namespaceURI, localPart, prefix);
        try {
            client.notify(document, topic);
        } catch (NotificationException e) {
            e.printStackTrace();
        }
    }

}
