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

package org.broadband_forum.obbaa.netconf.api.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.broadband_forum.obbaa.netconf.api.util.TestXML.assertXMLEquals;
import static org.broadband_forum.obbaa.netconf.api.util.TestXML.loadAsXml;
import java.io.IOException;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.broadband_forum.obbaa.netconf.api.util.NetconfMessageBuilderException;

public class LockRequestTest extends RpcTypeTest {

    private LockRequest m_lockRequest = new LockRequest();
    private static final String TEST_TARGET = "candidate";
    private String m_target = TEST_TARGET;
    private String m_messageId = "101";

    public LockRequestTest() {
        super(new LockRequest().setTargetRunning());
    }

    @Test
    public void testGetRequestDocument() throws NetconfMessageBuilderException, SAXException, IOException {

        m_lockRequest.setMessageId(m_messageId);
        m_lockRequest.setTarget(m_target);
        assertNotNull(m_lockRequest.getRequestDocument());
        assertXMLEquals(loadAsXml("lock.xml"), m_lockRequest.getRequestDocument().getDocumentElement());

    }

    @Test
    public void testSetAndGetTarget() {

        assertEquals(m_lockRequest, m_lockRequest.setTarget(m_target));
        assertEquals(TEST_TARGET, m_lockRequest.getTarget());
    }

    @Test
    public void testSetTargetRunning() {
        LockRequest lockRequest1 = m_lockRequest.setTargetRunning();
        assertEquals("running", lockRequest1.getTarget());

    }

    @Test
    public void testToString() {

        assertEquals("LockRequest [target=null]", m_lockRequest.toString());
        assertNotNull(m_lockRequest.toString());
    }

}
