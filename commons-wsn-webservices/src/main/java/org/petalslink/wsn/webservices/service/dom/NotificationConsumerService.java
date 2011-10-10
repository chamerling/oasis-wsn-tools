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
import org.petalslink.wsn.webservices.service.NotificationConsumerImpl;
import org.w3c.dom.Document;

import com.ebmwebsourcing.wsstar.basefaults.datatypes.impl.impl.WsrfbfModelFactoryImpl;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.abstraction.Notify;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.refinedabstraction.RefinedWsnbFactory;
import com.ebmwebsourcing.wsstar.basenotification.datatypes.api.utils.WsnbException;
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
@WebServiceProvider(wsdlLocation = "WS-NotificationConsumer.wsdl", serviceName = "NotificationConsumerService", portName = "NotificationConsumerPort", targetNamespace = "http://docs.oasis-open.org/wsn/bw-2")
@ServiceMode(value = javax.xml.ws.Service.Mode.MESSAGE)
public class NotificationConsumerService implements Provider<SOAPMessage> {

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
        QName operation = getOperation();
        Document in = null;
        try {
            in = SOAPMessageUtils.getBodyFromMessage(request);
        } catch (SOAPException e) {
            handleFault(e);
        }

        if ("Notify".equals(operation.getLocalPart())) {
            try {
                Notify notify = RefinedWsnbFactory.getInstance().getWsnbReader().readNotify(in);
                NotificationConsumerImpl.getInstance().notify(notify);
            } catch (WsnbException e) {
                handleFault(e);
            }
        } else {
            handleFault("Bad operation " + operation);
        }

        // does not return anything, must be a inonly operation!
        // the WS stack may detect the in only operation and does not care about
        // this result (true with CXF)
        try {
            return SOAPMessageUtils.createSOAPMessageFromBodyContent(null);
        } catch (SOAPException e) {
            handleFault(e);
        }
        return null;
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

    private void handleFault(Exception e) {
        throw new RuntimeException(e);
    }

    /**
     * @param string
     */
    private void handleFault(String string) {
        throw new RuntimeException(string);

    }
}
