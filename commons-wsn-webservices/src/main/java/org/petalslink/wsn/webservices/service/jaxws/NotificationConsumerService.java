/**
 * 
 */
package org.petalslink.wsn.webservices.service.jaxws;

import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.bw_2.NotificationConsumer;

/**
 * @author chamerling
 * 
 */
public class NotificationConsumerService implements NotificationConsumer {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.oasis_open.docs.wsn.bw_2.NotificationConsumer#notify(org.oasis_open
     * .docs.wsn.b_2.Notify)
     */
    public void notify(Notify notify) {
        System.out.println("Got a notify");
    }

}
