/*
 * Copyright 2018 Broadband Forum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadband_forum.obbaa.netconf.stack.api;

import static org.junit.Assert.assertEquals;

import org.broadband_forum.obbaa.netconf.stack.DefaultNcNotificationCounterService;
import org.broadband_forum.obbaa.netconf.stack.NcNotificationCounterService;
import org.junit.Before;
import org.junit.Test;

public class DefaultNcNotificationCounterServiceTest {
    NcNotificationCounterService m_counterService ;

    @Before
    public void setUp(){
        m_counterService = new DefaultNcNotificationCounterService();
    }

    @Test
    public void testCountersAreIncreasing(){
        assertEquals(0L, m_counterService.getNumberOfNotifications());
        m_counterService.increaseNumberOfNotificationsForUsers("adminuser");
        assertEquals(1L, m_counterService.getNumberOfNotifications());
        m_counterService.increaseNumberOfNotificationsForUsers("slice-owner1");
        assertEquals(2L, m_counterService.getNumberOfNotifications());

        assertEquals(0L, m_counterService.getOutNotifications(10));
        m_counterService.increaseOutNotifications(10);
        assertEquals(1L, m_counterService.getOutNotifications(10));
        m_counterService.increaseOutNotifications(10);
        assertEquals(2L, m_counterService.getOutNotifications(10));

        assertEquals(0L, m_counterService.getOutNotifications(20));
        m_counterService.increaseOutNotifications(20);
        assertEquals(1L, m_counterService.getOutNotifications(20));
        m_counterService.increaseOutNotifications(20);
        assertEquals(2L, m_counterService.getOutNotifications(20));
    }
}
