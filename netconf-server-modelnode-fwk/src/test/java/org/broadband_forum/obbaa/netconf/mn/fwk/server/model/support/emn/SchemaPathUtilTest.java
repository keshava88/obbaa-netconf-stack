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

import org.broadband_forum.obbaa.netconf.api.util.SchemaPathUtil;
import org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by keshava on 12/28/15.
 */
public class SchemaPathUtilTest {
    @Test
    public void testFromString(){
        assertEquals(JukeboxConstants.JUKEBOX_SCHEMA_PATH, SchemaPathUtil.fromString(SchemaPathUtil.toString(JukeboxConstants.JUKEBOX_SCHEMA_PATH)));
        assertEquals(JukeboxConstants.SONG_SCHEMA_PATH, SchemaPathUtil.fromString(SchemaPathUtil.toString(JukeboxConstants.SONG_SCHEMA_PATH)));
    }

}
