/**
 * 
 */
package org.petalslink.wsn.webservices.service;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import com.ebmwebsourcing.easycommons.xml.XMLHelper;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.GetCurrentMessage;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.GetCurrentMessageResponse;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.Subscribe;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.SubscribeResponse;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.utils.WsnbException;
import com.ebmwebsourcing.wsstar.wsnb.services.INotificationProducer;
import com.ebmwebsourcing.wsstar.wsnb.services.impl.util.Wsnb4ServUtils;
import com.ebmwebsourcing.wsstar.wsrfbf.services.faults.AbsWSStarFault;

/**
 * @author chamerling
 * 
 */
public class NotificationProducerImpl implements INotificationProducer {

    private static INotificationProducer INSTANCE;

    public static final synchronized INotificationProducer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotificationProducerImpl();
        }
        return INSTANCE;
    }

    private NotificationProducerImpl() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ebmwebsourcing.wsstar.wsnb.services.INotificationProducer#
     * getCurrentMessage
     * (com.ebmwebsourcing.wsstar.basenotification.datatypes.api
     * .abstraction.GetCurrentMessage)
     */
    public GetCurrentMessageResponse getCurrentMessage(GetCurrentMessage getCurrentMessage)
            throws WsnbException, AbsWSStarFault {
        System.out.println("getCurrentMessage, please implement me!");
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ebmwebsourcing.wsstar.wsnb.services.INotificationProducer#subscribe
     * (com
     * .ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.Subscribe
     * )
     */
    public SubscribeResponse subscribe(Subscribe subscribe) throws WsnbException, AbsWSStarFault {
        System.out.println("subscribe, please implement me!");

        Document dom = Wsnb4ServUtils.getWsnbWriter().writeSubscribeAsDOM(subscribe);

        try {
            System.out.println(XMLHelper.createStringFromDOMDocument(dom));
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        // TODO
        return null;
    }

}
