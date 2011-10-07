/**
 * 
 */
package org.petalslink.wsn.webservices.service;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import com.ebmwebsourcing.easycommons.xml.XMLHelper;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.Notify;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.utils.WsnbException;
import com.ebmwebsourcing.wsstar.wsnb.services.INotificationConsumer;
import com.ebmwebsourcing.wsstar.wsnb.services.impl.util.Wsnb4ServUtils;

/**
 * @author chamerling
 * 
 */
public class NotificationConsumerImpl implements INotificationConsumer {

    private static INotificationConsumer INSTANCE;

    public static final synchronized INotificationConsumer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotificationConsumerImpl();
        }
        return INSTANCE;
    }

    private NotificationConsumerImpl() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ebmwebsourcing.wsstar.wsnb.services.INotificationConsumer#notify(
     * com.ebmwebsourcing
     * .wsstar.basenotification.datatypes.api.abstraction.Notify)
     */
    public void notify(Notify notify) throws WsnbException {
        // your code goes here...
        System.out.println("Notify, please implement me!");
        // get the DOM from the bean
        Document dom = Wsnb4ServUtils.getWsnbWriter().writeNotifyAsDOM(notify);

        try {
            System.out.println(XMLHelper.createStringFromDOMDocument(dom));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
