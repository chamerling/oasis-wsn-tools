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
