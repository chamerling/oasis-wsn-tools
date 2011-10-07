/**
 * 
 */
package org.petalslink.research.commons.wsn.api;

import java.util.concurrent.TimeUnit;

import org.oasis_open.docs.wsn.bw_2.NotificationConsumer;
import org.petalslink.dsb.commons.service.api.Service;
import org.petalslink.dsb.cxf.CXFHelper;

import com.petalslink.wsn.service.wsnproducer.NotificationProducer;

/**
 * @author chamerling
 * 
 */
public class JAXWSExposerSample {

    public static void main(String[] args) {
        Service service = CXFHelper.getServiceFromFinalURL(
                "http://localhost:8886/cloud/wsn/Consumer", NotificationConsumer.class,
                new Consumer());

        Service service2 = CXFHelper.getServiceFromFinalURL(
                "http://localhost:8886/cloud/wsn/Producer", NotificationProducer.class,
                new Producer());

        service.start();
        service2.start();

        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
