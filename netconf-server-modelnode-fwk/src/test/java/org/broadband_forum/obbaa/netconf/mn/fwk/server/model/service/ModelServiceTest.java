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

package org.broadband_forum.obbaa.netconf.mn.fwk.server.model.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.broadband_forum.obbaa.netconf.server.util.TestUtil;
import org.junit.Test;
import org.w3c.dom.Element;

/**
 * Created by kbhatk on 7/12/16.
 */
public class ModelServiceTest {

    @Test
    public void testGetNameUsingModuleNameAndRevision(){
        ModelService service = new ModelService();
        service.setModuleName("my-module");
        service.setModuleRevision("1988-03-27");
        assertEquals("my-module?revision=1988-03-27", service.getName());
    }

	@Test
	public void testGetNameWithoutUsingModuleNameAndRevision(){
		ModelService service = new ModelService();
		service.setName("new-name");
		service.setModuleName("my-module");
		service.setModuleRevision("1988-03-27");
		assertEquals("new-name", service.getName());
	}

	@Test
	public void testGetDefaultXmlDocument() throws Exception {
		ModelService testObj = new ModelService();
		QName name1 = new QName("ns1", "name1");
		QName name2 = new QName("ns1", "name2");
		QName name3 = new QName("ns2", "name3");
		testObj.setRootElemQName(Arrays.asList(name1, name2, name3));
		List<Element> actualConfigElements = testObj.getDefaultSubtreeRootNodes();
		assertNotNull(actualConfigElements);
		assertEquals(3,actualConfigElements.size());

		List<String> expectedConfigElements = new ArrayList<>();
		String element1 = "<name1 xmlns=\"ns1\"/>";
		String element2 = "<name2 xmlns=\"ns1\"/>";
		String element3 = "<name3 xmlns=\"ns2\"/>";

		expectedConfigElements.add(element1);
		expectedConfigElements.add(element2);
		expectedConfigElements.add(element3);

		for(Element element: actualConfigElements){
			assertTrue(expectedConfigElements.contains(TestUtil.xmlToString(element)));
		}
	}

	@Test
	public void testUpdateSchema() {
    	ModelService service = spy(ModelService.class);
		org.opendaylight.yangtools.yang.common.QName qName = org.opendaylight.yangtools.yang.common.QName.create("ns", "name");

    	when(service.getSupportedFeatures()).thenReturn(Collections.EMPTY_SET);
		when(service.getSupportedDeviations()).thenReturn(Collections.EMPTY_MAP);
    	when(service.getYangFilePaths()).thenReturn(Collections.EMPTY_LIST);
    	assertFalse(service.updateSchema());

		when(service.getSupportedFeatures()).thenReturn(Collections.singleton(qName));
		assertTrue(service.updateSchema());

		when(service.getSupportedFeatures()).thenReturn(Collections.EMPTY_SET);
		when(service.getSupportedDeviations()).thenReturn(Collections.singletonMap(qName, Collections.singleton(qName)));
		assertTrue(service.updateSchema());

		when(service.getSupportedFeatures()).thenReturn(Collections.EMPTY_SET);
		when(service.getSupportedDeviations()).thenReturn(Collections.EMPTY_MAP);
		when(service.getYangFilePaths()).thenReturn(Collections.singletonList("dummy-yang"));
		assertTrue(service.updateSchema());
	}

}
