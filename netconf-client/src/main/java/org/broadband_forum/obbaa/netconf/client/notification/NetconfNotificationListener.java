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

package org.broadband_forum.obbaa.netconf.client.notification;

import org.broadband_forum.obbaa.netconf.api.LogAppNames;
import org.broadband_forum.obbaa.netconf.api.client.NotificationListener;
import org.broadband_forum.obbaa.netconf.api.messages.Notification;
import org.broadband_forum.obbaa.netconf.stack.logging.AdvancedLogger;
import org.broadband_forum.obbaa.netconf.stack.logging.AdvancedLoggerUtil;

public class NetconfNotificationListener implements NotificationListener {
    private static final AdvancedLogger LOGGER = AdvancedLoggerUtil.getGlobalDebugLogger(NetconfNotificationListener.class, LogAppNames.NETCONF_LIB);

    public void notificationReceived(Notification notification) {
        LOGGER.info(" Notification received:" + notification.notificationToString());
    }
}
