/**
 * 
 */
package org.petalslink.research.commons.wsn.api;

import javax.xml.namespace.QName;

import org.oasis_open.docs.wsn.b_2.GetCurrentMessage;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessageResponse;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeResponse;
import org.oasis_open.docs.wsn.bw_2.InvalidFilterFault;
import org.oasis_open.docs.wsn.bw_2.InvalidMessageContentExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidProducerPropertiesExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault;
import org.oasis_open.docs.wsn.bw_2.MultipleTopicsSpecifiedFault;
import org.oasis_open.docs.wsn.bw_2.NoCurrentMessageOnTopicFault;
import org.oasis_open.docs.wsn.bw_2.NotifyMessageNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.SubscribeCreationFailedFault;
import org.oasis_open.docs.wsn.bw_2.TopicExpressionDialectUnknownFault;
import org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault;
import org.oasis_open.docs.wsn.bw_2.UnrecognizedPolicyRequestFault;
import org.oasis_open.docs.wsn.bw_2.UnsupportedPolicyRequestFault;
import org.oasis_open.docs.wsrf.rp_2.GetResourcePropertyResponse;
import org.oasis_open.docs.wsrf.rpw_2.InvalidResourcePropertyQNameFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnavailableFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

import com.petalslink.wsn.service.wsnproducer.NotificationProducer;

/**
 * @author chamerling
 *
 */
public class Producer implements NotificationProducer {

    /* (non-Javadoc)
     * @see com.petalslink.wsn.service.wsnproducer.NotificationProducer#getCurrentMessage(org.oasis_open.docs.wsn.b_2.GetCurrentMessage)
     */
    public GetCurrentMessageResponse getCurrentMessage(GetCurrentMessage getCurrentMessageRequest)
            throws TopicNotSupportedFault, InvalidTopicExpressionFault, ResourceUnknownFault,
            TopicExpressionDialectUnknownFault, NoCurrentMessageOnTopicFault,
            MultipleTopicsSpecifiedFault {
        System.out.println("Got a getCurrentMessage");
        return null;
    }

    /* (non-Javadoc)
     * @see com.petalslink.wsn.service.wsnproducer.NotificationProducer#subscribe(org.oasis_open.docs.wsn.b_2.Subscribe)
     */
    public SubscribeResponse subscribe(Subscribe subscribeRequest)
            throws NotifyMessageNotSupportedFault, TopicNotSupportedFault, InvalidFilterFault,
            InvalidTopicExpressionFault, ResourceUnknownFault, TopicExpressionDialectUnknownFault,
            UnrecognizedPolicyRequestFault, SubscribeCreationFailedFault,
            InvalidMessageContentExpressionFault, UnsupportedPolicyRequestFault,
            UnacceptableInitialTerminationTimeFault, InvalidProducerPropertiesExpressionFault {
        System.out.println("Got a subscribe");

        return null;
    }

    /* (non-Javadoc)
     * @see com.petalslink.wsn.service.wsnproducer.NotificationProducer#getResourceProperty(javax.xml.namespace.QName)
     */
    public GetResourcePropertyResponse getResourceProperty(QName getResourcePropertyRequest)
            throws ResourceUnknownFault, InvalidResourcePropertyQNameFault,
            ResourceUnavailableFault {
        System.out.println("Got a getResourceProperty");
        
        return null;
    }

}
