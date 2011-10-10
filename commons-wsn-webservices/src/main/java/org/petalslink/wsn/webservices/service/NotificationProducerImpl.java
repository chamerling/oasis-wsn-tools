/*
 * Copyright 2011 Christophe Hamerling
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.petalslink.wsn.webservices.service;

import java.net.URI;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.petalslink.dsb.notification.commons.SOAUtil;
import org.w3c.dom.Document;

import com.ebmwebsourcing.easycommons.xml.XMLHelper;
import com.ebmwebsourcing.wsaddressing10.api.element.Address;
import com.ebmwebsourcing.wsaddressing10.api.type.EndpointReferenceType;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.GetCurrentMessage;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.GetCurrentMessageResponse;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.NotificationMessageHolderType.Message;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.Subscribe;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.SubscribeResponse;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.TopicExpressionType;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.refinedabstraction.RefinedWsnbFactory;
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
        System.out
                .println("getCurrentMessage, please implement me at org.petalslink.wsn.webservices.service.NotificationProducerImpl!");

        Message message = RefinedWsnbFactory.getInstance()
                .createNotificationMessageHolderTypeMessage(null);
        return RefinedWsnbFactory.getInstance().createGetCurrentMessageResponse(message);
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
        System.out
                .println("subscribe, please implement me at org.petalslink.wsn.webservices.service.NotificationProducerImpl!");

        Document dom = Wsnb4ServUtils.getWsnbWriter().writeSubscribeAsDOM(subscribe);

        try {
            System.out.println(XMLHelper.createStringFromDOMDocument(dom));
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        // Topic is in the subscribe filter
        List<TopicExpressionType> topics = subscribe.getFilter().getTopicExpressions();
        for (TopicExpressionType topicExpressionType : topics) {
            System.out.println("Topic : " + topicExpressionType.getContent());
            // get the NS from the list
            System.out.println("NS : " + topicExpressionType.getTopicNamespaces());
        }

        // create a subscribe response, pushing a registration ID will be
        // nice...
        final EndpointReferenceType registrationRef = SOAUtil.getInstance().getXmlObjectFactory()
                .create(EndpointReferenceType.class);
        Address address = SOAUtil.getInstance().getXmlObjectFactory().create(Address.class);
        address.setValue(URI.create("http://localhost:99998/foo/bar"));
        registrationRef.setAddress(address);
        return RefinedWsnbFactory.getInstance().createSubscribeResponse(registrationRef);
    }

}
