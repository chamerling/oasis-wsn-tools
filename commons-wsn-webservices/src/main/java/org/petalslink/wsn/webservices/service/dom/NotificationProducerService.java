/*
 * Copyright 2011 Christophe Hamerling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.petalslink.wsn.webservices.service.dom;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;

import org.petalslink.dsb.saaj.utils.SOAPMessageUtils;
import org.petalslink.wsn.webservices.service.NotificationProducerImpl;
import org.w3c.dom.Document;

import com.ebmwebsourcing.wsstar.basefaults.datatypes.impl.impl.WsrfbfModelFactoryImpl;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.GetCurrentMessage;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.GetCurrentMessageResponse;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.Subscribe;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.SubscribeResponse;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.refinedabstraction.RefinedWsnbFactory;
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
@WebServiceProvider(wsdlLocation = "WS-NotificationProducer.wsdl", serviceName = "NotificationProducerService", portName = "NotificationProducerPort", targetNamespace = "http://docs.oasis-open.org/wsn/bw-2")
@ServiceMode(value = javax.xml.ws.Service.Mode.MESSAGE)
public class NotificationProducerService implements Provider<SOAPMessage> {

    static {
        Wsnb4ServUtils.initModelFactories(new WsrfbfModelFactoryImpl(),
                new WsrfrModelFactoryImpl(), new WsrfrlModelFactoryImpl(),
                new WsrfrpModelFactoryImpl(), new WstopModelFactoryImpl(),
                new WsnbModelFactoryImpl());
    }

    @Resource
    WebServiceContext wsContext;

    /*
     * (non-Javadoc)
     * 
     * @see javax.xml.ws.Provider#invoke(java.lang.Object)
     */
    public SOAPMessage invoke(SOAPMessage request) {
        Document in = null;
        SOAPMessage result = null;
        try {
            in = SOAPMessageUtils.getBodyFromMessage(request);
        } catch (SOAPException e1) {
            handleFault(e1);
        }

        QName operation = getOperation();
        if (operation == null) {
            handleFault("Operation is null");
        }

        if ("Subscribe".equals(operation.getLocalPart())) {
            try {
                Subscribe subscribe = RefinedWsnbFactory.getInstance().getWsnbReader()
                        .readSubscribe(in);
                SubscribeResponse res = NotificationProducerImpl.getInstance().subscribe(subscribe);

                if (res != null) {
                    Document docResp = RefinedWsnbFactory.getInstance().getWsnbWriter()
                            .writeSubscribeResponseAsDOM(res);
                    result = SOAPMessageUtils.createSOAPMessageFromBodyContent(docResp);
                }
            } catch (Exception e) {
                handleFault(e);
            }

        } else if ("GetCurrentMessage".equals(operation.getLocalPart())) {
            try {
                GetCurrentMessage getCurrentMessage = RefinedWsnbFactory.getInstance()
                        .getWsnbReader().readGetCurrentMessage(in);
                GetCurrentMessageResponse res = NotificationProducerImpl.getInstance()
                        .getCurrentMessage(getCurrentMessage);

                if (res != null) {
                    Document docResp = RefinedWsnbFactory.getInstance().getWsnbWriter()
                            .writeGetCurrentMessageResponseAsDOM(res);
                    result = SOAPMessageUtils.createSOAPMessageFromBodyContent(docResp);
                }

            } catch (Exception e) {
                handleFault(e);
            }
        } else {
            handleFault("Bad operation " + operation);
        }
        return result;
    }

    /**
     * @param e1
     */
    private void handleFault(Exception e) {
        throw new RuntimeException(e);
    }

    private void handleFault(String message) {
        throw new RuntimeException(new Exception(message));
    }

    private QName getOperation() {
        QName result = null;
        if (wsContext != null && wsContext.getMessageContext() != null) {
            Object o = wsContext.getMessageContext().get(MessageContext.WSDL_OPERATION);
            if (o != null && o instanceof QName) {
                result = (QName) o;
            }
        }
        return result;
    }

}
