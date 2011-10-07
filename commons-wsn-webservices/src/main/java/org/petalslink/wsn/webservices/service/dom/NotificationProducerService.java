/**
 * 
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
        try {
            in = SOAPMessageUtils.getBodyFromMessage(request);
        } catch (SOAPException e1) {
            handleFault(e1);
        }

        QName operation = getOperation();
        if (operation == null) {
            handleFault(new Exception("Operation is null"));
        }

        if ("Subscribe".equals(operation.getLocalPart())) {
            try {
                Subscribe subscribe = RefinedWsnbFactory.getInstance().getWsnbReader()
                        .readSubscribe(in);
                SubscribeResponse res = NotificationProducerImpl.getInstance().subscribe(subscribe);

                if (res != null) {
                    Document docResp = RefinedWsnbFactory.getInstance().getWsnbWriter()
                            .writeSubscribeResponseAsDOM(res);
                    // TODO : need to write the response...
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
                    // TODO : Write out
                }

            } catch (Exception e) {
                handleFault(e);
            }
        } else {
            throw new RuntimeException("Bad operation " + operation);
        }

        // TODO
        return null;
    }

    /**
     * @param e1
     */
    private void handleFault(Exception e) {
        throw new RuntimeException(e);
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
