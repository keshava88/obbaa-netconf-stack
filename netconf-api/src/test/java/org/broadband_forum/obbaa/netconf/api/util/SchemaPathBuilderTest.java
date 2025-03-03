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

package org.broadband_forum.obbaa.netconf.api.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.model.api.SchemaPath;

/**
 * Created by keshava on 11/20/15.
 */
public class SchemaPathBuilderTest {
    @Test
    public void testSimpleBuild(){
        SchemaPathBuilder builder = new SchemaPathBuilder()
                                            .withNamespace("http://example.com/ns/example-jukebox")
                                            .withRevision("2015-02-27").appendLocalName("jukebox").appendLocalName("library");
        SchemaPath schemaPath = builder.build();
        assertEquals(SchemaPath.create(true, QName.create("http://example.com/ns/example-jukebox", "2015-02-27", "jukebox"),
                QName.create("http://example.com/ns/example-jukebox", "2015-02-27", "library")), schemaPath);
    }

    @Test
    public void testBuildWithParent(){
        SchemaPathBuilder builder = new SchemaPathBuilder()
                .withNamespace("http://example.com/ns/example-jukebox")
                .withRevision("2015-02-27").appendLocalName("jukebox").appendLocalName("library");
        SchemaPath schemaPath = builder.build();
        schemaPath = new SchemaPathBuilder().withNamespace("brand:new:namespace").withRevision("1988-03-27").withParent(schemaPath).appendLocalName("song").build();

        assertEquals(SchemaPath.create(true, QName.create("http://example.com/ns/example-jukebox", "2015-02-27", "jukebox"),
                QName.create("http://example.com/ns/example-jukebox", "2015-02-27", "library"), QName.create("brand:new:namespace", "1988-03-27", "song")), schemaPath);
    }

    @Test
    public void testFromString() throws SchemaPathBuilderException {
        SchemaPath artistSchemaPath = SchemaPath.create(true,
                QName.create("(http://example.com/ns/example-jukebox?revision=2015-02-27)jukebox"),
                QName.create("(http://example.com/ns/example-jukebox?revision=2015-02-27)library"),
                QName.create("(http://example.com/ns/example-jukebox?revision=2015-02-27)artist"));

        assertEquals(artistSchemaPath, SchemaPathBuilder.fromString("(http://example.com/ns/example-jukebox?revision=2015-02-27),jukebox,"
                + "library,artist"));

        //with spaces between local names
        assertEquals(artistSchemaPath, SchemaPathBuilder.fromString("(http://example.com/ns/example-jukebox?revision=2015-02-27) , jukebox,"
                + "library ,artist  "));

        SchemaPath pathWithMultipleNs = SchemaPath.create(true,
                QName.create("(http://example.com/ns/example-jukebox?revision=2015-02-27)jukebox"),
                QName.create("(http://example.com/ns/example-jukebox?revision=2015-02-27)library"),
                QName.create("(urn:another:namespace?revision=2016-02-27)anotherLocalName"),
                QName.create("(urn:another:namespace?revision=2016-02-27)anotherLocalName2"));
        assertEquals(pathWithMultipleNs, SchemaPathBuilder.fromString("(http://example.com/ns/example-jukebox?revision=2015-02-27),jukebox,"
                + "library", "(urn:another:namespace?revision=2016-02-27)anotherLocalName, anotherLocalName2"));
        //Invalid cases
        try {
            SchemaPathBuilder.fromString("(http://example.com/ns/example-jukebox?revision=2015-02-27, jukebox," + "library ,artist  ");
            fail("expected an exception here");
        }catch (SchemaPathBuilderException e){
            //expected
        }

        try {
            SchemaPathBuilder.fromString("http://example.com/ns/example-jukebox?revision=2015-02-27, jukebox," + "library ,artist  ");
            fail("expected an exception here");
        }catch (SchemaPathBuilderException e){
            //expected
        }

        try {
            SchemaPathBuilder.fromString("http://example.com/ns/example-jukebox?revision=2015-02-27), jukebox," + "library ,artist  ");
            fail("expected an exception here");
        }catch (SchemaPathBuilderException e){
            //expected
        }

        try {
            SchemaPathBuilder.fromString("(http://example.com/ns/example-jukebox), jukebox," + "library ,artist  ");
            fail("expected an exception here");
        }catch (SchemaPathBuilderException e){
            //expected
        }
    }

    @Test
    public void testFromMultipleStrings(){
        SchemaPath pathWithMultipleNs = SchemaPath.create(true,
                QName.create("(http://example.com/ns/example-jukebox?revision=2015-02-27)jukebox"),
                QName.create("(http://example.com/ns/example-jukebox?revision=2015-02-27)library"),
                QName.create("(urn:another:namespace?revision=2016-02-27)anotherLocalName"),
                QName.create("(urn:another:namespace?revision=2016-02-27)anotherLocalName2"));
        assertEquals(pathWithMultipleNs, SchemaPathBuilder.fromMultipleStrings("(http://example.com/ns/example-jukebox?revision=2015-02-27),jukebox,"
                + "library??(urn:another:namespace?revision=2016-02-27)anotherLocalName, anotherLocalName2"));

    }
    
    @Test
    public void testGetSchemaPathFromPathString(){
    	QName jukeboxQName = QName.create("(http://example.com/ns/example-jukebox)jukebox");
    	QName libraryQName = QName.create("(http://example.com/ns/example-jukebox)library");
    	QName anotherLocalQName = QName.create("(urn:another:namespace)anotherLocalName");
    	SchemaPath expectedSP = SchemaPath.create(true, jukeboxQName, libraryQName, anotherLocalQName);
    	Map<String, String> prefixToNsMap = new HashMap<>();
    	prefixToNsMap.put("ex", "http://example.com/ns/example-jukebox");
    	prefixToNsMap.put("test", "urn:another:namespace");
    	SchemaPath actualSP = SchemaPathBuilder.getSchemaPathFromString(prefixToNsMap, "/ex:jukebox/ex:library/test:anotherLocalName");
    	assertEquals(expectedSP, actualSP);

    	try {
    		actualSP = SchemaPathBuilder.getSchemaPathFromString(prefixToNsMap, "/ex:jukebox/library/test:anotherLocalName");
    	} catch(RuntimeException e){
    		assertEquals("Could not find the namespace for the prefix xmlns in the pathStr /ex:jukebox/library/test:anotherLocalName", e.getMessage());
    	}
    	
    	prefixToNsMap.put("xmlns", "http://example.com/ns/example-jukebox");
    	actualSP = SchemaPathBuilder.getSchemaPathFromString(prefixToNsMap, "/ex:jukebox/library/test:anotherLocalName");
    	assertEquals(expectedSP, actualSP);
    }

    @Test
    public void testToString() {
        testToStr("(http://example.com/ns/example-jukebox?revision=2015-02-27)jukebox,"
                + "library??(urn:another:namespace?revision=2016-02-27)anotherLocalName,anotherLocalName2");
        testToStr("(http://example.com/ns/example-jukebox?revision=2015-02-27)jukebox,library");
    }

    private void testToStr(String spStr) {
        SchemaPath schemaPath = SchemaPathBuilder.fromMultipleStrings(spStr);
        assertEquals(spStr, SchemaPathBuilder.toString(schemaPath));
    }
}
