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

package org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.emn;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.broadband_forum.obbaa.netconf.api.messages.InsertOperation;
import org.broadband_forum.obbaa.netconf.mn.fwk.schema.SchemaRegistry;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.ModelNodeId;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.datastore.DataStoreException;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.datastore.ModelNodeDataStoreManager;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.datastore.ModelNodeKey;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.GenericConfigAttribute;
import org.broadband_forum.obbaa.netconf.server.RequestScopeJunitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.model.api.LeafListSchemaNode;
import org.opendaylight.yangtools.yang.model.api.SchemaPath;

@RunWith(RequestScopeJunitRunner.class)
public class XmlChildLeafListHelperTest {
    XmlChildLeafListHelper m_helper;
    @Mock
    private LeafListSchemaNode m_schemaNode;
    public static final String LEAF_LIST = "leaf-list";
    private static final QName QNAME = QName.create("namespace", LEAF_LIST);
    @Mock
    private ModelNodeDataStoreManager m_dsm;
    @Mock
    private SchemaRegistry m_schemaRegistry;
    @Mock
    private XmlModelNodeImpl m_modelNode;
    private static final SchemaPath m_schemaPath = SchemaPath.create(true, QNAME);
    @Mock
    private ModelNodeId m_modelNodeId;
    @Mock
    private ModelNodeId m_parentNodeId;

    @Before
    public void setUp() throws DataStoreException {
        MockitoAnnotations.initMocks(this);
        m_helper = new XmlChildLeafListHelper(m_schemaNode, QNAME, m_dsm, m_schemaRegistry);
        when(m_dsm.findNode((SchemaPath) anyObject(), (ModelNodeKey)anyObject(), (ModelNodeId) anyObject(), (SchemaRegistry) anyObject())).thenReturn(m_modelNode);
        when(m_modelNode.getModelNodeSchemaPath()).thenReturn(m_schemaPath);
        when(m_modelNode.getModelNodeId()).thenReturn(m_modelNodeId);
        when(m_modelNode.getParentNodeId()).thenReturn(m_parentNodeId);
        when(m_modelNode.getSchemaRegistry()).thenReturn(m_schemaRegistry);
    }

    @Test
    public void testAddChildByUserOrder() throws Exception{
        when(m_schemaNode.isUserOrdered()).thenReturn(true);
        m_helper.addChildByUserOrder(m_modelNode, new GenericConfigAttribute(LEAF_LIST, "namespace", "value"), "", InsertOperation.FIRST_OP);
        verify(m_dsm).findNode(eq(m_schemaPath),(ModelNodeKey)anyObject(), eq(m_parentNodeId), (SchemaRegistry) anyObject());
    }

    @Test
    public void testRemoveChild() throws Exception{
        m_helper.removeChild(m_modelNode, new GenericConfigAttribute(LEAF_LIST, "namespace", "value"));
        verify(m_dsm).findNode(eq(m_schemaPath),(ModelNodeKey)anyObject(), eq(m_parentNodeId), (SchemaRegistry) anyObject());
    }

}
