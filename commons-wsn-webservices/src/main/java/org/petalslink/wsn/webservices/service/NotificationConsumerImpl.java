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

import java.util.List;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ebmwebsourcing.easycommons.xml.XMLHelper;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.NotificationMessageHolderType;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.NotificationMessageHolderType.Message;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.Notify;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.TopicExpressionType;
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
        System.out
                .println("Notify, please implement me at org.petalslink.wsn.webservices.service.NotificationConsumerImpl");

        // get the DOM from the bean
        Document dom = Wsnb4ServUtils.getWsnbWriter().writeNotifyAsDOM(notify);
        try {
            System.out.println(XMLHelper.createStringFromDOMDocument(dom));
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        // get the topic
        List<NotificationMessageHolderType> messages = notify.getNotificationMessage();
        for (NotificationMessageHolderType notificationMessageHolderType : messages) {
            TopicExpressionType targetTopic = notificationMessageHolderType.getTopic();
            // topic content is something like 'prefix:Name'
            System.out.println("Target topic : " + targetTopic.getContent());
            // you must get the prefix and the NS from the topicNamespaces
            System.out.println(targetTopic.getTopicNamespaces());

            // get the business message
            Message message = notificationMessageHolderType.getMessage();
            System.out.println("The business message : ");
            Element businessMessage = message.getAny();
            try {
                System.out.println(XMLHelper.createStringFromDOMNode(businessMessage));
            } catch (TransformerException e) {
                e.printStackTrace();
            }

        }

    }

}
