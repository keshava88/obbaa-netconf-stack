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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.broadband_forum.obbaa.netconf.mn.fwk.schema.SchemaRegistry;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.ModelNodeId;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.datastore.ModelNodeDataStoreManager;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.ConfigLeafAttribute;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.ModelNodeWithAttributes;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.model.api.SchemaPath;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Responsible for conversion of a given Xml String into XmlModelNodeImpl and vice versa.
 * Does lazy conversion (Only given node attributes are set, children are lazily initialised upon XmlModelNodeImpl#getChildren() call).
 *
 */
public interface XmlModelNodeToXmlMapper {

    static boolean nodesMatch(Node nodeXml, QName nodeQName) {
        if(nodeXml == null){
            return false;
        }
        String childElementLocalName = nodeXml.getLocalName();
        String childElementNamespace = nodeXml.getNamespaceURI();
        if (nodeQName.getLocalName().equals(childElementLocalName) && nodeQName.getNamespace().toString().equals(childElementNamespace)) {
            return true;
        }
        return false;
    }

    XmlModelNodeImpl getModelNodeFromParentSchemaPath(Element nodeXml, SchemaPath parentSchemaPath, ModelNodeId parentId, XmlModelNodeImpl parentModelNode, ModelNodeDataStoreManager modelNodeDsm, Object storedParentEntity, SchemaPath storedParentSchemaPath);

    List<XmlModelNodeImpl> getModelNodeFromNodeSchemaPath(boolean xmlLoaded, Element nodeXml, Map<QName, ConfigLeafAttribute> configAttrsFromEntity, Map<QName, LinkedHashSet<ConfigLeafAttribute>> configLeafListsFromEntity, SchemaPath nodeSchemaPath, ModelNodeId parentId, XmlModelNodeImpl parentModelNode, ModelNodeDataStoreManager modelNodeDsm, Object storedParentEntity, SchemaPath storedParentSchemaPath);

    XmlModelNodeImpl getModelNode(Object entity, ModelNodeDataStoreManager modelNodeDSM);

    Element getXmlValue(XmlModelNodeImpl xmlModelNode);

    XmlModelNodeImpl getRootXmlModelNode(ModelNodeWithAttributes modelNode, ModelNodeDataStoreManager dsm);

    List<Element> loadXmlValue(Object storedParentEntity, SchemaPath schemaPath, SchemaRegistry schemaRegistry, SchemaPath storedParentSchemaPath);
}
